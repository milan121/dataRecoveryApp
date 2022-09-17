package com.codexdeveloper.photorecovery.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.codexdeveloper.photorecovery.model.AlbumOthers;
import com.codexdeveloper.photorecovery.R;

public class AlbumsOtherAdapter extends RecyclerView.Adapter<AlbumsOtherAdapter.MyViewHolder> {

    Context context;
    ArrayList<AlbumOthers> albumOthers = new ArrayList<>();
    OnClickItemListener mOnClickItemListener;
    Activity aaa;


    public AlbumsOtherAdapter(Context context, ArrayList<AlbumOthers> mList, OnClickItemListener onClickItemListener) {
        this.context = context;
        this.albumOthers = mList;
        mOnClickItemListener = onClickItemListener;
        Log.e("01122", "intData_2: " + albumOthers);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_album_audio, parent, false);
        return new MyViewHolder(itemView, mOnClickItemListener);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.mOtherFileSize.setText("" + albumOthers.get(position).getListOther().size() + "\nOthers");
        holder.filePath.setText("" + albumOthers.get(position).getStrOtherFolder());

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);


    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mOtherFileSize;
        TextView filePath;

        OnClickItemListener onClickItemListener;

        public MyViewHolder(View view, OnClickItemListener onClickItemListener) {
            super(view);

            mOtherFileSize = (TextView) view.findViewById(R.id.tv_folder2);
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
    public int getItemCount() {
        return albumOthers.size();
    }

    public interface OnClickItemListener {
        void onClickItem(int position);
    }

}
