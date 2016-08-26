package com.example.scardenas.gamelibrary.ui;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Scene;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.scardenas.gamelibrary.BaseActivity;
import com.example.scardenas.gamelibrary.PreferenceManager;
import com.example.scardenas.gamelibrary.R;
import com.example.scardenas.gamelibrary.adapter.ScreenshotListAdapter;
import com.example.scardenas.gamelibrary.data.Game;
import com.example.scardenas.gamelibrary.data.Screenshot;
import com.example.scardenas.gamelibrary.utils.SnackbarUtils;
import com.example.scardenas.gamelibrary.widget.ScreenshotDialog;
import com.example.scardenas.gamelibrary.widget.ScreenshotView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity(R.layout.activity_game_detail)
public class GameDetailActivity extends BaseActivity {

    @Extra("game")
    Game game;

    @ViewById(R.id.game_detail_app_bar) AppBarLayout appBarLayout;
    @ViewById(R.id.game_detail_collapsing_toolbar_layout) CollapsingToolbarLayout collapsingToolbarLayout;
    @ViewById(R.id.game_detail_image_parallax) ImageView imageViewCoverParallax;
    @ViewById(R.id.game_detail_toolbar) Toolbar toolbar;
    @ViewById(R.id.game_detail_fab_add) FloatingActionButton buttonAddGameToLibrary;
    @ViewById(R.id.game_detail_fab_remove) FloatingActionButton buttonRemoveGameFromLibrary;

    @ViewById(R.id.game_detail_screenshots_recycler_view) RecyclerView recyclerViewScreenshots;

    private PreferenceManager preferenceManager;
    private SnackbarUtils snackbarUtils;

    private List<Screenshot> screenshotList;
    private ScreenshotListAdapter screenshotListAdapter;

    @AfterViews
    public void initialize() {
        preferenceManager = new PreferenceManager(GameDetailActivity.this);
        snackbarUtils = new SnackbarUtils(GameDetailActivity.this);

        //TODO Check if the game is in library, hide or show the buttons depending on the result

        collapsingToolbarLayout.setTitle(game.getName());
        toolbar.setTitle(game.getName());

        Glide.with(GameDetailActivity.this)
                .load(CLOUDINARY_IMAGES_URL+CLOUDINARY_SIZE_COVER_BIG+game.getCover().getCloudinaryId()+CLOUDINARY_IMAGES_FORMAT)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(imageViewCoverParallax);

        screenshotList = game.getScreenshots();
        if (screenshotList != null && screenshotList.size() > 0) {
            screenshotListAdapter = new ScreenshotListAdapter(GameDetailActivity.this, screenshotList, new ScreenshotView.ScreenshotItemClickListener() {
                @Override
                public void onScreenshotClick(Screenshot screenshot) {
                    loadScreenshot(screenshot);
                }
            });
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(GameDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewScreenshots.setLayoutManager(layoutManager);
            recyclerViewScreenshots.setItemAnimator(new DefaultItemAnimator());
            recyclerViewScreenshots.setAdapter(screenshotListAdapter);
        }
    }

    private void loadScreenshot(Screenshot screenshot) {
        ScreenshotDialog screenshotDialog = new ScreenshotDialog(
                GameDetailActivity.this,
                screenshot,
                screenshotList.indexOf(screenshot),
                screenshotList.size(),
                new ScreenshotDialog.OnArrowsClickListener() {
            @Override
            public void onLeftArrowClicked(ScreenshotDialog currentScreenshotDialog, Screenshot screenshot) {
                currentScreenshotDialog.dismiss();
                int order = screenshotList.indexOf(screenshot);
                order--;
                Screenshot previousScreenshot = screenshotList.get(order);
                loadScreenshot(previousScreenshot);
            }

            @Override
            public void onRightArrowClicked(ScreenshotDialog currentScreenshotDialog, Screenshot screenshot) {
                currentScreenshotDialog.dismiss();
                int order = screenshotList.indexOf(screenshot);
                order++;
                Screenshot previousScreenshot = screenshotList.get(order);
                loadScreenshot(previousScreenshot);
            }
        });
        screenshotDialog.show();
    }

}
