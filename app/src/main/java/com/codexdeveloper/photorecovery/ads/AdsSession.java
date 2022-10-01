package com.codexdeveloper.photorecovery.ads;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class AdsSession {
    Context context;
    SharedPreferences preferences;
    public static final String BANNER = "Banner";
    public static final String APPOPEN = "AppOpen";
    public static final String INTERSTITIAL = "Interstitial";
    public static final String REWARDED = "Rewarded";
    public static final String NATIVE = "Native";

    public AdsSession(Context context) {
        this.context = context;
        try {
            preferences = context.getSharedPreferences("AdsPre", Context.MODE_PRIVATE);
        }catch (Exception e){
            Log.i("TAG", "setAdsIds: adsId : ");
        }
    }

    public void setAdsIds(String adsType, String adsId) {
        Log.i("TAG", "setAdsIds: adsId : " + adsId);
        preferences.edit().putString(adsType, adsId).apply();
    }

    public String getAdsIds(String adsType) {
        if (preferences!=null && preferences.getString(adsType, "")!=null) {
            return preferences.getString(adsType, "");
        }else return "";
    }
}
