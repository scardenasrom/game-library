package com.example.scardenas.gamelibrary;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.scardenas.gamelibrary.data.Game;
import com.example.scardenas.gamelibrary.ui.GameListActivity;
import com.example.scardenas.gamelibrary.ui.MainActivity;
import com.google.gson.Gson;

public class BaseActivity extends AppCompatActivity {

    protected static final String CLOUDINARY_IMAGES_URL = "https://res.cloudinary.com/igdb/image/upload/t_";
    protected static final String CLOUDINARY_SIZE_COVER_SMALL = "cover_small_2x/";
    protected static final String CLOUDINARY_SIZE_COVER_BIG = "cover_big_2x/";
    protected static final String CLOUDINARY_SIZE_SCREENSHOT_MED = "screenshot_med_2x/";
    protected static final String CLOUDINARY_SIZE_LOGO_MED = "logo_med_2x/";
    protected static final String CLOUDINARY_SIZE_SCREENSHOT_BIG = "screenshot_big_2x/";
    protected static final String CLOUDINARY_SIZE_SCREENSHOT_HUGE = "screenshot_huge_2x/";
    protected static final String CLOUDINARY_SIZE_THUMB = "thumb_2x/";
    protected static final String CLOUDINARY_SIZE_MICRO = "micro_2x/";
    protected static final String CLOUDINARY_IMAGES_FORMAT = ".jpg";

    protected static final int INFO_SNACKBAR_WITH_UNDO_DURATION = 5000;

    protected void makeViewDisappear(View view) {
        if (view.getVisibility() != View.GONE) {
            view.setAnimation(AnimationUtils.loadAnimation(BaseActivity.this, R.anim.fade_out));
            view.setVisibility(View.GONE);
        }
    }

    protected void makeViewAppear(View view) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
            view.setAnimation(AnimationUtils.loadAnimation(BaseActivity.this, R.anim.fade_in));
        }
    }

    protected Game convertStringToGame(String gameString) {
        Gson gson = new Gson();
        return gson.fromJson(gameString, Game.class);
    }

    protected String convertGameToString(Game game) {
        Gson gson = new Gson();
        return gson.toJson(game, Game.class);
    }

    protected void slideViewFromBottom(final View view) {
        Animation animation = AnimationUtils.loadAnimation(BaseActivity.this, R.anim.abc_slide_in_bottom);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(animation);
    }

    protected void slideViewToBottom(final View view) {
        Animation animation = AnimationUtils.loadAnimation(BaseActivity.this, R.anim.abc_slide_out_bottom);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(animation);
    }

}
