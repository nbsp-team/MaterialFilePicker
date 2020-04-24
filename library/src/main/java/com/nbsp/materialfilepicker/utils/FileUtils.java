package com.nbsp.materialfilepicker.utils;

import androidx.annotation.Nullable;

import com.nbsp.materialfilepicker.filter.FileFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileUtils {

    public static List<File> getFileList(File directory, FileFilter filter) {
        File[] files = directory.listFiles(filter::accept);

        if (files == null) {
            return new ArrayList<>();
        }

        List<File> result = Arrays.asList(files);
        Collections.sort(result, new FileComparator());
        return result;
    }

    @Nullable
    public static File getParentOrNull(File file) {
        if (file.getParent() == null) {
            return null;
        }

        return file.getParentFile();
    }

    public static boolean isParent(File maybeChild, File possibleParent) {
        if (!possibleParent.exists() || !possibleParent.isDirectory()) {
            return false;
        }

        File child = maybeChild;
        while (child != null) {
            if (child.equals(possibleParent)) {
                return true;
            }
            child = child.getParentFile();
        }

        return false;
    }
}
