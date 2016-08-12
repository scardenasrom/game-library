package com.example.scardenas.gamelibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scardenas.gamelibrary.R;
import com.example.scardenas.gamelibrary.data.Game;
import com.example.scardenas.gamelibrary.widget.GameView;

import java.util.List;

public class GameListAdapterOld extends RecyclerView.Adapter<GameView> {

    private Context context;
    private List<Game> games;

    private GameView.GameViewItemClickListener gameViewItemClickListener;
    private GameView.FABItemClickListener fabItemClickListener;

    public GameListAdapterOld(Context context, List<Game> games) {
        this.context = context;
        this.games = games;
    }

    public GameListAdapterOld(Context context, List<Game> games, GameView.GameViewItemClickListener gameViewItemClickListener, GameView.FABItemClickListener fabItemClickListener) {
        this.context = context;
        this.games = games;
        this.gameViewItemClickListener = gameViewItemClickListener;
        this.fabItemClickListener = fabItemClickListener;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    @Override
    public GameView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (fabItemClickListener != null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.view_game_card, parent, false);
        } else {
            itemView = LayoutInflater.from(context).inflate(R.layout.view_game_card_no_fab, parent, false);
        }
        return new GameView(itemView, context, gameViewItemClickListener, fabItemClickListener);
    }

    @Override
    public void onBindViewHolder(GameView gameView, int position) {
        Game game = games.get(position);
        gameView.bind(game);
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

}
