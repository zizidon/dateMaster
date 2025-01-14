package com.example.demo.entity;

// デートスポットを表すエンティティクラス
public class DateSpot {

    private Long spotId;  // スポットID（データベースの主キー）
    private String spotName;  // デートスポット名
    private String description;  // デートスポットの説明（カテゴリなど）
    private String spotAddress;  // スポットの住所
    private double latitude;  // 緯度
    private double longitude;  // 経度
    
    // 営業時間（曜日ごとの営業時間を追加）
    private String openingMonday; 
    private String openingTuesday;
    private String openingWednesday;
    private String openingThursday;
    private String openingFriday;
    private String openingSaturday;
    private String openingSunday;

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
    public String getOpeningMonday() {
        return openingMonday;
    }

    public void setOpeningMonday(String openingMonday) {
        this.openingMonday = openingMonday;
    }

    public String getOpeningTuesday() {
        return openingTuesday;
    }

    public void setOpeningTuesday(String openingTuesday) {
        this.openingTuesday = openingTuesday;
    }

    public String getOpeningWednesday() {
        return openingWednesday;
    }

    public void setOpeningWednesday(String openingWednesday) {
        this.openingWednesday = openingWednesday;
    }

    public String getOpeningThursday() {
        return openingThursday;
    }

    public void setOpeningThursday(String openingThursday) {
        this.openingThursday = openingThursday;
    }

    public String getOpeningFriday() {
        return openingFriday;
    }

    public void setOpeningFriday(String openingFriday) {
        this.openingFriday = openingFriday;
    }

    public String getOpeningSaturday() {
        return openingSaturday;
    }

    public void setOpeningSaturday(String openingSaturday) {
        this.openingSaturday = openingSaturday;
    }

    public String getOpeningSunday() {
        return openingSunday;
    }

    public void setOpeningSunday(String openingSunday) {
        this.openingSunday = openingSunday;
    }
}
