package com.example.backend.dto;

public class YearlyData {

    private int year;
    private long asset;

    public YearlyData(int year, long asset) {
        this.year = year;
        this.asset = asset;
    }

    public int getYear() {
        return year;
    }

    public long getAsset() {
        return asset;
    }
}