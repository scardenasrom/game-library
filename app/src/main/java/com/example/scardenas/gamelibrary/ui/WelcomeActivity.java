package com.example.scardenas.gamelibrary.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.scardenas.gamelibrary.BaseActivity;
import com.example.scardenas.gamelibrary.PreferenceManager;
import com.example.scardenas.gamelibrary.R;
import com.example.scardenas.gamelibrary.adapter.WelcomeScreenPagerAdapter;
import com.gc.materialdesign.views.ButtonFlat;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

@EActivity(R.layout.activity_welcome)
public class WelcomeActivity extends BaseActivity {

    @ViewById(R.id.welcome_view_pager) ViewPager viewPager;
    @ViewById(R.id.welcome_layout_dots) LinearLayout layoutDots;
    @ViewById(R.id.welcome_btn_next) ButtonFlat btnNext;
    @ViewById(R.id.welcome_btn_skip) ButtonFlat btnSkip;

    private WelcomeScreenPagerAdapter welcomeScreenPagerAdapter;

    private PreferenceManager preferenceManager;

    private int[] layouts;

    @AfterViews
    public void initialize() {
        preferenceManager = new PreferenceManager(WelcomeActivity.this);
        if (!preferenceManager.isFirstTimeLaunch())
            launchHomeScreen();

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        layouts = new int[] {
                R.layout.welcome_screen_1,
                R.layout.welcome_screen_2,
                R.layout.welcome_screen_3,
                R.layout.welcome_screen_4
        };

        addBottomDots(0);
        changeStatusBarColor();
        configureAdapter();
    }

    private void launchHomeScreen() {
        preferenceManager.setFirstTimeLaunch(false);
        startActivity(new Intent(WelcomeActivity.this, MainActivity_.class));
        finish();
    }

    private void addBottomDots(int currentPage) {
        TextView[] dots = new TextView[layouts.length];

        int colorActive = getResources().getColor(R.color.dot_color);
        int colorInactive = getResources().getColor(R.color.inactive_dot_color);

        layoutDots.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(WelcomeActivity.this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInactive);
            layoutDots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[currentPage].setTextColor(colorActive);
        }
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void configureAdapter() {
        welcomeScreenPagerAdapter = new WelcomeScreenPagerAdapter(WelcomeActivity.this, layouts);
        viewPager.setAdapter(welcomeScreenPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
                if (position == layouts.length - 1) {
                    btnNext.setText(getString(R.string.welcome_btn_got_it));
                    btnSkip.setVisibility(View.GONE);
                } else {
                    btnNext.setText(getString(R.string.welcome_btn_next));
                    btnSkip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Click(R.id.welcome_btn_next)
    public void clickOnNext() {
        int current = getItem(+1);
        if (current < layouts.length) {
            viewPager.setCurrentItem(current);
        } else {
            launchHomeScreen();
        }
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    @Click(R.id.welcome_btn_skip)
    public void clickOnSkip() {
        launchHomeScreen();
    }

}
