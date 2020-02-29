package com.nbsp.materialfilepicker.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nbsp.materialfilepicker.R;
import com.nbsp.materialfilepicker.filter.FileFilter;
import com.nbsp.materialfilepicker.utils.FileUtils;
import com.nbsp.materialfilepicker.widget.EmptyRecyclerView;

import java.io.File;

import static java.util.Objects.requireNonNull;

public class DirectoryFragment extends Fragment {

    private static final String ARG_FILE_PATH = "arg_file_path";
    private static final String ARG_FILTER = "arg_filter";

    private View mEmptyView;
    private String mPath;

    private FileFilter mFilter;

    private EmptyRecyclerView mDirectoryRecyclerView;
    private DirectoryAdapter mDirectoryAdapter;
    private FileClickListener mFileClickListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mFileClickListener = (FileClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFileClickListener = null;
    }

    static DirectoryFragment getInstance(
            String path,
            FileFilter filter
    ) {
        final DirectoryFragment instance = new DirectoryFragment();

        final Bundle args = new Bundle();
        args.putString(ARG_FILE_PATH, path);
        args.putSerializable(ARG_FILTER, filter);
        instance.setArguments(args);

        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_directory, container, false);

        mDirectoryRecyclerView = view.findViewById(R.id.directory_recycler_view);
        mEmptyView = view.findViewById(R.id.directory_empty_view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initArgs();
        initFilesList();
    }

    private void initFilesList() {
        mDirectoryAdapter = new DirectoryAdapter(FileUtils.getFileListByDirPath(mPath, mFilter));

        mDirectoryAdapter.setOnItemClickListener((view, position) -> {
            if (mFileClickListener != null) {
                mFileClickListener.onFileClicked(mDirectoryAdapter.getModel(position));
            }
        });

        mDirectoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDirectoryRecyclerView.setAdapter(mDirectoryAdapter);
        mDirectoryRecyclerView.setEmptyView(mEmptyView);
    }

    private void initArgs() {
        final Bundle arguments = requireNonNull(getArguments());

        if (arguments.getString(ARG_FILE_PATH) != null) {
            mPath = getArguments().getString(ARG_FILE_PATH);
        }

        mFilter = (FileFilter) getArguments().getSerializable(ARG_FILTER);
    }

    interface FileClickListener {
        void onFileClicked(File clickedFile);
    }
}
