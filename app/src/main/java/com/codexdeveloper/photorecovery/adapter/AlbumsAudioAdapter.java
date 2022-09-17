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

import com.codexdeveloper.photorecovery.model.AlbumAudio;
import com.codexdeveloper.photorecovery.R;

public class AlbumsAudioAdapter extends RecyclerView.Adapter<AlbumsAudioAdapter.MyViewHolder> {

    Context context;
    ArrayList<AlbumAudio> albumAudios = new ArrayList<>();
    OnClickItemListener mOnClickItemListener;
    Activity aaa;

    public AlbumsAudioAdapter(Context context, ArrayList<AlbumAudio> mList, OnClickItemListener onClickItemListener) {
        this.context = context;
        this.albumAudios = mList;
        mOnClickItemListener = onClickItemListener;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mAudioFileSize;
        TextView filePath;

        OnClickItemListener onClickItemListener;

        public MyViewHolder(View view, OnClickItemListener onClickItemListener) {
            super(view);

            mAudioFileSize = (TextView) view.findViewById(R.id.tv_folder2);
            filePath = (TextView) view.findViewById(R.id.filePath);
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
                .inflate(R.layout.card_album_audio, parent, false);
        return new MyViewHolder(itemView, mOnClickItemListener);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.mAudioFileSize.setText("" + albumAudios.get(position).getListAudio().size() + "\nAudios");
        holder.filePath.setText("" + albumAudios.get(position).getStrAudioFolder());

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

    }

    @Override
    public int getItemCount() {
        return albumAudios.size();
    }

    public interface OnClickItemListener {
        void onClickItem(int position);
    }
}
