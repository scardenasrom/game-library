package com.example.scardenas.gamelibrary.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

public class Game implements Comparable<Game>, Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;
    @SerializedName("summary")
    private String summary;
    @SerializedName("rating")
    private float rating;
    @SerializedName("screenshots")
    private List<Screenshot> screenshots;
    @SerializedName("storyline")
    private String storyline;
    @SerializedName("developers")
    private int[] developers;
    @SerializedName("publishers")
    private int[] publishers;
    @SerializedName("release_dates")
    private List<ReleaseDate> releaseDates;
    @SerializedName("videos")
    private List<Video> videos;
    @SerializedName("cover")
    private Cover cover;

    private String platformNames;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<Screenshot> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(List<Screenshot> screenshots) {
        this.screenshots = screenshots;
    }

    public String getStoryline() {
        return storyline;
    }

    public void setStoryline(String storyline) {
        this.storyline = storyline;
    }

    public int[] getDevelopers() {
        return developers;
    }

    public void setDevelopers(int[] developers) {
        this.developers = developers;
    }

    public int[] getPublishers() {
        return publishers;
    }

    public void setPublishers(int[] publishers) {
        this.publishers = publishers;
    }

    public List<ReleaseDate> getReleaseDates() {
        return releaseDates;
    }

    public void setReleaseDates(List<ReleaseDate> releaseDates) {
        this.releaseDates = releaseDates;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public String getPlatformNames() {
        return platformNames;
    }

    public void setPlatformNames(String platformNames) {
        this.platformNames = platformNames;
    }

    @Override
    public int compareTo(Game game) {
        return this.getName().compareTo(game.getName());
    }

}
