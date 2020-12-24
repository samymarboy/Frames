package dev.jahir.frames.ui;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import dev.jahir.frames.R;

public class GBannerLoader {

    public Activity activity;
    private AdRequest adRequest;
    private AdView bannerAdView;
    public static boolean isPremiumBoolean = false;
    public static String adsId = "ca-app-pub-3940256099942544/6300978111";
    public static String interstitialAdsId = "ca-app-pub-3940256099942544/1033173712";
    private View bannerViewWrapper;

    public GBannerLoader(Activity activity, View bannerViewWrapper) {
        this.activity = activity;
        this.bannerViewWrapper = bannerViewWrapper;
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
        adRequest = adRequestBuilder.build();
    }

    public void showBanner(LinearLayout linearLayout) {
        if (isPremiumBoolean) {
            bannerAdView = null;
            return;
        }

        AdSize adSize = getAdSize();
        setDefaultViewHeight(adSize);
        bannerAdView = new AdView(activity);
        bannerAdView.setAdSize(adSize);

        bannerAdView.setAdUnitId(adsId);
        bannerAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (bannerAdView != null)
                    bannerAdView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int error) {
                if (bannerAdView != null)
                    bannerAdView.setVisibility(View.GONE);
            }
        });
        bannerAdView.loadAd(adRequest);
        linearLayout.removeAllViews();
        linearLayout.addView(bannerAdView);
    }

    private void setDefaultViewHeight(AdSize adSize) {
        try {
            if (bannerViewWrapper != null && adSize != null) {
                TextView textView = bannerViewWrapper.findViewById(R.id.ad_empty_textview);
                if (textView != null && adSize.getHeight() > 50 && adSize.getHeight() < 100) {
                    ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) textView.getLayoutParams();
                    params.height = convertDpToPx(adSize.getHeight());
                    textView.setLayoutParams(params);
                }
            }
        } catch (Exception e) {
            Log.e("error-message", e.getMessage());
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private AdSize getAdSize() {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }

    public void onPause() {
        try {
            if (bannerAdView != null) {
                bannerAdView.pause();
            }
        } catch (Exception e) {
            Log.e("error-message", e.getMessage());
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void resume() {
        try {
            if (bannerAdView != null) {
                bannerAdView.resume();
            }
        } catch (Exception e) {
            Log.e("error-message", e.getMessage());
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void destroy() {
        try {
            if (bannerAdView != null) {
                bannerAdView.destroy();
            }
            activity = null;
        } catch (Exception e) {
            Log.e("error-message", e.getMessage());
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public int convertDpToPx(int dp) {
        return (int) (dp * activity.getResources().getDisplayMetrics().density);
    }

}
