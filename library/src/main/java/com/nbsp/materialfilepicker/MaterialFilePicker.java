package com.nbsp.materialfilepicker;

import android.app.Activity;
import android.content.Intent;

import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.util.regex.Pattern;

/**
 * Created by Dimorinny on 25.02.16.
 */
public class MaterialFilePicker {
    private Activity mActivity;
    private Integer mRequestCode;
    private Pattern mFileFilter;
    private Boolean mDirectoriesFilter = false;
    private String mRootPath;
    private String mCurrentPath;

    public MaterialFilePicker() {}

    public MaterialFilePicker withActivity(Activity activity) {
        mActivity = activity;
        return this;
    }

    public MaterialFilePicker withRequestCode(int requestCode) {
        mRequestCode = requestCode;
        return this;
    }

    public MaterialFilePicker withFilter(Pattern pattern) {
        mFileFilter = pattern;
        return this;
    }

    public MaterialFilePicker withFilterDirectories(boolean directoriesFilter) {
        mDirectoriesFilter = directoriesFilter;
        return this;
    }

    public MaterialFilePicker withRootPath(String rootPath) {
        mRootPath = rootPath;
        return this;
    }

    public MaterialFilePicker withPath(String path) {
        mCurrentPath = path;
        return this;
    }

    public void start() {
        if (mActivity == null) {
            throw new RuntimeException("You must pass activity by calling withActivity method");
        }

        if (mRequestCode == null) {
            throw new RuntimeException("You must pass request code by calling withRequestCode method");
        }

        Intent intent = new Intent(mActivity, FilePickerActivity.class);
        intent.putExtra(FilePickerActivity.ARG_DIRECTORIES_FILTER, mDirectoriesFilter);

        if (mFileFilter != null) {
            intent.putExtra(FilePickerActivity.ARG_FILE_FILTER, mFileFilter);
        }

        if (mRootPath != null) {
            intent.putExtra(FilePickerActivity.ARG_START_PATH, mRootPath);
        }

        if (mCurrentPath != null) {
            intent.putExtra(FilePickerActivity.ARG_CURRENT_PATH, mCurrentPath);
        }

        mActivity.startActivityForResult(intent, mRequestCode);
    }
}
