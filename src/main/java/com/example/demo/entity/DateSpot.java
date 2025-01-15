package com.example.demo.entity;

// デートスポットを表すエンティティクラス
public class DateSpot {

    private Long spotId;  // スポットID（データベースの主キー）
    private String spotName;  // デートスポット名
    private String description;  // デートスポットの説明（カテゴリなど）
    private Long spotType; //スポットの種類（飲食店、公園など）
    private String spotAddress;  // スポットの住所
    private double latitude;  // 緯度
    private double longitude;  // 経度
    
    // 営業時間（曜日ごとの営業時間を追加）
    private String Monday; 
    private String Tuesday;
    private String Wednesday;
    private String Thursday;
    private String Friday;
    private String Saturday;
    private String Sunday;

    // ゲッターとセッター（プロパティアクセス用）

    // スポットIDの取得
    public Long getSpotId() {
        return spotId;
    }

    // スポットIDの設定
    public void setSpotId(Long spotId) {
        this.spotId = spotId;
    }

    // スポット名の取得
    public String getSpotName() {
        return spotName;
    }

    // スポット名の設定
    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    // スポット説明の取得
    public String getDescription() {
        return description;
    }

    // スポット説明の設定
    public void setDescription(String description) {
        this.description = description;
    }
    
    //スポットの種類（晴れ、雨、曇り、全て可能など）
    public Long getSpotType() {
    	return spotType;
    }
    
    //スポット種類の取得
    public void setSpotType(Long spotType) {
    	this.spotType = spotType;
    }

    // スポット住所の取得
    public String getSpotAddress() {
        return spotAddress;
    }

    // スポット住所の設定
    public void setSpotAddress(String spotAddress) {
        this.spotAddress = spotAddress;
    }

    // 緯度の取得
    public double getLatitude() {
        return latitude;
    }

    // 緯度の設定
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    // 経度の取得
    public double getLongitude() {
        return longitude;
    }

    // 経度の設定
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // 各曜日の営業時間の取得と設定
    public String getMonday() {
        return Monday;
    }

    public void setMonday(String Monday) {
        this.Monday = Monday;
    }

    public String getTuesday() {
        return Tuesday;
    }

    public void setTuesday(String Tuesday) {
        this.Tuesday = Tuesday;
    }

    public String getWednesday() {
        return Wednesday;
    }

    public void setWednesday(String Wednesday) {
        this.Wednesday = Wednesday;
    }

    public String getThursday() {
        return Thursday;
    }

    public void setThursday(String Thursday) {
        this.Thursday = Thursday;
    }

    public String getFriday() {
        return Friday;
    }

    public void setFriday(String Friday) {
        this.Friday = Friday;
    }

    public String getSaturday() {
        return Saturday;
    }

    public void setSaturday(String Saturday) {
        this.Saturday = Saturday;
    }

    public String getSunday() {
        return Sunday;
    }

    public void setSunday(String Sunday) {
        this.Sunday = Sunday;
    }
}
