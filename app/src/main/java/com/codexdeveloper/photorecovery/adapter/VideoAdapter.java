package com.codexdeveloper.photorecovery.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DateFormat;
import java.util.ArrayList;

import com.codexdeveloper.photorecovery.model.VideoModel;
import com.codexdeveloper.photorecovery.R;
import com.codexdeveloper.photorecovery.utills.Utils;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {

    Context context;
    ArrayList<VideoModel> listPhoto;
    BitmapDrawable placeholder;
    Activity aaa;

    public VideoAdapter(Context context, ArrayList<VideoModel> mList, Activity aaa) {
        listPhoto = new ArrayList<>();
        this.context = context;
        this.listPhoto = mList;
        this.aaa = aaa;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_photo, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.tvDate.setText(DateFormat.getDateInstance().format(listPhoto.get(position).getLastModifiedVideo()));
        holder.tvSize.setText(Utils.formatSize(listPhoto.get(position).getSizeVideo()));

        if (!listPhoto.get(position).isCheck()) {
            holder.ivChecked.setVisibility(View.GONE);
        } else {
            holder.ivChecked.setVisibility(View.VISIBLE);
        }
        try {
            Glide.with(context)
                    .load("file://" + listPhoto.get(position).getPathVideo())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .centerCrop()
                    .error(R.drawable.ic_error)
                    .placeholder(placeholder)
                    .into(holder.ivPhoto);
        } catch (Exception e) {
            //do nothing
        }

        holder.albumCardView.setOnClickListener(view -> {
            if (!listPhoto.get(position).isCheck()) {
                holder.ivChecked.setVisibility(View.VISIBLE);
                listPhoto.get(position).setCheck(true);
            } else {
                holder.ivChecked.setVisibility(View.GONE);
                listPhoto.get(position).setCheck(false);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listPhoto.size();
    }

    public ArrayList<VideoModel> getSelectedItem() {
        ArrayList<VideoModel> arrayList = new ArrayList();
        if (this.listPhoto != null) {
            for (int i = 0; i < this.listPhoto.size(); i++) {
                if ((this.listPhoto.get(i)).isCheck()) {
                    arrayList.add(this.listPhoto.get(i));
                }
            }
        }
        return arrayList;
    }


    public void setAllImagesUnseleted() {
        if (this.listPhoto != null) {
            for (int i = 0; i < this.listPhoto.size(); i++) {
                if ((this.listPhoto.get(i)).isCheck()) {
                    (this.listPhoto.get(i)).setCheck(false);
                }
            }
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvSize;
        ImageView ivPhoto;
        ImageView ivChecked;
        CardView albumCardView;

        public MyViewHolder(View view) {
            super(view);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvSize = (TextView) view.findViewById(R.id.tvSize);
            ivPhoto = view.findViewById(R.id.iv_image);
            ivChecked = (ImageView) view.findViewById(R.id.isChecked);
            albumCardView = (CardView) view.findViewById(R.id.album_card);
        }
    }

}
