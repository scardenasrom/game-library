package com.example.scardenas.gamelibrary.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.scardenas.gamelibrary.BaseActivity;
import com.example.scardenas.gamelibrary.PreferenceManager;
import com.example.scardenas.gamelibrary.R;
import com.example.scardenas.gamelibrary.adapter.GameListAdapter;
import com.example.scardenas.gamelibrary.data.Game;
import com.example.scardenas.gamelibrary.data.Platform;
import com.example.scardenas.gamelibrary.data.ReleaseDate;
import com.example.scardenas.gamelibrary.network.IGDBServiceProvider;
import com.example.scardenas.gamelibrary.utils.SnackbarUtils;
import com.example.scardenas.gamelibrary.widget.GameView;
import com.gc.materialdesign.views.Card;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_game_list)
public class GameListActivity extends BaseActivity {

    private final static int OFFSET_INCREMENTS = 10;

    @Extra("gameName") String gameName;

    @ViewById(R.id.game_list_recycler_view) RecyclerView recyclerView;
    @ViewById(R.id.snackbar_loading_snackbar) LinearLayout customLoadingSnackbar;
    @ViewById(R.id.snackbar_loading_text) TextView customLoadingTextView;
    @ViewById(R.id.game_list_layout_search_for_another) RelativeLayout layoutSearchForAnother;
    @ViewById(R.id.game_list_text_view_nothing_found) TextView textViewNothingFound;
    @ViewById(R.id.game_list_text_input_layout_game_search) TextInputLayout textInputLayoutGameSearch;
    @ViewById(R.id.game_list_text_input_edit_text_game_search) TextInputEditText textInputEditTextGameSearch;
    @ViewById(R.id.game_list_btn_game_search) AppCompatButton btnGameSearch;

    private SnackbarUtils snackBarUtils;
    private PreferenceManager preferenceManager;

    private GameListAdapter gameListAdapter;
    private List<Game> gameList = new ArrayList<>();

    private int offset = 0;

    @AfterViews
    public void initialize() {
        initialConfigurations();
        configureGameListAdapter();
        customLoadingTextView.setText(getString(R.string.game_list_searching_for_games));
        slideViewFromBottom(customLoadingSnackbar);
        getGames();
    }

    private void initialConfigurations() {
        snackBarUtils = new SnackbarUtils(GameListActivity.this);
        preferenceManager = new PreferenceManager(GameListActivity.this);
    }

    private void configureGameListAdapter() {
        gameListAdapter = new GameListAdapter(GameListActivity.this, gameList, new GameView.GameViewItemClickListener() {
            @Override
            public void onGameClick(Game game) {
                if (customLoadingSnackbar.getVisibility() == View.GONE)
//                    snackBarUtils.createInfoSnackbar(recyclerView, game.getName()).show();
                GameDetailActivity_.intent(GameListActivity.this).game(game).start();
            }
        }, new GameView.FABItemClickListener() {
            @Override
            public void onFABClick(Game game) {
                if (customLoadingSnackbar.getVisibility() == View.GONE) {
                    snackBarUtils.createInfoSnackbar(recyclerView, getString(R.string.game_list_game_added_to_library, game.getName())).show();
                    List<Game> storedGames = preferenceManager.getLibraryGames();
                    storedGames.add(game);
                    preferenceManager.setLibraryGames(storedGames);
                }
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(GameListActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(gameListAdapter);
    }

    @Background
    public void getGames() {
        List<Game> games = IGDBServiceProvider.searchForGames(gameName, offset);
        if (games != null && games.size() > 0) {
            correctPlatforms(games);
        } else {
            showError();
        }
    }

    @Background
    public void correctPlatforms(List<Game> games) {
        for (Game game: games) {
            List<ReleaseDate> releaseDates = game.getReleaseDates();
            String platforms = "";
            if (releaseDates != null) {
                for (ReleaseDate releaseDate: releaseDates) {
                    Platform platform = IGDBServiceProvider.searchPlatformWithId(releaseDate.getPlatform());
                    if (platform != null) {
                        if (TextUtils.isEmpty(platforms)) {
                            platforms = platform.getName();
                        } else {
                            if (!platforms.contains(platform.getName())) {
                                platforms = platforms + ", " + platform.getName();
                            }
                        }
                    }
                }
            }
            game.setPlatformNames(platforms);
            gameList.add(game);
        }
        showGames();
    }

    @UiThread
    public void showGames() {
        gameListAdapter.notifyDataSetChanged();
        if (gameList.size() == (OFFSET_INCREMENTS + offset)) {
            View footerView = LayoutInflater.from(GameListActivity.this).inflate(R.layout.view_game_list_load_more_footer, recyclerView, false);
            RelativeLayout footerRootView = (RelativeLayout) footerView.findViewById(R.id.game_list_footer_relative_layout);
            footerRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    slideViewFromBottom(customLoadingSnackbar);
                    if (gameListAdapter.hasFooter()) {
                        gameListAdapter.removeFooter();
                    }
                    offset = offset + OFFSET_INCREMENTS;
                    getGames();
                }
            });
            gameListAdapter.setFooterView(footerView);
        } else {
            if (gameListAdapter.hasFooter()) {
                gameListAdapter.removeFooter();
            }
        }
        slideViewToBottom(customLoadingSnackbar);
    }

    @UiThread
    public void showError() {
        snackBarUtils.createAlertSnackbar(recyclerView, getString(R.string.game_list_error_searching, gameName), Snackbar.LENGTH_LONG).show();
        slideViewToBottom(customLoadingSnackbar);
        makeViewDisappear(recyclerView);
        textViewNothingFound.setText(getString(R.string.game_list_nothing_found, gameName));
        makeViewAppear(layoutSearchForAnother);
    }

    @Click(R.id.game_list_btn_game_search)
    public void gameSearchClick() {
        String inputGame = textInputEditTextGameSearch.getText().toString().trim();
        if (!TextUtils.isEmpty(inputGame)) {
            gameName = inputGame;
            makeViewDisappear(layoutSearchForAnother);
            makeViewAppear(recyclerView);
            slideViewFromBottom(customLoadingSnackbar);
            getGames();
        }

    }

}
