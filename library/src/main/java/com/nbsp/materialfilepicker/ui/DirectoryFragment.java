package com.nbsp.materialfilepicker.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nbsp.materialfilepicker.R;
import com.nbsp.materialfilepicker.utils.FileUtils;
import com.nbsp.materialfilepicker.widget.EmptyRecyclerView;

import java.io.File;
import java.util.regex.Pattern;

/**
 * Created by Dimorinny on 24.10.15.
 */
public class DirectoryFragment extends Fragment {
    interface FileClickListener {
        void onFileClicked(File clickedFile);
    }

    private static final String ARG_FILE_PATH = "arg_file_path";
    private static final String ARG_FILE_FILTER = "arg_regexp_file_filter";
    private static final String ARG_DIRECTORIES_FILTER = "arg_directories_filter";
    private static final String ARG_SHOW_HIDDEN = "arg_show_hidden";

    private View mEmptyView;
    private String mPath;

    @Nullable private Pattern mFileFilter;
    private boolean mDirectoryFilter;

    private boolean mShowHidden;

    private EmptyRecyclerView mDirectoryRecyclerView;
    private DirectoryAdapter mDirectoryAdapter;
    private FileClickListener mFileClickListener;

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFileClickListener = (FileClickListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFileClickListener = null;
    }

    public static DirectoryFragment getInstance(
            String path, @Nullable Pattern filter, boolean directoriesFilter, boolean showHidden) {
        DirectoryFragment instance = new DirectoryFragment();

        Bundle args = new Bundle();
        args.putString(ARG_FILE_PATH, path);
        args.putSerializable(ARG_FILE_FILTER, filter);
        args.putBoolean(ARG_DIRECTORIES_FILTER, directoriesFilter);
        args.putBoolean(ARG_SHOW_HIDDEN, showHidden);
        instance.setArguments(args);

        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_directory, container, false);
        mDirectoryRecyclerView = (EmptyRecyclerView) view.findViewById(R.id.directory_recycler_view);
        mEmptyView = view.findViewById(R.id.directory_empty_view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initArgs();
        initFilesList();
    }

    private void initFilesList() {
        mDirectoryAdapter = new DirectoryAdapter(getActivity(),
                FileUtils.getFileListByDirPath(mPath, mFileFilter, mDirectoryFilter, mShowHidden));

        mDirectoryAdapter.setOnItemClickListener(new DirectoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mFileClickListener != null) {
                    mFileClickListener.onFileClicked(mDirectoryAdapter.getModel(position));
                }
            }
        });

        mDirectoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDirectoryRecyclerView.setAdapter(mDirectoryAdapter);
        mDirectoryRecyclerView.setEmptyView(mEmptyView);
    }

    private void initArgs() {
        if (getArguments().getString(ARG_FILE_PATH) != null) {
            mPath = getArguments().getString(ARG_FILE_PATH);
        }
        mDirectoryFilter = getArguments().getBoolean(ARG_DIRECTORIES_FILTER);
        mFileFilter = (Pattern) getArguments().getSerializable(ARG_FILE_FILTER);
        mShowHidden = getArguments().getBoolean(ARG_SHOW_HIDDEN, false);
    }
}
