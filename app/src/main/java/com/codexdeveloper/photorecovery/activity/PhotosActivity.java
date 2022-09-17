package com.codexdeveloper.photorecovery.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

import com.codexdeveloper.photorecovery.ads.AdmobAdsModel;
import com.codexdeveloper.photorecovery.model.PhotoModel;
import com.codexdeveloper.photorecovery.R;
import com.codexdeveloper.photorecovery.adapter.PhotoAdapter;
import com.codexdeveloper.photorecovery.task.RecoverPhotosAsyncTask;

import static com.codexdeveloper.photorecovery.utills.Utils.mAlbumPhotos;
import static com.codexdeveloper.photorecovery.utills.Utils.mHiddenFiles;

public class PhotosActivity extends AppCompatActivity {

    int int_position;
    boolean AllFiles;
    RecyclerView recyclerView;
    PhotoAdapter adapter;
    TextView btnRestore, btnUnchecked;
    ArrayList<PhotoModel> mList = new ArrayList<PhotoModel>();
    RecoverPhotosAsyncTask mRecoverPhotosAsyncTask;
    LinearLayout ll_back;
    Activity aaa;
    String status;
    MaterialToolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

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

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void intData() {
        int_position = getIntent().getIntExtra("value", 0);
        AllFiles = getIntent().getBooleanExtra("all_files", true);
        Log.e("tag", "all_files-->2: " + AllFiles);

        if (AllFiles) {
            if (mAlbumPhotos != null && (mAlbumPhotos.size() > int_position)) {
                mList.addAll((ArrayList<PhotoModel>) mAlbumPhotos.get(int_position).getListPhoto().clone());
//                toolBar.setTitle("" + mAlbumPhotos.get(int_position).getStrFolder());
            }
        } else {

            if (mHiddenFiles != null && (mHiddenFiles.size() > int_position)) {
                mList.addAll((ArrayList<PhotoModel>) mHiddenFiles.get(int_position).getListPhoto().clone());
//                toolBar.setTitle("" + mHiddenFiles.get(int_position).getStrFolder());
            }

        }


        adapter = new PhotoAdapter(this, mList, aaa);
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
            final ArrayList<PhotoModel> tempList = adapter.getSelectedItem();

            if (tempList.size() == 0) {
                Toast.makeText(PhotosActivity.this, "Cannot restore, all items are unchecked!", Toast.LENGTH_LONG).show();
            } else {

                mRecoverPhotosAsyncTask = new RecoverPhotosAsyncTask(PhotosActivity.this, adapter.getSelectedItem(), new RecoverPhotosAsyncTask.OnRestoreListener() {
                    @Override
                    public void onComplete() {
                        Intent intent = new Intent(getApplicationContext(), RestoreResultActivity.class);
                        intent.putExtra("value", tempList.size());
                        startActivity(intent);
                        adapter.setAllImagesUnseleted();
                        adapter.notifyDataSetChanged();
                    }
                });
                mRecoverPhotosAsyncTask.execute();
            }
        } else if (status.equals("back")) {
            finish();
        }
    }


}