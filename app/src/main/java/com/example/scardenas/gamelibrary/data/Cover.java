package com.example.scardenas.gamelibrary.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cover implements Serializable {

    @SerializedName("cloudinary_id")
    private String cloudinaryId;
    @SerializedName("width")
    private int width;
    @SerializedName("height")
    private int height;

    public String getCloudinaryId() {
        return cloudinaryId;
    }

    public void setCloudinaryId(String cloudinaryId) {
        this.cloudinaryId = cloudinaryId;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
