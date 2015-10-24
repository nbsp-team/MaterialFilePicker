package com.dimorinny.sample;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Dimorinny on 23.10.15.
 */
public class FilePickerActivity extends ListActivity {

    /**
     * The file path
     */
    public final static String EXTRA_FILE_PATH = "file_path";

    /**
     * Sets whether hidden files should be visible in the list or not
     */
    public final static String EXTRA_SHOW_HIDDEN_FILES = "show_hidden_files";

    /**
     * The allowed file extensions in an ArrayList of Strings
     */
    public final static String EXTRA_ACCEPTED_FILE_EXTENSIONS = "accepted_file_extensions";

    /**
     * The initial directory which will be used if no directory has been sent with the intent
     */
    private final static String DEFAULT_INITIAL_DIRECTORY = "/";

    protected File mDirectory;
    protected ArrayList<File> mFiles;
    protected FilePickerListAdapter mAdapter;
    protected boolean mShowHiddenFiles = false;
    protected String[] acceptedFileExtensions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the view to be shown if the list is empty
        LayoutInflater inflator = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View emptyView = inflator.inflate(R.layout.file_picker_empty_view, null);
        ((ViewGroup)getListView().getParent()).addView(emptyView);
        getListView().setEmptyView(emptyView);

        // Set initial directory
        mDirectory = new File(DEFAULT_INITIAL_DIRECTORY);

        // Initialize the ArrayList
        mFiles = new ArrayList<File>();

        // Set the ListAdapter
        mAdapter = new FilePickerListAdapter(this, mFiles);
        setListAdapter(mAdapter);

        // Initialize the extensions array to allow any file extensions
        acceptedFileExtensions = new String[] {};

        // Get intent extras
        if(getIntent().hasExtra(EXTRA_FILE_PATH)) {
            mDirectory = new File(getIntent().getStringExtra(EXTRA_FILE_PATH));
        }
        if(getIntent().hasExtra(EXTRA_SHOW_HIDDEN_FILES)) {
            mShowHiddenFiles = getIntent().getBooleanExtra(EXTRA_SHOW_HIDDEN_FILES, false);
        }
        if(getIntent().hasExtra(EXTRA_ACCEPTED_FILE_EXTENSIONS)) {
            ArrayList<String> collection = getIntent().getStringArrayListExtra(EXTRA_ACCEPTED_FILE_EXTENSIONS);
            acceptedFileExtensions = (String[]) collection.toArray(new String[collection.size()]);
        }
    }

    @Override
    protected void onResume() {
        refreshFilesList();
        super.onResume();
    }

    /**
     * Updates the list view to the current directory
     */
    protected void refreshFilesList() {
        // Clear the files ArrayList
        mFiles.clear();

        // Set the extension file filter
        ExtensionFilenameFilter filter = new ExtensionFilenameFilter(acceptedFileExtensions);

        // Get the files in the directory
        File[] files = mDirectory.listFiles(filter);
        if(files != null && files.length > 0) {
            for(File f : files) {
                if(f.isHidden() && !mShowHiddenFiles) {
                    // Don't add the file
                    continue;
                }

                // Add the file the ArrayAdapter
                mFiles.add(f);
            }

            Collections.sort(mFiles, new FileComparator());
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if(mDirectory.getParentFile() != null) {
            // Go to parent directory
            mDirectory = mDirectory.getParentFile();
            refreshFilesList();
            return;
        }

        super.onBackPressed();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        File newFile = (File)l.getItemAtPosition(position);

        if(newFile.isFile()) {
            // Set result
            Intent extra = new Intent();
            extra.putExtra(EXTRA_FILE_PATH, newFile.getAbsolutePath());
            setResult(RESULT_OK, extra);
            // Finish the activity
            finish();
        } else {
            mDirectory = newFile;
            // Update the files list
            refreshFilesList();
        }

        super.onListItemClick(l, v, position, id);
    }

    private class FilePickerListAdapter extends ArrayAdapter<File> {

        private List<File> mObjects;

        public FilePickerListAdapter(Context context, List<File> objects) {
            super(context, R.layout.file_picker_list_item, android.R.id.text1, objects);
            mObjects = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row = null;

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.file_picker_list_item, parent, false);
            } else {
                row = convertView;
            }

            File object = mObjects.get(position);

            ImageView imageView = (ImageView)row.findViewById(R.id.file_picker_image);
            TextView textView = (TextView)row.findViewById(R.id.file_picker_text);
            // Set single line
            textView.setSingleLine(true);

            textView.setText(object.getName());
            if(object.isFile()) {
                // Show the file icon
//                imageView.setImageResource(R.drawable.);
            } else {
                // Show the folder icon
//                imageView.setImageResource(R.drawable.folder);
            }

            return row;
        }

    }

    private class FileComparator implements Comparator<File> {
        @Override
        public int compare(File f1, File f2) {
            if(f1 == f2) {
                return 0;
            }
            if(f1.isDirectory() && f2.isFile()) {
                // Show directories above files
                return -1;
            }
            if(f1.isFile() && f2.isDirectory()) {
                // Show files below directories
                return 1;
            }
            // Sort the directories alphabetically
            return f1.getName().compareToIgnoreCase(f2.getName());
        }
    }

    private class ExtensionFilenameFilter implements FilenameFilter {
        private String[] mExtensions;

        public ExtensionFilenameFilter(String[] extensions) {
            super();
            mExtensions = extensions;
        }

        @Override
        public boolean accept(File dir, String filename) {
            if(new File(dir, filename).isDirectory()) {
                // Accept all directory names
                return true;
            }
            if(mExtensions != null && mExtensions.length > 0) {
                for(int i = 0; i < mExtensions.length; i++) {
                    if(filename.endsWith(mExtensions[i])) {
                        // The filename ends with the extension
                        return true;
                    }
                }
                // The filename did not match any of the extensions
                return false;
            }
            // No extensions has been set. Accept all file extensions.
            return true;
        }
    }
}
