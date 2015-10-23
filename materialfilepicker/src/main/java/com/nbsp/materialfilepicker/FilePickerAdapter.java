package com.nbsp.materialfilepicker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

/**
 * Created by nickolay on 10.03.15.
 */

public class FilePickerAdapter extends RecyclerView.Adapter<FilePickerAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private final List<File> mFiles;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mName;
        private final TextView mDescription;
        private final ImageView mIcon;

        public ViewHolder(final View applicationView, final OnItemClickListener listener) {
            super(applicationView);

            mName = (TextView) applicationView.findViewById(R.id.file_name);
            mDescription = (TextView) applicationView.findViewById(R.id.file_description);
            mIcon = (ImageView) applicationView.findViewById(R.id.file_icon);

            if (listener != null) {
                applicationView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClick(applicationView, getAdapterPosition());
                    }
                });
            }
        }
    }

    public FilePickerAdapter(List<File> files) {
        mFiles = files;
    }

    @Override
    public FilePickerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_file, parent, false);

        return new ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        File file = mFiles.get(position);

        holder.mName.setText(file.getName());
        holder.mDescription.setText(file.getName());
        holder.mIcon.setImageResource(R.drawable.ic_folder_grey_700_48dp);
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }
}