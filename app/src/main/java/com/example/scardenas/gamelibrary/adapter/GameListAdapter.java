package com.example.scardenas.gamelibrary.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.scardenas.gamelibrary.R;
import com.example.scardenas.gamelibrary.data.Game;
import com.example.scardenas.gamelibrary.widget.GameView;

import java.util.List;

public class GameListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private Context context;
    private List<Game> games;

    private GameView.GameViewItemClickListener gameViewItemClickListener;
    private GameView.FABItemClickListener fabItemClickListener;

    private RecyclerView.ViewHolder footerViewHolder;

    public GameListAdapter(Context context, List<Game> games) {
        this.context = context;
        this.games = games;
    }

    public GameListAdapter(Context context,
                           List<Game> games,
                           GameView.GameViewItemClickListener gameViewItemClickListener,
                           GameView.FABItemClickListener fabItemClickListener) {
        this.context = context;
        this.games = games;
        this.gameViewItemClickListener = gameViewItemClickListener;
        this.fabItemClickListener = fabItemClickListener;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public void setFooterView(View foot) {
        if (footerViewHolder == null || foot != footerViewHolder.itemView) {
            footerViewHolder = new FooterViewHolder(foot);
            notifyDataSetChanged();
        }
    }

    public void removeFooter() {
        if (footerViewHolder != null){
            footerViewHolder = null;
            notifyDataSetChanged();
        }
    }

    public boolean hasFooter() {
        return footerViewHolder != null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView;
            if (fabItemClickListener != null) {
                itemView = LayoutInflater.from(context).inflate(R.layout.view_game_card, parent, false);
            } else {
                itemView = LayoutInflater.from(context).inflate(R.layout.view_game_card_no_fab, parent, false);
            }
            return new GameView(itemView, context, gameViewItemClickListener, fabItemClickListener);
        } else if (viewType == TYPE_FOOTER) {
            return footerViewHolder;
        }
        return null;
    }

    private boolean isPositionFooter (int position) {
        return position == this.games.size();
    }

    @Override
    public int getItemViewType (int position) {
        if(isPositionFooter (position)) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof GameView) {
            Game game = games.get(position);
            GameView gameView = (GameView)viewHolder;
            gameView.bind(game);
        }
    }

    @Override
    public int getItemCount() {
        if (hasFooter()) {
            return games.size() + 1;
        } else {
            return games.size();
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout rootView;

        public FooterViewHolder (View itemView) {
            super (itemView);

            rootView = (RelativeLayout) itemView.findViewById(R.id.game_list_footer_relative_layout);
        }

    }

}
