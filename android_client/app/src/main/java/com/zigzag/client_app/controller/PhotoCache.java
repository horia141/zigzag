package com.zigzag.client_app.controller;

import android.net.Uri;
import android.util.Log;
import android.util.Pair;

import com.android.volley.Cache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhotoCache implements Cache {

    private static final String COUNT_FILE_PATH = "counts";
    private static final String HEADERS_FILE_PATH_PATTERN = "%d.headers";
    private static final String CONTENT_FILE_PATH_PATTERN = "%d.content";

    private final File rootDirectory;
    private final int maxNumberOfElements;
    private final Pattern allowedKeys;
    private int currentIndex;
    private final Map<Integer, String> reverseAllocatedIdxs;
    private final Map<String, Integer> allocatedIdxs;
    private final Map<String, Entry> entries;

    public PhotoCache(File newRootDirectory, int newMaxNumberOfElements, Pattern newAllowedKeys) {
        assert newMaxNumberOfElements >= 1;
        rootDirectory = newRootDirectory;
        maxNumberOfElements = newMaxNumberOfElements;
        allowedKeys = newAllowedKeys;
        currentIndex = 0;
        reverseAllocatedIdxs = new HashMap<>();
        allocatedIdxs = new HashMap<>();
        entries = new HashMap<>();

        if (!rootDirectory.exists()) {
            boolean created = rootDirectory.mkdirs();
            if (!created) {
                throw new RuntimeException("Unable to create cache root directory");
            }
        }
        File resDir = new File(newRootDirectory, "res");
        if (!resDir.exists()) {
            resDir.mkdir();
        }
    }

    public Entry get(String key) {
        Entry entry = entries.get(key);

        if (entry == null) {
            return null;
        }

        if (entry.data == null) {
            try {
                entry.data = readContent(contentFileForKey(key));
            } catch (IOException e) {
                return null;
            }
        }

        return entry;
    }

    public void put(String key, Entry entry) {
        Matcher keyMatcher = allowedKeys.matcher(key);
        if (!keyMatcher.matches()) {
            return;
        }

        String previousKey = null;
        synchronized (this) {
            // First, remove any existing key.
            previousKey = reverseAllocatedIdxs.get(currentIndex);
            if (previousKey != null) {
                reverseAllocatedIdxs.remove(currentIndex);
                allocatedIdxs.remove(previousKey);
                entries.remove(previousKey);
            }

            // Then, update the index with it.
            reverseAllocatedIdxs.put(currentIndex, key);
            allocatedIdxs.put(key, currentIndex);
            entries.put(key, entry);

            // Modul update the counter.
            currentIndex = (currentIndex + 1) % maxNumberOfElements;
        }

        try {
            if (previousKey != null) {
                headersFileForKey(previousKey).delete();
                contentFileForKey(previousKey).delete();
            }

            updateCountStatus();
            writeHeaders(headersFileForKey(key), key, entry);
            writeContent(contentFileForKey(key), entry);
        } catch (IOException e) {
            throw new RuntimeException("Could not write cache entry", e);
        }
    }

    public void initialize() {
        int previousMaxNumberOfElements = -1;
        currentIndex = 0;

        try {
            File countFile = new File(rootDirectory, COUNT_FILE_PATH);
            DataInputStream countStream = new DataInputStream(new FileInputStream(countFile));
            previousMaxNumberOfElements = countStream.readInt();
            currentIndex = countStream.readInt();
            countStream.close();
        } catch (IOException e) {
            // Do nothing.
        }

        if (maxNumberOfElements != previousMaxNumberOfElements || currentIndex >= maxNumberOfElements) {
            removeAllFiles();
        }

        try {
            updateCountStatus();

            for (int ii = 0; ii < maxNumberOfElements; ii++) {
                File headersFile = new File(rootDirectory, String.format(HEADERS_FILE_PATH_PATTERN, ii));

                if (!headersFile.exists()) {
                    continue;
                }

                Pair<String, Entry> headersInfo = readHeaders(headersFile);
                reverseAllocatedIdxs.put(ii, headersInfo.first);
                allocatedIdxs.put(headersInfo.first, ii);
                entries.put(headersInfo.first, headersInfo.second);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create cache structures");
        }
    }

    public void invalidate(String key, boolean fullExpire) {
        Entry entry = get(key);

        if (entry == null) {
            return;
        }

        entry.softTtl = 0;
        if (fullExpire) {
            entry.ttl = 0;
        }

        put(key, entry);
    }

    public void remove(String key) {
        Entry entry = entries.get(key);

        if (entry == null) {
            return;
        }

        File headersFile = headersFileForKey(key);
        headersFile.delete();
        File contentFile = contentFileForKey(key);
        contentFile.delete();
        synchronized (this) {
            int allocatedIdx = allocatedIdxs.get(key);
            reverseAllocatedIdxs.remove(allocatedIdx);
            allocatedIdxs.remove(key);
            entries.remove(key);
        }
    }

    public void clear() {
        synchronized (this) {
            reverseAllocatedIdxs.clear();
            allocatedIdxs.clear();
            entries.clear();
            currentIndex = 0;
        }
        removeAllFiles();
        try {
            updateCountStatus();
        } catch (IOException e) {
            // Do nothing.
        }
    }

    private void updateCountStatus() throws IOException {
        File countsFile = new File(rootDirectory, COUNT_FILE_PATH);
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(countsFile)));
        dos.writeInt(maxNumberOfElements);
        dos.writeInt(currentIndex);
        dos.close();
    }

    private File headersFileForKey(String key) {
        return new File(rootDirectory, String.format(HEADERS_FILE_PATH_PATTERN, allocatedIdxs.get(key)));
    }

    public File contentFileForKey(String key) {
        // This function assumes the keys are something like http://[host]/[path].(jpg|mp4).
        Uri keyAsUri = Uri.parse(key);
        return new File(rootDirectory, keyAsUri.getLastPathSegment());
    }

    private void removeAllFiles() {
        File[] allFiles = rootDirectory.listFiles();
        if (allFiles != null) {
            for (File file : allFiles) {
                file.delete();
            }
        }
    }

    private static Pair<String, Entry> readHeaders(File headersFile) throws IOException {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(headersFile)));
        String key = dis.readUTF();
        Entry entry = new Entry();
        entry.etag = dis.readUTF();
        if (entry.etag.equals("")) {
            entry.etag = null;
        }
        entry.serverDate = dis.readLong();
        entry.ttl = dis.readLong();
        entry.softTtl = dis.readLong();
        entry.responseHeaders = readStringStringMap(dis);
        dis.close();
        return new Pair<>(key, entry);
    }

    private static Map<String, String> readStringStringMap(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        Map<String, String> result = (size == 0) ? Collections.<String, String>emptyMap()
                : new HashMap<String, String>(size);
        for (int i = 0; i < size; i++) {
            String key = dis.readUTF();
            String value = dis.readUTF();
            result.put(key, value);
        }
        return result;
    }

    private static void writeHeaders(File headersFile, String key, Entry entry) throws IOException {
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(headersFile)));
        dos.writeUTF(key);
        dos.writeUTF(entry.etag == null ? "" : entry.etag);
        dos.writeLong(entry.serverDate);
        dos.writeLong(entry.ttl);
        dos.writeLong(entry.softTtl);
        writeStringStringMap(dos, entry.responseHeaders);
        dos.close();
    }

    private static void writeStringStringMap(DataOutputStream dos, Map<String, String> map) throws IOException {
        if (map == null) {
            dos.writeInt(0);
            return;
        }

        dos.writeInt(map.size());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            dos.writeUTF(entry.getKey());
            dos.writeUTF(entry.getValue());
        }
    }

    private static byte[] readContent(File contentFile) throws IOException {
        int contentSize = (int)contentFile.length();
        byte[] content = new byte[contentSize];

        InputStream ios = new BufferedInputStream(new FileInputStream(contentFile));
        int readSize = ios.read(content, 0, contentSize);
        ios.close();
        if (readSize != contentSize) {
            throw new IOException("Could not read full data for content file");
        }

        return content;
    }

    private static void writeContent(File contentFile, Entry entry) throws IOException {
        if (entry.data == null) {
            throw new IOException("No data to write to content file");
        }

        OutputStream fos = new BufferedOutputStream(new FileOutputStream(contentFile));
        fos.write(entry.data);
        fos.close();
    }
}