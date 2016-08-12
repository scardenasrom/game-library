package com.example.scardenas.gamelibrary.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReleaseDate implements Serializable {

    @SerializedName("category")
    private int category;
    @SerializedName("platform")
    private int platform;
    @SerializedName("date")
    private long date;
    @SerializedName("region")
    private int region;

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

}
