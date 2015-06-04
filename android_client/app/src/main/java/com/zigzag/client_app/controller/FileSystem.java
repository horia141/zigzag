package com.zigzag.client_app.controller;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FileSystem {

    private class InitializeTask implements  Runnable {

        @Override
        public void run() {
            boolean createdSpecific = true;

            synchronized (specificDir) {
                if (!specificDir.exists()) {
                    createdSpecific = specificDir.mkdirs();
                } else if (!specificDir.isDirectory()) {
                    Log.e("ZigZag/FileSystem", "Specific path exists but it is not a directory");
                    throw new RuntimeException("Specific path exists but it is not a directory");
                }
            }

            if (!createdSpecific) {
                Log.e("ZigZag/FileSystem", "Could not create specific directory");
                throw new RuntimeException("Could not create specific directory");
            }
        }
    }

    private class PeriodicGCTask implements Runnable {

        @Override
        public void run() {
            File[] filesToGc;
            synchronized (specificDir) {
                filesToGc = specificDir.listFiles();
            }

            for (File fileToGc : filesToGc) {
                if (knownPathsFilter.contains(fileToGc.getAbsolutePath())) {
                    continue;
                }

                fileToGc.delete();
            }
        }
    }

    private class TargetedGCTask implements Runnable {

        private final Object tag;
        private final String knownPath;

        public TargetedGCTask(Object newTag, String newKnownPath) {
            tag = newTag;
            knownPath = newKnownPath;
        }

        @Override
        public void run() {
            File knownFile;
            synchronized (specificDir) {
                knownFile = new File(specificDir, knownPath);
            }

            knownFile.delete();
            knownPaths.remove(tag);
            knownPathsFilter.remove(knownFile.getAbsolutePath());
        }
    }

    private static final long TARGETED_GC_WAIT_MIN = 1;

    private final String specificName;
    private final ScheduledThreadPoolExecutor executor;
    private final Map<Object, String> knownPaths;
    private final Set<String> knownPathsFilter;
    private final File specificDir;

    public FileSystem(String newSpecificName) {
        specificName = newSpecificName;
        executor = new ScheduledThreadPoolExecutor(1);
        knownPaths = new ConcurrentHashMap<>();
        knownPathsFilter = new ConcurrentSkipListSet<>();
        File publicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        specificDir = new File(publicDir, newSpecificName);

        executor.schedule(new InitializeTask(), 0, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(new PeriodicGCTask(), TARGETED_GC_WAIT_MIN, TARGETED_GC_WAIT_MIN,
                TimeUnit.MINUTES);
    }

    public File register(Object tag, String path) {
        File tagFile;
        synchronized (specificDir) {
            tagFile = new File(specificDir, path);
        }

        knownPaths.put(tag, path);
        knownPathsFilter.add(tagFile.getAbsolutePath());

        return tagFile;
    }

    public void release(Object tag) {
        String path = knownPaths.get(tag);
        if (path == null) {
            return;
        }

        executor.schedule(new TargetedGCTask(tag, path), 0, TimeUnit.SECONDS);
    }
}
