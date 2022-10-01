package com.codexdeveloper.photorecovery.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.codexdeveloper.photorecovery.BuildConfig;
import com.codexdeveloper.photorecovery.ads.AdModel;
import com.codexdeveloper.photorecovery.ads.AdsSession;
import com.codexdeveloper.photorecovery.ads.AppOpenManager;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyApplication extends Application implements LifecycleObserver, Application.ActivityLifecycleCallbacks {
    public static Context context;
    /* access modifiers changed from: private */
    public static ProgressDialog progressDialog;
    private static AppOpenManager appOpenManager;

    private static final String CHANNEL_ID = "mivan";
    private static final String TAG = "App";
    public static AppOpenAdManager appOpenAdManager;
    public Activity mActivity;
    static List<AdModel> lessons = new ArrayList<>();
    /* access modifiers changed from: private */

    public static AdModel adModel;
    public static int adTime = 1;
    @SuppressLint("StaticFieldLeak")
    protected static MyApplication instance;

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context2) {
        super.attachBaseContext(context2);
    }

    public static MyApplication getApp() {
        return instance;
    }

    public void onCreate() {
        super.onCreate();
        instance = this;
        MobileAds.initialize(this);
        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().setTestDeviceIds(Collections.singletonList("07FB62FF68E7F95261E5BFEC3873072A")).build());

        context = getApplicationContext();

        MobileAds.initialize(
                this,
                initializationStatus -> {
                });

        appOpenManager = new AppOpenManager(this, new AdsSession(this));

    }

    public static synchronized Application getInstance(Activity activity) {
        instance.mActivity = activity;
        instance.getConfig();
        return instance;
    }

    public static synchronized Application getInstance() {
        return instance;
    }

    private void getConfig() {

//        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
//        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
//                .setMinimumFetchIntervalInSeconds(3600)
//                .build();
//        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
//        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);

        // grab an instance of Firebase Remote Config
        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        // set the local default configuration for fallback
//        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);

        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(BuildConfig.DEBUG ? 0 : TimeUnit.HOURS.toSeconds(0))
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);

        mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(mActivity, task -> {
            if (task.isSuccessful()) {
//                boolean updated = task.getResult();
//                mFirebaseRemoteConfig.activate();

                Log.e("LOG_TAG", "Config params updated: " + task.getResult());
                lessons.clear();


                String object = mFirebaseRemoteConfig.getString("adModel");

                Gson gson = new GsonBuilder().create();
                lessons = gson.fromJson(object, new TypeToken<List<AdModel>>() {
                }.getType());
                adModel = lessons.get(0);
                Log.i(TAG, "getConfig: adModel : " + adModel);

                AdsSession session = new AdsSession(getApplicationContext());
                if (adModel != null)
                    if (adModel.isAdShow()) {
                        if (adModel.isAD()) {
                            appOpenAdManager = new AppOpenAdManager();
                            onMoveToForeground();
                            adTime = adModel.getAdTime();
                            session.setAdsIds(AdsSession.BANNER, adModel.getBannerAd());
                            session.setAdsIds(AdsSession.NATIVE, adModel.getNativeAd());
                            session.setAdsIds(AdsSession.INTERSTITIAL, adModel.getInterstitial());
                            session.setAdsIds(AdsSession.REWARDED, adModel.getRewardedAd());
                            session.setAdsIds(AdsSession.APPOPEN, adModel.getAdOpen());
                        } else if (adModel.isAdmobAd()) {
                            session.setAdsIds(AdsSession.BANNER, adModel.getAdmobBannerAd());
                            session.setAdsIds(AdsSession.NATIVE, adModel.getAdmobNativeAd());
                            session.setAdsIds(AdsSession.INTERSTITIAL, adModel.getAdmobInterstitial());
                            session.setAdsIds(AdsSession.REWARDED, adModel.getAdMobRewardedAd());
                            session.setAdsIds(AdsSession.APPOPEN, adModel.getAdmobAppOpenAd());
                        }
                    } else {
                        session.setAdsIds(AdsSession.BANNER, "");
                        session.setAdsIds(AdsSession.NATIVE, "");
                        session.setAdsIds(AdsSession.INTERSTITIAL, "");
                        session.setAdsIds(AdsSession.REWARDED, "");
                        session.setAdsIds(AdsSession.APPOPEN, "");
                    }
            }
        });
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        // An ad activity is started when an ad is showing, which could be AdActivity class from Google
        // SDK or another activity class implemented by a third party mediation partner. Updating the
        // currentActivity only when an ad is not showing will ensure it is not an ad activity, but the
        // one that shows the ad.
        if (appOpenAdManager != null)
            if (!appOpenAdManager.isShowingAd) {
                mActivity = activity;
            }
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        mActivity = activity;
        Log.e("OnActivityResume", "Called");
        getConfig();
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        mActivity = null;
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected void onMoveToForeground() {
        if (appOpenAdManager != null)
            // Show the ad (if available) when the app moves to foreground.
            appOpenAdManager.showAdIfAvailable(mActivity);
    }

    /**
     * Interface definition for a callback to be invoked when an app open ad is complete
     * (i.e. dismissed or fails to show).
     */
    public interface OnShowAdCompleteListener {
        void onShowAdComplete();
    }

    private static class AppOpenAdManager {

        private static final String LOG_TAG = "AppOpenAdManager";
        private final String AD_UNIT_ID = adModel.getAdOpen();

        private AppOpenAd appOpenAd = null;
        private boolean isLoadingAd = false;
        private boolean isShowingAd = false;

        /**
         * Keep track of the time an app open ad is loaded to ensure you don't show an expired ad.
         */
        private long loadTime = 0;

        /**
         * Constructor.
         */
        public AppOpenAdManager() {
        }

        /**
         * Load an ad.
         *
         * @param context the context of the activity that loads the ad
         */
        private void loadAd(Context context) {
            // Do not load ad if there is an unused ad or one is already loading.
            if (isLoadingAd || isAdAvailable()) {
                return;
            }

            isLoadingAd = true;
            AdRequest request = new AdRequest.Builder().build();
            AppOpenAd.load(
                    context,
                    AD_UNIT_ID,
                    request,
                    AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                    new AppOpenAd.AppOpenAdLoadCallback() {
                        /**
                         * Called when an app open ad has loaded.
                         *
                         * @param ad the loaded app open ad.
                         */
                        @Override
                        public void onAdLoaded(@NonNull AppOpenAd ad) {
                            appOpenAd = ad;
                            isLoadingAd = false;
                            loadTime = (new Date()).getTime();

                            Log.d(LOG_TAG, "onAdLoaded.");
                        }

                        /**
                         * Called when an app open ad has failed to load.
                         *
                         * @param loadAdError the error.
                         */
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            isLoadingAd = false;
                            Log.e(LOG_TAG, "onAdFailedToLoad: " + loadAdError.getMessage());
                        }
                    });
        }

        /**
         * Check if ad was loaded more than n hours ago.
         */
        private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
            long dateDifference = (new Date()).getTime() - loadTime;
            long numMilliSecondsPerHour = 3600000;
            return (dateDifference < (numMilliSecondsPerHour * numHours));
        }

        /**
         * Check if ad exists and can be shown.
         */
        private boolean isAdAvailable() {
            // Ad references in the app open beta will time out after four hours, but this time limit
            // may change in future beta versions. For details, see:
            // https://support.google.com/admob/answer/9341964?hl=en
            return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
        }

        /**
         * Show the ad if one isn't already showing.
         *
         * @param activity the activity that shows the app open ad
         */
        private void showAdIfAvailable(@NonNull final Activity activity) {
            showAdIfAvailable(activity, () -> {
            });
        }

        /**
         * Show the ad if one isn't already showing.
         *
         * @param activity                 the activity that shows the app open ad
         * @param onShowAdCompleteListener the listener to be notified when an app open ad is complete
         */
        private void showAdIfAvailable(@NonNull final Activity activity, @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
            // If the app open ad is already showing, do not show the ad again.
            if (isShowingAd) {
                Log.e(LOG_TAG, "The app open ad is already showing.");
                return;
            }

            // If the app open ad is not available yet, invoke the callback then load the ad.
            if (!isAdAvailable()) {
                onShowAdCompleteListener.onShowAdComplete();
                loadAd(activity);
                return;
            }

            Log.e(LOG_TAG, "Will show ad.");

            appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                /** Called when full screen content is dismissed. */
                @Override
                public void onAdDismissedFullScreenContent() {
                    // Set the reference to null so isAdAvailable() returns false.
                    appOpenAd = null;
                    isShowingAd = false;

                    Log.e(LOG_TAG, "onAdDismissedFullScreenContent.");

                    onShowAdCompleteListener.onShowAdComplete();
                    loadAd(activity);
                }

                /** Called when fullscreen content failed to show. */
                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    appOpenAd = null;
                    isShowingAd = false;

                    Log.e(LOG_TAG, "onAdFailedToShowFullScreenContent: " + adError.getMessage());

                    onShowAdCompleteListener.onShowAdComplete();
                    loadAd(activity);
                }

                /** Called when fullscreen content is shown. */
                @Override
                public void onAdShowedFullScreenContent() {
                    Log.e(LOG_TAG, "onAdShowedFullScreenContent.");
                }
            });

            isShowingAd = true;
            appOpenAd.show(activity);
        }
    }

}
