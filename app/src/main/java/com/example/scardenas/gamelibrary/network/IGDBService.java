package com.example.scardenas.gamelibrary.network;

import com.example.scardenas.gamelibrary.data.Game;
import com.example.scardenas.gamelibrary.data.Platform;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IGDBService {

    @GET("/games/{gameId}/?fields=*")
    Call<List<Game>> getGame(@Path("gameId") String gameId);

    @GET("/games/?fields=id,name,url,summary,rating,screenshots,storyline,developers,publishers,release_dates,videos,cover&order=release_dates.date%3Adesc")
    Call<List<Game>> searchGames(@Query("limit") int numberOfResults, @Query("search") String searchString, @Query("offset") int offset);

    @GET("/platforms/{platformId}/?fields=*")
    Call<List<Platform>> searchPlatformById(@Path("platformId") int platformId);

}
