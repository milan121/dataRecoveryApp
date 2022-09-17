package com.codexdeveloper.photorecovery.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

import com.codexdeveloper.photorecovery.ads.AdmobAdsModel;
import com.codexdeveloper.photorecovery.model.OtherModel;
import com.codexdeveloper.photorecovery.R;
import com.codexdeveloper.photorecovery.adapter.OtherAdapter;
import com.codexdeveloper.photorecovery.task.RecoverOthersAsyncTask;

import static com.codexdeveloper.photorecovery.utills.Utils.mAlbumOthers;

public class OtherActivity extends AppCompatActivity {

    int int_position;
    RecyclerView recyclerView;
    TextView btnRestore, btnUnchecked;
    ArrayList<OtherModel> mList = new ArrayList<OtherModel>();
    Activity aaa;
    String status;

    OtherAdapter adapter;
    RecoverOthersAsyncTask mRecoverPhotosAsyncTask;

    MaterialToolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        new AdmobAdsModel(this).bannerAds(this, findViewById(R.id.adsView));

        intView();
        intData();

    }

    public void intView() {

        btnRestore = (TextView) findViewById(R.id.btnRestore);
        btnUnchecked = (TextView) findViewById(R.id.btnUnchecked);
        recyclerView = (RecyclerView) findViewById(R.id.gv_folder);
        toolBar = findViewById(R.id.toolBar);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
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
        if (mAlbumOthers != null && (mAlbumOthers.size() > int_position)) {
            mList.addAll((ArrayList<OtherModel>) mAlbumOthers.get(int_position).getListOther().clone());
//            toolBar.setTitle("" + mAlbumOthers.get(int_position).getStrOtherFolder());
        }

        adapter = new OtherAdapter(this, mList, aaa);
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
            final ArrayList<OtherModel> tempList = adapter.getSelectedItem();

            if (tempList.size() == 0) {
                Toast.makeText(OtherActivity.this, "Cannot restore, all items are unchecked!", Toast.LENGTH_LONG).show();
            } else {

                mRecoverPhotosAsyncTask = new RecoverOthersAsyncTask(OtherActivity.this, adapter.getSelectedItem(), new RecoverOthersAsyncTask.OnRestoreListener() {
                    @Override
                    public void onComplete() {
                        Intent intent = new Intent(getApplicationContext(), RestoreResultActivity.class);
                        intent.putExtra("value", tempList.size());
                        startActivity(intent);
                        finish();
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