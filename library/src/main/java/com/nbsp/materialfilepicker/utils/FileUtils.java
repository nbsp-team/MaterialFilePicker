package com.nbsp.materialfilepicker.utils;

import com.nbsp.materialfilepicker.filter.FileFilter;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileUtils {

    public static List<File> getFileListByDirPath(String path, FileFilter filter) {
        File directory = new File(path);
        File[] files = directory.listFiles(filter::accept);

        if (files == null) {
            return new ArrayList<>();
        }

        List<File> result = Arrays.asList(files);
        Collections.sort(result, new FileComparator());
        return result;
    }

    public static File getParentIfExists(File file) {
        if (file.getParent() == null) {
            return file;
        }

        return file.getParentFile();
    }

    public static String getReadableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
