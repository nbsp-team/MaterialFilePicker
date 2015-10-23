package com.nbsp.materialfilepicker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by nickolay on 16.03.15.
 */

public class FilePickerFragment extends Fragment {

    private RecyclerView mRecyclerView;

    public static FilePickerFragment newInstance() {
        Bundle arguments = new Bundle();

        FilePickerFragment filePickerFragment = new FilePickerFragment();
        filePickerFragment.setArguments(arguments);

        return filePickerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return null;
    }
}


