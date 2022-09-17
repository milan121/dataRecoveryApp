package com.codexdeveloper.photorecovery.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.codexdeveloper.photorecovery.model.AlbumVideo;
import com.codexdeveloper.photorecovery.model.VideoModel;
import com.codexdeveloper.photorecovery.R;

public class AlbumsVideoAdapter extends RecyclerView.Adapter<AlbumsVideoAdapter.MyViewHolder> {

    Context context;
    ArrayList<AlbumVideo> albumVideos = new ArrayList<>();
    OnClickItemListener mOnClickItemListener;
    Activity aaa;


    public AlbumsVideoAdapter(Context context, ArrayList<AlbumVideo> mList, OnClickItemListener onClickItemListener) {
        this.context = context;
        this.albumVideos = mList;
        mOnClickItemListener = onClickItemListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_album_new, parent, false);
        return new MyViewHolder(itemView, mOnClickItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumsVideoAdapter.MyViewHolder holder, int position) {

        holder.mVideoFileSize.setText("" + albumVideos.get(position).getListVideo().size()+"\nVideos");
        ArrayList<VideoModel> singleSectionItems = albumVideos.get(position).getListVideo();

        SectionListDataAdapterForVideo itemListDataAdapter = new SectionListDataAdapterForVideo(context, singleSectionItems, position, aaa);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        holder.mVideoListView.setLayoutManager(layoutManager);
        holder.mVideoListView.setHasFixedSize(true);
        holder.mVideoListView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true));
        holder.mVideoListView.setAdapter(itemListDataAdapter);

    }

    @Override
    public int getItemCount() {
        return albumVideos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mVideoFileSize;
        RecyclerView mVideoListView;
        OnClickItemListener onClickItemListener;

        public MyViewHolder(View view, OnClickItemListener onClickItemListener) {
            super(view);
            this.mVideoListView = (RecyclerView) view.findViewById(R.id.recycler_view_list);
            mVideoFileSize = (TextView) view.findViewById(R.id.tv_folder2);
            this.onClickItemListener = onClickItemListener;
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            onClickItemListener.onClickItem(getAdapterPosition());
        }
    }

    public interface OnClickItemListener {
        void onClickItem(int position);
    }


}
