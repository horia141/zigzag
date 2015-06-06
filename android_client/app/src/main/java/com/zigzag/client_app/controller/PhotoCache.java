package com.zigzag.client_app.controller;

import android.util.Pair;

import com.android.volley.Cache;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PhotoCache implements Cache {

    private static final String COUNT_FILE_PATH = "counts";
    private static final String HEADERS_FILE_PATH_PATTERN = "%d.headers";
    private static final String CONTENT_FILE_PATH_PATTERN = "%d.content";

    private final File rootDirectory;
    private final int maxNumberOfElements;
    private final Map<String, Entry> entries;

    public PhotoCache(File newRootDirectory, int newMaxNumberOfElements) {
        rootDirectory = newRootDirectory;
        maxNumberOfElements = newMaxNumberOfElements;
        entries = new HashMap<>();

        if (!rootDirectory.exists()) {
            boolean created = rootDirectory.mkdirs();
            if (!created) {
                throw new RuntimeException("Unable to create cache root directory");
            }
        }
    }

    public Entry get(String key) {
        Entry entry = entries.get(key);

        if (entry == null) {
            return null;
        }

        if (entry.data == null) {
            entry.data = readContent(contentFileForKey(key));
        }

        return entry;
    }

    public void put(String key, Entry entry) {
        writeHeaders(headersFileForKey(key), key, entry);
        writeContent(contentFileForKey(key), entry);
        entries.put(key, entry);
    }

    public void initialize() {
        File countFile = new File(rootDirectory, COUNT_FILE_PATH);
        int previousMaxNumberOfElements = -1;

        try {
            DataInputStream countStream = new DataInputStream(new FileInputStream(countFile));
            previousMaxNumberOfElements = countStream.readInt();
            countStream.close();
        } catch (IOException e) {
            previousMaxNumberOfElements = maxNumberOfElements;
        }

        if (maxNumberOfElements != previousMaxNumberOfElements) {
            removeAllFiles();
        }

        try {
            DataOutputStream countStream = new DataOutputStream(new FileOutputStream(countFile));
            countStream.writeInt(maxNumberOfElements);
            countStream.close();

            for (int ii = 0; ii < maxNumberOfElements; ii++) {
                File headersFile = new File(rootDirectory, String.format(HEADERS_FILE_PATH_PATTERN, ii));
                Pair<String, Entry> headersInfo = null;

                if (headersFile.exists()) {
                    headersInfo = readHeaders(headersFile);
                } else {
                    boolean created = headersFile.createNewFile();
                    if (!created) {
                        throw new RuntimeException("Could not create headers file");
                    }
                }

                File contentFile = new File(rootDirectory, String.format(CONTENT_FILE_PATH_PATTERN, ii));
                if (!contentFile.exists()) {
                    boolean created = contentFile.createNewFile();
                    if (!created) {
                        throw new RuntimeException("Could not create content file");
                    }
                }

                if (headersInfo != null) {
                    entries.put(headersInfo.first, headersInfo.second);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create cache structures");
        }
    }

    public void invalidate(String key, boolean fullExpire) {

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
        entries.remove(key);
    }

    public void clear() {
        removeAllFiles();
        entries.clear();
    }

    private int bucketForKey(String key) {
        return key.hashCode() % maxNumberOfElements;
    }

    private File headersFileForKey(String key) {
        return new File(rootDirectory, String.format(HEADERS_FILE_PATH_PATTERN, bucketForKey(key)));
    }

    private File contentFileForKey(String key) {
        return new File(rootDirectory, String.format(CONTENT_FILE_PATH_PATTERN, bucketForKey(key)));
    }

    private void removeAllFiles() {
        File[] allFiles = rootDirectory.listFiles();
        if (allFiles != null) {
            for (File file : allFiles) {
                file.delete();
            }
        }
    }

    private static Pair<String, Entry> readHeaders(File headersFile) {
        return null;
    }

    private static void writeHeaders(File headersFile, String key, Entry entry) {

    }

    private static byte[] readContent(File contentFile) {
        return null;
    }

    private static void writeContent(File contentFile, Entry entry) {

    }
}
