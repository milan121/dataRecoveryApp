package com.codexdeveloper.photorecovery.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.codexdeveloper.photorecovery.R;
import com.google.android.material.appbar.MaterialToolbar;

public class PrivacyPolicyActivity extends AppCompatActivity {
    WebView web;
    MaterialToolbar toolBar;

    public static void start(Context context) {
        Intent starter = new Intent(context, PrivacyPolicyActivity.class);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        web = findViewById(R.id.web);
        web.loadUrl("file:///android_asset/policy.html");
        toolBar = findViewById(R.id.toolBar);
        toolBar.setNavigationOnClickListener(v -> {
            finish();
        });
    }
}