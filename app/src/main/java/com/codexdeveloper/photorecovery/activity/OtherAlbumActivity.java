package com.codexdeveloper.photorecovery.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.appbar.MaterialToolbar;

import com.codexdeveloper.photorecovery.R;
import com.codexdeveloper.photorecovery.adapter.AlbumsOtherAdapter;
import com.codexdeveloper.photorecovery.ads.AdmobAdsModel;

import static com.codexdeveloper.photorecovery.utills.Utils.mAlbumOthers;

public class OtherAlbumActivity extends AppCompatActivity implements AlbumsOtherAdapter.OnClickItemListener {

    RecyclerView recyclerView;
    AlbumsOtherAdapter adapter;

    MaterialToolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_album);

        new AdmobAdsModel(this).bannerAds(this, findViewById(R.id.adsView));

        intView();
        intData();

    }

    public void intView() {

        recyclerView = (RecyclerView) findViewById(R.id.gv_folder);
        toolBar = findViewById(R.id.toolBar);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        toolBar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    public void intData() {
        Log.e("01122", "intData: " + mAlbumOthers.size());
        adapter = new AlbumsOtherAdapter(OtherAlbumActivity.this, mAlbumOthers, OtherAlbumActivity.this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onClickItem(int position) {

        Intent intent = new Intent(getApplicationContext(), OtherActivity.class);
        intent.putExtra("value", position);
        startActivity(intent);

    }
}