package com.codexdeveloper.photorecovery.activity;

import android.app.Application;

import com.codexdeveloper.photorecovery.R;
import com.codexdeveloper.photorecovery.ads.AppOpenManager;

public class MyApplication extends Application {

    private static MyApplication mInstance;
    AppOpenManager appOpenManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        appOpenManager = new AppOpenManager(MyApplication.this, getString(R.string.admob_open_ads));

    }

    public static MyApplication getInstance() {
        return mInstance;
    }


}