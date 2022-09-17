package com.codexdeveloper.photorecovery.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import com.codexdeveloper.photorecovery.model.AlbumPhoto;
import com.codexdeveloper.photorecovery.model.PhotoModel;
import com.codexdeveloper.photorecovery.R;

public class AlbumsImageAdapter extends RecyclerView.Adapter<AlbumsImageAdapter.MyViewHolder> {
    Context context;
    ArrayList<AlbumPhoto> albumPhotos = new ArrayList<>();
    OnClickItemListener mOnClickItemListener;
    Activity aaa;

    public AlbumsImageAdapter(Context context, ArrayList<AlbumPhoto> mList, OnClickItemListener onClickItemListener) {
        this.context = context;
        this.albumPhotos = mList;
        mOnClickItemListener = onClickItemListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mPhotoFileSize;
        RecyclerView mImageListView;
        OnClickItemListener onClickItemListener;

        public MyViewHolder(View view, OnClickItemListener onClickItemListener) {
            super(view);
            this.mImageListView = (RecyclerView) view.findViewById(R.id.recycler_view_list);
            mPhotoFileSize = (TextView) view.findViewById(R.id.tv_folder2);
            this.onClickItemListener = onClickItemListener;
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onClickItemListener.onClickItem(getAdapterPosition());
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_album_new, parent, false);
        return new MyViewHolder(itemView, mOnClickItemListener);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.mPhotoFileSize.setText("" + albumPhotos.get(position).getListPhoto().size() + "\nPictures");
        ArrayList<PhotoModel> singleSectionItems = albumPhotos.get(position).getListPhoto();
        SectionListDataAdapterForImages itemListDataAdapter = new SectionListDataAdapterForImages(context, singleSectionItems, position, aaa);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        holder.mImageListView.setLayoutManager(layoutManager);
        holder.mImageListView.setHasFixedSize(true);
        holder.mImageListView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true));
        holder.mImageListView.setAdapter(itemListDataAdapter);


    }

    @Override
    public int getItemCount() {
        return albumPhotos.size();
    }

    public interface OnClickItemListener {
        void onClickItem(int position);
    }

}
