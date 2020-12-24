package dev.jahir.frames.ui;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import static dev.jahir.frames.ui.GBannerLoader.adsId;
import static dev.jahir.frames.ui.GBannerLoader.interstitialAdsId;

public class GInterstitialLoader {

    Activity activity;
    InterstitialAd mInterstitialAd;
    AdRequest.Builder adRequestBuilder;

    public GInterstitialLoader(Activity activity) {
        this.activity = activity;
        this.mInterstitialAd = new InterstitialAd(activity);
        this.mInterstitialAd.setAdUnitId(interstitialAdsId);
        this.adRequestBuilder = new AdRequest.Builder();
    }

    public void showFullscreenAds() {
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
                Toast.makeText(activity, "The interstitial is loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
            }
        });
        mInterstitialAd.loadAd(adRequestBuilder.build());
    }
}