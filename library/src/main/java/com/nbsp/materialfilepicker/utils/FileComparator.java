package com.nbsp.materialfilepicker.utils;

import java.io.File;
import java.util.Comparator;

/**
 * Created by Dimorinny on 24.10.15.
 */
public class FileComparator implements Comparator<File> {
    @Override
    public int compare(File f1, File f2) {
        if (f1 == f2) {
            return 0;
        }
        if (f1.isDirectory() && f2.isFile()) {
            // Show directories above files
            return -1;
        }
        if (f1.isFile() && f2.isDirectory()) {
            // Show files below directories
            return 1;
        }
        // Sort the directories alphabetically
        return f1.getName().compareToIgnoreCase(f2.getName());
    }
}
