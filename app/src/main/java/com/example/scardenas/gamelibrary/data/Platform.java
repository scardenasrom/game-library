package com.example.scardenas.gamelibrary.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Platform implements Serializable {

    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("slug")
    String slug;
    @SerializedName("url")
    String url;
    @SerializedName("created_at")
    long createdAt;
    @SerializedName("updated_at")
    long updatedAt;
    @SerializedName("games")
    List<Integer> gamesIds;

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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Integer> getGamesIds() {
        return gamesIds;
    }

    public void setGamesIds(List<Integer> gamesIds) {
        this.gamesIds = gamesIds;
    }

}
