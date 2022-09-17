package com.codexdeveloper.photorecovery.ads;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import com.codexdeveloper.photorecovery.R;


public class AdmobAdsModel {

    Context context;
    static InterstitialAd mInterstitialAd;
    String mTag = "AdmobAdsModel";

    public AdmobAdsModel(Context context) {
        this.context = context;
    }

    public void bannerAds(Context context, ViewGroup frameLayout) {

        AdView adView = new AdView(context);
        adView.setAdUnitId(context.getString(R.string.admob_banner_ads));
        adView.setAdSize(getAdSize((Activity) context));
        frameLayout.addView(adView);
        AdRequest build = new AdRequest.Builder().build();
        adView.loadAd(build);


        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Log.e(mTag, "onAdClosed: ");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d(mTag, "onAdFailedToLoad: Banner Ads " + loadAdError.getMessage());
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                Log.e(mTag, "onAdOpened: ");
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.e(mTag, "onAdLoaded: ");
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                Log.e(mTag, "onAdClicked: ");
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                Log.e(mTag, "onAdImpression: ");
            }
        });

    }

    private static AdSize getAdSize(Activity activity) {
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, (int) (displayMetrics.widthPixels / displayMetrics.density));
    }

    public void interstitialAdLoad(Context context) {


        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(context, context.getString(R.string.admob_interstitial_ads), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                mInterstitialAd = interstitialAd;
                Log.d(mTag, "onAdLoaded: ");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d(mTag, "onAdFailedToLoad: " + loadAdError.getMessage());
            }
        });
    }

    public void interstitialAdShow(Activity context, final GetBackPointer getBackPointer) {

        if (mInterstitialAd != null) {
            mInterstitialAd.show(context);

            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    Log.d(mTag, "onAdFailedToShowFullScreenContent: " + adError.getMessage());
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent();
                    Log.d(mTag, "onAdShowedFullScreenContent: ");

                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    Log.d(mTag, "onAdDismissedFullScreenContent: ");
                    interstitialAdLoad(context);
                    if (getBackPointer != null) {
                        getBackPointer.returnAction();
                    }
                }

                @Override
                public void onAdImpression() {
                    super.onAdImpression();
                    Log.d(mTag, "onAdImpression: ");
                }
            });
        } else {
            if (getBackPointer != null) {
                getBackPointer.returnAction();
            }
        }


    }

    public interface GetBackPointer {
        public void returnAction();
    }


}
