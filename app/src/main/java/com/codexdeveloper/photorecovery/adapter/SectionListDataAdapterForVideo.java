package com.codexdeveloper.photorecovery.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import com.codexdeveloper.photorecovery.model.VideoModel;
import com.codexdeveloper.photorecovery.R;
import com.codexdeveloper.photorecovery.activity.VideoActivity;
import com.codexdeveloper.photorecovery.view.SquareImageView;

public class SectionListDataAdapterForVideo extends RecyclerView.Adapter<SectionListDataAdapterForVideo.SingleItemRowHolder> {

    private ArrayList<VideoModel> itemsList;
    private Context mContext;
    int size;
    int postion;
    Activity aaa;


    public SectionListDataAdapterForVideo(Context context, ArrayList<VideoModel> itemsList, int mPostion, Activity aaa) {
        this.itemsList = itemsList;

        this.mContext = context;
        postion = mPostion;
        this.aaa = aaa;
        if (itemsList.size() >= 30) {
            size = 30;
        } else {
            size = itemsList.size();
        }
    }

    @NonNull

    @Override
    public SingleItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image, viewGroup, false);
        return new SingleItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionListDataAdapterForVideo.SingleItemRowHolder holder, int position) {

        VideoModel singleItem = itemsList.get(position);

        try {
            Glide.with(mContext)
                    .asBitmap()
                    .load("file://" + singleItem.getPathVideo())
                    .override(50, 50)
                    .error(R.drawable.ic_error)
                    .into(holder.itemImage);
        } catch (Exception e) {
            //do nothing
            Toast.makeText(mContext, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        holder.itemImage.setOnClickListener(view -> gotonext());

    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected SquareImageView itemImage;

        public SingleItemRowHolder(View view) {
            super(view);

            this.itemImage = (SquareImageView) view.findViewById(R.id.ivImage);
        }
    }

    private void gotonext() {
        Intent intent = new Intent(mContext, VideoActivity.class);
        intent.putExtra("value", postion);
        mContext.startActivity(intent);

    }
}
