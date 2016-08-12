package com.example.scardenas.gamelibrary.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.scardenas.gamelibrary.R;

public class SnackbarUtils {

    private final Context context;
    private int duration = Snackbar.LENGTH_SHORT;

    public SnackbarUtils(Context context) {
        this.context = context;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setShortDuration() {
        this.duration = Snackbar.LENGTH_SHORT;
    }

    public void setLongDuration() {
        this.duration = Snackbar.LENGTH_LONG;
    }

    public void setIndefiniteDuration() {
        this.duration = Snackbar.LENGTH_INDEFINITE;
    }

    //region Alert Snackbar
    public Snackbar createAlertSnackbar(View anchorView, int resMessageId) {
        String message = context.getString(resMessageId);
        return createAlertSnackbar(anchorView, message);
    }

    public Snackbar createAlertSnackbar(View anchorView, String message) {
        return createAlertSnackbar(anchorView, message, duration);
    }

    public Snackbar createAlertSnackbar(View anchorView, int resMessageId, int duration) {
        String message = context.getString(resMessageId);
        return createAlertSnackbar(anchorView, message, duration);
    }

    public Snackbar createAlertSnackbar(View anchorView, String message, int duration) {
        Snackbar result = Snackbar.make(anchorView, message, duration);
        View snackbarView = result.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.alert_snackbar_bg));
        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(context.getResources().getColor(R.color.white));
        tv.setMaxLines(5);
        return result;
    }
    //endregion

    //region Info Snackbar
    public Snackbar createInfoSnackbar(View anchorView, int resMessageId) {
        String message = context.getString(resMessageId);
        return createInfoSnackbar(anchorView, message);
    }

    public Snackbar createInfoSnackbar(View anchorView, String message) {
        return createInfoSnackbar(anchorView, message, duration);
    }

    public Snackbar createInfoSnackbar(View anchorView, int resMessageId, int duration) {
        String message = context.getString(resMessageId);
        return createInfoSnackbar(anchorView, message, duration);
    }

    public Snackbar createInfoSnackbar(View anchorView, String message, int duration) {
        Snackbar result = Snackbar.make(anchorView, message, duration);
        View snackbarView = result.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_accent));
        TextView tv = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(context.getResources().getColor(R.color.white));
        tv.setMaxLines(5);
        return result;
    }
    //endregion

    //region Info Snackbar Undo Action
    public Snackbar createInfoSnackbarWithUndoAction(View anchorView, int resMessageId, int resActionMessageId, View.OnClickListener onClickListener) {
        String message = context.getString(resMessageId);
        String actionMessage = context.getString(resActionMessageId);
        return createInfoSnackbarWithUndoAction(anchorView, message, actionMessage, onClickListener);
    }

    public Snackbar createInfoSnackbarWithUndoAction(View anchorView, int resMessageId, int resActionMessageId, View.OnClickListener onClickListener, int duration) {
        String message = context.getString(resMessageId);
        String actionMessage = context.getString(resActionMessageId);
        return createInfoSnackbarWithUndoAction(anchorView, message, actionMessage, onClickListener, duration);
    }

    public Snackbar createInfoSnackbarWithUndoAction(View anchorView, String message, String actionMessage, View.OnClickListener onClickListener) {
        return createInfoSnackbarWithUndoAction(anchorView, message, actionMessage, onClickListener, Snackbar.LENGTH_LONG);
    }

    public Snackbar createInfoSnackbarWithUndoAction(View anchorView, String message, String actionMessage, View.OnClickListener onClickListener, int duration) {
        Snackbar result = Snackbar.make(anchorView, "", duration);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout)result.getView();
        TextView snackbarTextView = (TextView) snackbarLayout.findViewById(android.support.design.R.id.snackbar_text);
        snackbarTextView.setVisibility(View.INVISIBLE);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View newSnackbarView = layoutInflater.inflate(R.layout.snackbar_info_undo, null);
        TextView newSnackbarMessage = (TextView)newSnackbarView.findViewById(R.id.snackbar_info_undo_text);
        newSnackbarMessage.setText(message);
        TextView newSnackbarAction = (TextView)newSnackbarView.findViewById(R.id.snackbar_info_action);
        newSnackbarAction.setText(actionMessage);
        newSnackbarAction.setOnClickListener(onClickListener);
        snackbarLayout.addView(newSnackbarView, 0);
        snackbarLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.color_accent));
        return result;
    }
    //endregion

}
