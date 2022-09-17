package com.codexdeveloper.photorecovery.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

import com.codexdeveloper.photorecovery.BuildConfig;
import com.codexdeveloper.photorecovery.R;
import com.codexdeveloper.photorecovery.ads.AdmobAdsModel;
import com.codexdeveloper.photorecovery.utills.Utils;

import static android.os.Build.VERSION.SDK_INT;

public class MainActivity extends AppCompatActivity {

    TextView startBtn, totalImage, totalVideo, totalAudio, totalOther;

    LinearLayout allImages, allVideos, allAudios, allOthers;

    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    MaterialToolbar toolBar;

    @Override
    protected void onResume() {
        new AdmobAdsModel(this).interstitialAdLoad(this);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AdmobAdsModel(this).bannerAds(this, findViewById(R.id.adsView));

        startBtn = findViewById(R.id.startBtn);
        totalImage = findViewById(R.id.totalImage);
        totalVideo = findViewById(R.id.totalVideo);
        totalAudio = findViewById(R.id.totalAudio);
        totalOther = findViewById(R.id.totalOther);
        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        toolBar.inflateMenu(R.menu.toolbar_menu);

        allImages = findViewById(R.id.allImages);
        allVideos = findViewById(R.id.allVideos);
        allAudios = findViewById(R.id.allAudios);
        allOthers = findViewById(R.id.allOthers);

        startBtn.setOnClickListener(v -> {
            permission();
        });

        totalImage.setText(Utils.noOfImage + " Files");
        totalVideo.setText(Utils.noOfVideo + " Files");
        totalAudio.setText(Utils.noOfAudio + " Files");
        totalOther.setText(Utils.noOfOther + " Files");

        allImages.setOnClickListener(v -> {
            new AdmobAdsModel(this).interstitialAdShow(this, () -> {
                if (Utils.mAlbumPhotos.size() != 0) {
                    Intent intent = new Intent(getApplicationContext(), AlbumActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Please Start Scanning", Toast.LENGTH_SHORT).show();
                }
            });
        });

        allVideos.setOnClickListener(v -> {
            new AdmobAdsModel(this).interstitialAdShow(this, () -> {
                if (Utils.mAlbumVideos.size() != 0) {
                    Intent intent = new Intent(getApplicationContext(), VideoAlbumActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Please Start Scanning", Toast.LENGTH_SHORT).show();
                }
            });

        });

        allAudios.setOnClickListener(v -> {
            new AdmobAdsModel(this).interstitialAdShow(this, () -> {
                if (Utils.mAlbumAudios.size() != 0) {
                    Intent intent = new Intent(getApplicationContext(), AudioAlbumActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Please Start Scanning", Toast.LENGTH_SHORT).show();
                }
            });

        });

        allOthers.setOnClickListener(v -> {
            new AdmobAdsModel(this).interstitialAdShow(this, () -> {
                if (Utils.mAlbumOthers.size() != 0) {
                    Intent intent = new Intent(getApplicationContext(), OtherAlbumActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Please Start Scanning", Toast.LENGTH_SHORT).show();
                }
            });

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        Log.d("debug", "activity : onCreateOptionsMenu");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:

                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
            default:
                //Do Something
                break;
        }
        return true;
    }

    void permission() {

        if (SDK_INT >= 30) {
            if (!Environment.isExternalStorageManager()) {
                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), getString(R.string.app_name) + " required permissions to access all files.Please go to Setting and enable the 'all files access' then go back ", Snackbar.LENGTH_INDEFINITE);

                Snackbar.SnackbarLayout params = (Snackbar.SnackbarLayout) snack.getView();
                params.setMinimumHeight(150);
                snack.setAction("Settings", v -> {
                    try {
                        Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
                        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri);
                        startActivity(intent);
                    } catch (Exception ex) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                        startActivity(intent);
                    }
                });
                snack.show();
            } else {
                new AdmobAdsModel(this).interstitialAdShow(this, () -> {
                    startActivity(new Intent(MainActivity.this, ScanningActivity.class));

                });
            }
        } else {

            //And finally ask for the permission
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                //File write logic here
                new AdmobAdsModel(this).interstitialAdShow(this, () -> {
                    startActivity(new Intent(MainActivity.this, ScanningActivity.class));
                });
            } else {
                ActivityCompat.requestPermissions(this, permissions, 101);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 101:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(MainActivity.this, ScanningActivity.class));
                } else {

                    //Do Something
                }
                break;
        }

    }

}