package com.codexdeveloper.photorecovery.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.codexdeveloper.photorecovery.BuildConfig;
import com.codexdeveloper.photorecovery.R;
import com.codexdeveloper.photorecovery.utills.Config;
import com.google.android.material.appbar.MaterialToolbar;

public class AboutUsActivity extends AppCompatActivity {
    TextView version;
    MaterialToolbar toolBar;

    public static void start(Context context) {
        Intent starter = new Intent(context, AboutUsActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        version = findViewById(R.id.version);
        version.setText("Version: " + BuildConfig.VERSION_NAME);
        toolBar = findViewById(R.id.toolBar);
        toolBar.setNavigationOnClickListener(v -> {
            finish();
        });

    }
}