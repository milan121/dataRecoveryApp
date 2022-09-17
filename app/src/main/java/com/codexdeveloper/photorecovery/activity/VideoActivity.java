package com.codexdeveloper.photorecovery.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

import com.codexdeveloper.photorecovery.ads.AdmobAdsModel;
import com.codexdeveloper.photorecovery.model.VideoModel;
import com.codexdeveloper.photorecovery.R;
import com.codexdeveloper.photorecovery.adapter.VideoAdapter;
import com.codexdeveloper.photorecovery.task.RecoverVideosAsyncTask;

import static com.codexdeveloper.photorecovery.utills.Utils.mAlbumVideos;

public class VideoActivity extends AppCompatActivity {

    int int_position;
    RecyclerView recyclerView;
    VideoAdapter adapter;
    TextView btnRestore, btnUnchecked;
    ArrayList<VideoModel> mList = new ArrayList<VideoModel>();
    RecoverVideosAsyncTask recoverVideosAsyncTask;
    LinearLayout ll_back;
    Activity aaa;
    String status;

    MaterialToolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        new AdmobAdsModel(this).bannerAds(this, findViewById(R.id.adsView));
        intView();
        intData();
    }

    public void intView() {

        btnRestore = (TextView) findViewById(R.id.btnRestore);
        btnUnchecked = (TextView) findViewById(R.id.btnUnchecked);
        recyclerView = (RecyclerView) findViewById(R.id.gv_folder);
        toolBar = findViewById(R.id.toolBar);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        toolBar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    public void intData() {
        int_position = getIntent().getIntExtra("value", 0);
        if (mAlbumVideos != null && (mAlbumVideos.size() > int_position)) {
            mList.addAll((ArrayList<VideoModel>) mAlbumVideos.get(int_position).getListVideo().clone());
//            toolBar.setTitle("" + mAlbumVideos.get(int_position).getStrVideoFolder());
        }

        adapter = new VideoAdapter(this, mList, aaa);
        recyclerView.setAdapter(adapter);
        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                status = "restore";
                gotonext();
            }
        });

        btnUnchecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adapter.setAllImagesUnseleted();
                adapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onBackPressed() {
        status = "back";
        gotonext();
    }


    private void gotonext() {

        if (status.equals("restore")) {
            final ArrayList<VideoModel> tempList = adapter.getSelectedItem();


            if (tempList.size() == 0) {
                Toast.makeText(VideoActivity.this, "Cannot restore, all items are unchecked!", Toast.LENGTH_LONG).show();
            } else {

                recoverVideosAsyncTask = new RecoverVideosAsyncTask(VideoActivity.this, adapter.getSelectedItem(), new RecoverVideosAsyncTask.OnRestoreListener() {
                    @Override
                    public void onComplete() {
                        Intent intent = new Intent(getApplicationContext(), RestoreResultActivity.class);
                        intent.putExtra("value", tempList.size());
                        startActivity(intent);
                        adapter.setAllImagesUnseleted();
                        adapter.notifyDataSetChanged();
                    }
                });
                recoverVideosAsyncTask.execute();
            }
        } else if (status.equals("back")) {
            finish();
        }
    }


}