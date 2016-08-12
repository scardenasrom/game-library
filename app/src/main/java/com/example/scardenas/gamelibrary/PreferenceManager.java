package com.example.scardenas.gamelibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.ArraySet;

import com.example.scardenas.gamelibrary.data.Game;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PreferenceManager {

    private static final String PREF_NAME = "general-prefs";
    private static final String IS_FIRST_TIME_LAUNCH_PREF = "isFirstTimeLaunch";
    private static final String LIBRARY_GAMES = "libraryGames";

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    public PreferenceManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH_PREF, isFirstTime);
        editor.apply();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH_PREF, true);
    }

    public void setLibraryGames(List<Game> games) {
        Gson gson = new Gson();
        Set<String> gamesStringSet = new HashSet<>(games.size());
        for (Game game: games) {
            String gameString = gson.toJson(game, Game.class);
            gamesStringSet.add(gameString);
        }
        editor.putStringSet(LIBRARY_GAMES, gamesStringSet);
        editor.apply();
    }

    public List<Game> getLibraryGames() {
        Gson gson = new Gson();
        List<Game> games = new ArrayList<>();
        Set<String> gamesStringSet = pref.getStringSet(LIBRARY_GAMES, new HashSet<String>());
        for (String gameString: gamesStringSet) {
            Game game = gson.fromJson(gameString, Game.class);
            games.add(game);
        }
        return games;
    }

}
