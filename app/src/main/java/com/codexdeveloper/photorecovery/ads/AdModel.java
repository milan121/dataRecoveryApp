package com.codexdeveloper.photorecovery.ads;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdModel {
	@SerializedName("bannerAd")
	@Expose
	String bannerAd;
	@SerializedName("nativeAd")
	@Expose
	String nativeAd;
	@SerializedName("adOpen")
	@Expose
	String adOpen;
	@SerializedName("interstitial")
	@Expose
	String interstitial;
	@SerializedName("rewardedAd")
	@Expose
	String rewardedAd;
	@SerializedName("admobBannerAd")
	@Expose
	String admobBannerAd;
	@SerializedName("admobNativeAd")
	@Expose
	String admobNativeAd;
	@SerializedName("admobAppOpenAd")
	@Expose
	String admobAppOpenAd;
	@SerializedName("admobInterstitial")
	@Expose
	String admobInterstitial;
	@SerializedName("adMobRewardedAd")
	@Expose
	String adMobRewardedAd;
	@SerializedName("adTime")
	@Expose
	int adTime;
	@SerializedName("isAD")
	@Expose
	boolean isAD;
	@SerializedName("isAdmobAd")
	@Expose
	boolean isAdmobAd;
	@SerializedName("isAdShow")
	@Expose
	boolean isAdShow;

	public AdModel(String bannerAd, String nativeAd, String adOpen, String interstitial, String rewardedAd, String admobBannerAd,
				   String admobNativeAd, String admobAppOpenAd, String admobInterstitial, String adMobRewardedAd, int adTime, boolean isAD, boolean isAdmobAd,boolean isAdShow) {
		this.bannerAd = bannerAd;
		this.nativeAd = nativeAd;
		this.adOpen = adOpen;
		this.interstitial = interstitial;
		this.rewardedAd = rewardedAd;
		this.admobBannerAd = admobBannerAd;
		this.admobNativeAd = admobNativeAd;
		this.admobAppOpenAd = admobAppOpenAd;
		this.admobInterstitial = admobInterstitial;
		this.adMobRewardedAd = adMobRewardedAd;
		this.adTime = adTime;
		this.isAD = isAD;
		this.isAdmobAd = isAdmobAd;
		this.isAdShow = isAdShow;
	}

	public boolean isAdmobAd() {
		return isAdmobAd;
	}

	public void setAdmobAd(boolean admobAd) {
		isAdmobAd = admobAd;
	}

	public String getAdmobBannerAd() {
		return admobBannerAd;
	}

	public void setAdmobBannerAd(String admobBannerAd) {
		this.admobBannerAd = admobBannerAd;
	}

	public String getAdmobNativeAd() {
		return admobNativeAd;
	}

	public void setAdmobNativeAd(String admobNativeAd) {
		this.admobNativeAd = admobNativeAd;
	}

	public String getAdmobAppOpenAd() {
		return admobAppOpenAd;
	}

	public void setAdmobAppOpenAd(String admobAppOpenAd) {
		this.admobAppOpenAd = admobAppOpenAd;
	}

	public String getAdmobInterstitial() {
		return admobInterstitial;
	}

	public void setAdmobInterstitial(String admobInterstitial) {
		this.admobInterstitial = admobInterstitial;
	}


	public String getBannerAd() {
		return bannerAd;
	}

	public void setBannerAd(String bannerAd) {
		this.bannerAd = bannerAd;
	}

	public String getNativeAd() {
		return nativeAd;
	}

	public void setNativeAd(String nativeAd) {
		this.nativeAd = nativeAd;
	}

	public String getAdOpen() {
		return adOpen;
	}

	public void setAdOpen(String adOpen) {
		this.adOpen = adOpen;
	}

	public String getInterstitial() {
		return interstitial;
	}

	public void setInterstitial(String interstitial) {
		this.interstitial = interstitial;
	}


	public int getAdTime() {
		return adTime;
	}

	public void setAdTime(int adTime) {
		this.adTime = adTime;
	}

	public boolean isAD() {
		return isAD;
	}

	public void setAD(boolean AD) {
		isAD = AD;
	}

	public String getRewardedAd() {
		return rewardedAd;
	}

	public void setRewardedAd(String rewardedAd) {
		this.rewardedAd = rewardedAd;
	}

	public String getAdMobRewardedAd() {
		return adMobRewardedAd;
	}

	public void setAdMobRewardedAd(String adMobRewardedAd) {
		this.adMobRewardedAd = adMobRewardedAd;
	}

	public boolean isAdShow() {
		return isAdShow;
	}

	public void setAdShow(boolean adShow) {
		isAdShow = adShow;
	}

	@Override
	public String toString() {
		return "AdModel{" +
				"bannerAd='" + bannerAd + '\'' +
				", nativeAd='" + nativeAd + '\'' +
				", adOpen='" + adOpen + '\'' +
				", interstitial='" + interstitial + '\'' +
				", rewardedAd='" + rewardedAd + '\'' +
				", admobBannerAd='" + admobBannerAd + '\'' +
				", admobNativeAd='" + admobNativeAd + '\'' +
				", admobAppOpenAd='" + admobAppOpenAd + '\'' +
				", admobInterstitial='" + admobInterstitial + '\'' +
				", adMobRewardedAd='" + adMobRewardedAd + '\'' +
				", adTime=" + adTime +
				", isAD=" + isAD +
				", isAdmobAd=" + isAdmobAd +
				", isAdShow=" + isAdShow +
				'}';
	}
}
