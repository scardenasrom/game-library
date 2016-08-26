package com.example.scardenas.gamelibrary.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.scardenas.gamelibrary.R;
import com.example.scardenas.gamelibrary.data.Screenshot;

public class ScreenshotView extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final Context context;

    private Screenshot screenshot;

    private LinearLayout rootView;
    private ImageView screenshotImageView;

    public ScreenshotItemClickListener screenshotItemClickListener;

    public ScreenshotView(View itemView, Context context, ScreenshotItemClickListener screenshotItemClickListener) {
        super(itemView);
        this.context = context;
        this.screenshotItemClickListener = screenshotItemClickListener;

        rootView = (LinearLayout)itemView.findViewById(R.id.view_screenshot_root_view);
        screenshotImageView = (ImageView)itemView.findViewById(R.id.view_screenshot_image_view);
        screenshotImageView.setOnClickListener(this);
    }

    public Screenshot getScreenshot() {
        return this.screenshot;
    }

    public void bind(Screenshot screenshot) {
        this.screenshot = screenshot;
        String imgurl = "https://res.cloudinary.com/igdb/image/upload/t_screenshot_med_2x/" + screenshot.getCloudinaryId() + ".jpg";
        Glide.with(context)
                .load(imgurl)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(screenshotImageView);
    }

    @Override
    public void onClick(View view) {
        if (view == screenshotImageView) {
            if (screenshotItemClickListener != null) {
                screenshotItemClickListener.onScreenshotClick(screenshot);
            }
        }
    }

    public static interface ScreenshotItemClickListener {
        void onScreenshotClick(Screenshot screenshot);
    }

}
