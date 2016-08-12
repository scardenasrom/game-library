package com.example.scardenas.gamelibrary.widget;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.scardenas.gamelibrary.R;
import com.example.scardenas.gamelibrary.data.Game;

import org.apache.commons.lang3.StringUtils;

public class GameView extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final Context context;

    private Game game;

//    private CardView cardViewGame;
    private LinearLayout cardViewRootView;
    private ImageView imageViewCover;
    private TextView textViewGameTitle;
    private TextView textViewGameSynopsis;
    private TextView textViewGameScore;
    private TextView textViewPlatforms;
    private FloatingActionButton addGameToLibraryButton;

    public GameViewItemClickListener gameViewItemClickListener;
    public FABItemClickListener fabItemClickListener;

    public GameView(View itemView, Context context, GameViewItemClickListener gameViewItemClickListener, FABItemClickListener fabItemClickListener) {
        super(itemView);
        this.context = context;
        this.gameViewItemClickListener = gameViewItemClickListener;
        this.fabItemClickListener = fabItemClickListener;

//        cardViewGame = (CardView)itemView.findViewById(R.id.game_card_view);
        cardViewRootView = (LinearLayout)itemView.findViewById(R.id.game_card_view_root_view);
        imageViewCover = (ImageView)itemView.findViewById(R.id.view_game_card_cover);
        textViewGameTitle = (TextView) itemView.findViewById(R.id.view_game_card_game_title);
        textViewGameSynopsis = (TextView) itemView.findViewById(R.id.view_game_card_game_synopsis);
        textViewGameScore = (TextView) itemView.findViewById(R.id.view_game_card_score);
        textViewPlatforms = (TextView) itemView.findViewById(R.id.view_game_card_platforms);
        if (fabItemClickListener != null) {
            addGameToLibraryButton = (FloatingActionButton) itemView.findViewById(R.id.game_card_btn_add_game);
            addGameToLibraryButton.setOnClickListener(this);
        }
//        cardViewGame.setOnClickListener(this);
        cardViewRootView.setOnClickListener(this);
    }

    public Game getGame() {
        return this.game;
    }

    public void bind(Game game) {
        this.game = game;
        configureTitle();
        configureSynopsis();
        configurePlatforms();
        configureScore();
        configureCover();
    }

    private void configureTitle() {
        textViewGameTitle.setText(game.getName());
    }

    private void configureSynopsis() {
        if (game.getSummary() != null && !game.getSummary().trim().isEmpty()) {
            textViewGameSynopsis.setText(game.getSummary().trim());
        } else if (game.getStoryline() != null && !game.getStoryline().trim().isEmpty()) {
            textViewGameSynopsis.setText(game.getStoryline().trim());
        } else {
            textViewGameSynopsis.setText("");
        }
    }

    private void configurePlatforms() {
        textViewPlatforms.setText(game.getPlatformNames());
    }

    private void configureScore() {
        if (game.getRating() != 0.0) {
            textViewGameScore.setText(context.getString(R.string.game_card_score, (long)game.getRating()));
        } else {
            textViewGameScore.setVisibility(View.GONE);
        }
    }

    private void configureCover() {
        String imgurl = "";
        if (game.getCover() != null && game.getCover().getCloudinaryId() != null && !game.getCover().getCloudinaryId().isEmpty()) {
            imgurl = "https://res.cloudinary.com/igdb/image/upload/t_cover_big/"
                    + game.getCover().getCloudinaryId() + ".jpg";
        }

        else if (game.getScreenshots() != null && game.getScreenshots().size() > 0) {
            if (game.getScreenshots().get(0) != null && game.getScreenshots().get(0).getCloudinaryId() != null
                    && !game.getScreenshots().get(0).getCloudinaryId().isEmpty()) {
                imgurl = "https://res.cloudinary.com/igdb/image/upload/t_cover_big/"
                        + game.getScreenshots().get(0).getCloudinaryId() + ".jpg";
            }
        }

        if (!TextUtils.isEmpty(imgurl)) {
            Glide.with(context)
                    .load(imgurl)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageViewCover);
        }
    }

    public void setGameViewItemClickListener(GameViewItemClickListener gameViewItemClickListener) {
        this.gameViewItemClickListener = gameViewItemClickListener;
    }

    @Override
    public void onClick(View view) {
        if (view == cardViewRootView) {
            if (gameViewItemClickListener != null) {
                gameViewItemClickListener.onGameClick(game);
            }
        } else if (view == addGameToLibraryButton) {
            if (fabItemClickListener != null) {
                fabItemClickListener.onFABClick(game);
            }
        }
    }

    public static interface GameViewItemClickListener {
        void onGameClick(Game game);
    }

    public static interface FABItemClickListener {
        void onFABClick(Game game);
    }

}
