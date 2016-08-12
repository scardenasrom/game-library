package com.example.scardenas.gamelibrary.network;

import android.util.Log;

import com.example.scardenas.gamelibrary.data.Game;
import com.example.scardenas.gamelibrary.data.Platform;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IGDBServiceProvider {

    private static final String TAG = "IGDBServiceError";

    private static final String BASE_URL = "https://igdbcom-internet-game-database-v1.p.mashape.com/";
    private static final String API_KEY = "4nw4zHxauWmshPJZziGpUSh5k4rOp1JEamtjsnScZjkBd2Fpml";

    public static final int DEFAULT_NUMBER_OF_RESULTS = 10;

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static IGDBService igdbService = createService(IGDBService.class);

    public static <S> S createService(Class<S> serviceClass) {
        httpClient.connectTimeout(20, TimeUnit.SECONDS);
        httpClient.readTimeout(20, TimeUnit.SECONDS);
        httpClient.writeTimeout(20, TimeUnit.SECONDS);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("X-Mashape-Key", API_KEY)
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    public static Game getGameWithId(String gameId) {
        try {
            Call<List<Game>> call = igdbService.getGame(gameId);
            Response<List<Game>> response = call.execute();
            if (response.isSuccessful()) {
                return response.body().get(0);
            } else {
                Log.e(TAG, "There was an error searching for the game: " + response.message());
                return null;
            }
        } catch (IOException exception) {
            Log.e(TAG, "There was an exception searching for the game. This is the stacktrace: ", exception);
            return null;
        }
    }

    public static List<Game> searchForGames(String searchString, int offset) {
        return searchForGames(DEFAULT_NUMBER_OF_RESULTS, searchString, offset);
    }

    public static List<Game> searchForGames(int numberOfResults, String searchString, int offset) {
        try {
            Call<List<Game>> call = igdbService.searchGames(numberOfResults, searchString, offset);
            Response<List<Game>> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                Log.e(TAG, "There was an error searching for the games: " + response.message());
                return null;
            }
        } catch (IOException exception) {
            Log.e(TAG, "There was an exception searching for the games. This is the stacktrace: ", exception);
            return null;
        }
    }

    public static Platform searchPlatformWithId(int platformId) {
        try {
            Call<List<Platform>> call = igdbService.searchPlatformById(platformId);
            Response<List<Platform>> response = call.execute();
            if (response.isSuccessful()) {
                return response.body().get(0);
            } else {
                Log.e(TAG, "There was an error searching for the games: " + response.message());
                return null;
            }
        } catch (IOException exception) {
            Log.e(TAG, "There was an exception searching for the platform. This is the stacktrace: " + exception);
            return null;
        }
    }

}
