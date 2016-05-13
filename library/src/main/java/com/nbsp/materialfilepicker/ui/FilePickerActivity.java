package com.nbsp.materialfilepicker.ui;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;

import com.nbsp.materialfilepicker.R;
import com.nbsp.materialfilepicker.utils.FileUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.regex.Pattern;

/**
 * Created by Dimorinny on 24.10.15.
 */
public class FilePickerActivity extends AppCompatActivity implements DirectoryFragment.FileClickListener {
    public static final String ARG_FILE_FILTER = "arg_file_filter";
    public static final String ARG_DIRECTORIES_FILTER = "arg_directories_filter";
    public static final String ARG_START_PATH = "arg_start_path";
    public static final String ARG_CURRENT_PATH = "arg_current_path";
    public static final String ARG_SHOW_HIDDEN = "arg_show_hidden";

    public static final String STATE_START_PATH = "state_start_path";
    private static final String STATE_CURRENT_PATH = "state_current_path";

    public static final String RESULT_FILE_PATH = "result_file_path";
    private static final int HANDLE_CLICK_DELAY = 150;

    private Toolbar mToolbar;
    private String mStartPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String mCurrentPath = mStartPath;

    private Pattern mFileFilter;
    private boolean mDirectoriesFilter;

    private boolean mShowHidden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_picker);

        initArguments();
        initViews();
        initToolbar();

        if (savedInstanceState != null) {
            mStartPath = savedInstanceState.getString(STATE_START_PATH);
            mCurrentPath = savedInstanceState.getString(STATE_CURRENT_PATH);
        } else {
            initFragment();
        }
    }

    private void initArguments() {
        if (getIntent().hasExtra(ARG_DIRECTORIES_FILTER)) {
            mDirectoriesFilter = getIntent().getBooleanExtra(ARG_DIRECTORIES_FILTER, false);
        }

        if (getIntent().hasExtra(ARG_FILE_FILTER)) {
            mFileFilter = (Pattern) getIntent().getSerializableExtra(ARG_FILE_FILTER);
        }

        if (getIntent().hasExtra(ARG_START_PATH)) {
            mStartPath = getIntent().getStringExtra(ARG_START_PATH);
            mCurrentPath = mStartPath;
        }

        if (getIntent().hasExtra(ARG_CURRENT_PATH)) {
            String currentPath = getIntent().getStringExtra(ARG_START_PATH);

            if (currentPath.startsWith(mStartPath)) {
                mCurrentPath = currentPath;
            }
        }

        if (getIntent().hasExtra(ARG_SHOW_HIDDEN)) {
            mShowHidden = getIntent().getBooleanExtra(ARG_SHOW_HIDDEN, false);
        }
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);

        // Show back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Truncate start of toolbar title
        try {
            Field f = mToolbar.getClass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);

            TextView textView = (TextView) f.get(mToolbar);
            textView.setEllipsize(TextUtils.TruncateAt.START);
        } catch (Exception ignored) {}

        updateTitle();
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void initFragment() {
        getFragmentManager().beginTransaction()
                .add(R.id.container, DirectoryFragment.getInstance(
                        mStartPath, mFileFilter, mDirectoriesFilter, mShowHidden))
                .commit();
    }

    private void updateTitle() {
        if (getSupportActionBar() != null) {
            String title = mCurrentPath.isEmpty() ? "/" : mCurrentPath;
            if (title.startsWith(mStartPath)) {
                title = title.replaceFirst(mStartPath, getString(R.string.start_path_name));
            }
            getSupportActionBar().setTitle(title);
        }
    }

    private void addFragmentToBackStack(String path) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, DirectoryFragment.getInstance(
                        path, mFileFilter, mDirectoriesFilter, mShowHidden))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();

        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
            mCurrentPath = FileUtils.cutLastSegmentOfPath(mCurrentPath);
            updateTitle();
        } else {
            setResult(RESULT_CANCELED);
            super.onBackPressed();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_CURRENT_PATH, mCurrentPath);
        outState.putString(STATE_START_PATH, mStartPath);
    }

    @Override
    public void onFileClicked(final File clickedFile) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                handleFileClicked(clickedFile);
            }
        }, HANDLE_CLICK_DELAY);
    }

    private void handleFileClicked(final File clickedFile) {
        if (!isFinishing()) {
            if (clickedFile.isDirectory()) {
                addFragmentToBackStack(clickedFile.getPath());
                mCurrentPath = clickedFile.getPath();
                updateTitle();
            } else {
                setResultAndFinish(clickedFile.getPath());
            }
        } else {
            // Do nothing
        }
    }

    private void setResultAndFinish(String filePath) {
        Intent data = new Intent();
        data.putExtra(RESULT_FILE_PATH, filePath);
        setResult(RESULT_OK, data);
        finish();
    }
}
