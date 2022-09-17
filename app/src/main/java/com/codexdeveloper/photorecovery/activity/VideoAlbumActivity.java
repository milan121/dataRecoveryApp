package com.codexdeveloper.photorecovery.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.appbar.MaterialToolbar;

import com.codexdeveloper.photorecovery.R;
import com.codexdeveloper.photorecovery.adapter.AlbumsVideoAdapter;
import com.codexdeveloper.photorecovery.ads.AdmobAdsModel;

import static com.codexdeveloper.photorecovery.utills.Utils.mAlbumVideos;

public class VideoAlbumActivity extends AppCompatActivity implements AlbumsVideoAdapter.OnClickItemListener {

    RecyclerView recyclerView;
    AlbumsVideoAdapter adapter;

    MaterialToolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_album);

        new AdmobAdsModel(this).bannerAds(this, findViewById(R.id.adsView));
        intView();
        intData();
    }


    public void intView() {

        recyclerView = (RecyclerView) findViewById(R.id.gv_folder);
        toolBar = findViewById(R.id.toolBar);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        toolBar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    public void intData() {
        Log.e("01122", "intData: " + mAlbumVideos.size());
        adapter = new AlbumsVideoAdapter(VideoAlbumActivity.this, mAlbumVideos, VideoAlbumActivity.this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickItem(int position) {

        Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
        intent.putExtra("value", position);
        startActivity(intent);
    }
}