package com.example.demo.entity;

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

    // カテゴリ名（description から変換されたカテゴリ名）
    private String categoryName;

    // ゲッターとセッター（プロパティアクセス用）

    public Long getSpotId() {
        return spotId;
    }

    public void setSpotId(Long spotId) {
        this.spotId = spotId;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        // description が設定されるたびにカテゴリ名を設定
        this.categoryName = convertDescriptionToCategory(description);
    }

    public Long getSpotType() {
        return spotType;
    }

    public void setSpotType(Long spotType) {
        this.spotType = spotType;
    }

    public String getSpotAddress() {
        return spotAddress;
    }

    public void setSpotAddress(String spotAddress) {
        this.spotAddress = spotAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

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

    // categoryNameのゲッターとセッター
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    // description 番号をカテゴリ名に変換するメソッド
    private String convertDescriptionToCategory(String description) {
        if (description == null) return "未定義";
        
        switch (description) {
            case "1":
                return "カフェ";
            case "2":
                return "神社系";
            case "3":
                return "公園";
            case "4":
                return "歴史系";
            case "5":
                return "アクティブ系";
            case "6":
                return "インドア系";
            case "7":
                return "飲食店";
            case "8":
                return "動物園";
            default:
                return "未定義";
        }
    } 
}
