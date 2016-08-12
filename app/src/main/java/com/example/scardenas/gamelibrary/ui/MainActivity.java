package com.example.scardenas.gamelibrary.ui;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.scardenas.gamelibrary.BaseActivity;
import com.example.scardenas.gamelibrary.PreferenceManager;
import com.example.scardenas.gamelibrary.R;
import com.example.scardenas.gamelibrary.adapter.GameListAdapter;
import com.example.scardenas.gamelibrary.data.Game;
import com.example.scardenas.gamelibrary.utils.SnackbarUtils;
import com.example.scardenas.gamelibrary.widget.GameView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewById(R.id.main_toolbar) Toolbar toolbar;
    @ViewById(R.id.main_layout_empty_list) RelativeLayout layoutEmptyList;
    @ViewById(R.id.main_layout_search) RelativeLayout layoutSearch;
    @ViewById(R.id.main_recycler_view) RecyclerView recyclerView;
    @ViewById(R.id.main_btn_add_new_game) FloatingActionButton fbnAddGame;
    @ViewById(R.id.main_text_input_edit_text_game_search) TextInputEditText textInputEditTextGameSearch;

    private SnackbarUtils snackbarUtils;
    private PreferenceManager preferenceManager;

    private List<Game> gameList = new ArrayList<>();
    private GameListAdapter gameListAdapter;

    private Snackbar infoSnackbarWithUndo;

    @AfterViews
    public void initialize() {
        initialConfiguration();
        configureRecyclerView();
        checkViewState();
    }

    private void initialConfiguration() {
        toolbar.setTitle(R.string.app_name);
        snackbarUtils = new SnackbarUtils(MainActivity.this);
        preferenceManager = new PreferenceManager(MainActivity.this);
    }

    private void configureRecyclerView() {
        gameList = preferenceManager.getLibraryGames();
        gameListAdapter = new GameListAdapter(MainActivity.this, gameList, new GameView.GameViewItemClickListener() {
            @Override
            public void onGameClick(Game game) {
                snackbarUtils.createInfoSnackbar(toolbar, game.getName()).show();
            }
        }, null);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(gameListAdapter);
        configureTouchHelper();
    }

    private void configureTouchHelper() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (viewHolder instanceof GameView) {
                    GameView gameView = (GameView)viewHolder;
                    final Game game = gameView.getGame();
                    int position = gameList.indexOf(game);
                    gameList.remove(game);
                    preferenceManager.setLibraryGames(gameList);
                    recyclerView.getAdapter().notifyItemRemoved(position);
                    infoSnackbarWithUndo = snackbarUtils.createInfoSnackbarWithUndoAction(recyclerView,
                            getString(R.string.main_game_removed_snackbar_text, game.getName()),
                            getString(R.string.main_game_removed_snackbar_action),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    infoSnackbarWithUndo.dismiss();
                                    gameList.add(game);
                                    Collections.sort(gameList);
                                    int position = gameList.indexOf(game);
                                    preferenceManager.setLibraryGames(gameList);
                                    recyclerView.getAdapter().notifyItemInserted(position);
                                }
                            }, INFO_SNACKBAR_WITH_UNDO_DURATION);
                    infoSnackbarWithUndo.show();
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void checkViewState() {
        gameList = preferenceManager.getLibraryGames();
        gameListAdapter.setGames(gameList);
        if (gameList.size() > 0) {
            Collections.sort(gameList);
            makeViewAppear(recyclerView);
            makeViewDisappear(layoutEmptyList);
            makeViewDisappear(layoutSearch);
            gameListAdapter.notifyDataSetChanged();
        } else {
            makeViewAppear(layoutEmptyList);
            makeViewDisappear(recyclerView);
            makeViewDisappear(layoutSearch);
        }
    }

    @Click(R.id.main_btn_add_new_game)
    public void addGameClick() {
        if (layoutSearch.getVisibility() == View.VISIBLE) {
            fbnAddGame.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_light));
            checkViewState();
        } else {
            fbnAddGame.setImageDrawable(getResources().getDrawable(R.drawable.ic_back));
            makeViewDisappear(recyclerView);
            makeViewAppear(layoutSearch);
            makeViewDisappear(layoutEmptyList);
        }

    }

    @Click(R.id.main_btn_game_search)
    public void searchGameClick() {
        String gameName = textInputEditTextGameSearch.getText().toString().trim();
        if (!TextUtils.isEmpty(gameName)) {
            GameListActivity_.intent(MainActivity.this).gameName(gameName).start();
        }
    }

    @Override
    public void onBackPressed() {
        if (layoutSearch.getVisibility() == View.VISIBLE) {
            fbnAddGame.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_light));
            checkViewState();
        } else {
            super.onBackPressed();
        }
    }

}
