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
    private String opening_Monday; 
    private String opening_Tuesday;
    private String opening_Wednesday;
    private String opening_Thursday;
    private String opening_Friday;
    private String opening_Saturday;
    private String opening_Sunday;

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
        return opening_Monday;
    }

    public void setOpeningMonday(String openingMonday) {
        this.opening_Monday = openingMonday;
    }

    public String getOpeningTuesday() {
        return opening_Tuesday;
    }

    public void setOpeningTuesday(String openingTuesday) {
        this.opening_Tuesday = openingTuesday;
    }

    public String getOpeningWednesday() {
        return opening_Wednesday;
    }

    public void setOpeningWednesday(String openingWednesday) {
        this.opening_Wednesday = openingWednesday;
    }

    public String getOpeningThursday() {
        return opening_Thursday;
    }

    public void setOpeningThursday(String openingThursday) {
        this.opening_Thursday = openingThursday;
    }

    public String getOpeningFriday() {
        return opening_Friday;
    }

    public void setOpeningFriday(String openingFriday) {
        this.opening_Friday = openingFriday;
    }

    public String getOpeningSaturday() {
        return opening_Saturday;
    }

    public void setOpeningSaturday(String openingSaturday) {
        this.opening_Saturday = openingSaturday;
    }

    public String getOpeningSunday() {
        return opening_Sunday;
    }

    public void setOpeningSunday(String openingSunday) {
        this.opening_Sunday = openingSunday;
    }
}
