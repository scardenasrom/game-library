package com.example.scardenas.gamelibrary.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Video implements Serializable {

    @SerializedName("name")
    private String name;
    @SerializedName("video_id")
    private String videoId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

}
