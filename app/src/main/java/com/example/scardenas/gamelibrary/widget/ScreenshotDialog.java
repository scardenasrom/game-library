package com.example.scardenas.gamelibrary.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.scardenas.gamelibrary.R;
import com.example.scardenas.gamelibrary.data.Screenshot;

public class ScreenshotDialog extends Dialog implements View.OnClickListener {

    private final Context context;
    private final Screenshot screenshot;
    private ImageView imageViewScreenshot;
    private TextView textViewScreenshotNumber;

    private ImageView leftArrow;
    private ImageView rightArrow;

    private int order;
    private int total;

    private OnArrowsClickListener onArrowsClickListener;

    public ScreenshotDialog(Context context,
                            Screenshot screenshot,
                            int order,
                            int total,
                            OnArrowsClickListener onArrowsClickListener) {
        super(context);
        this.context = context;
        this.screenshot = screenshot;
        this.order = order;
        this.total = total;
        this.onArrowsClickListener = onArrowsClickListener;
    }

    public ScreenshotDialog(Context context,
                            int themeResId,
                            Screenshot screenshot,
                            int order,
                            int total,
                            OnArrowsClickListener onArrowsClickListener) {
        super(context, themeResId);
        this.context = context;
        this.screenshot = screenshot;
        this.order = order;
        this.total = total;
        this.onArrowsClickListener = onArrowsClickListener;
    }

    protected ScreenshotDialog(Context context,
                               boolean cancelable,
                               OnCancelListener cancelListener,
                               Screenshot screenshot,
                               int order,
                               int total,
                               OnArrowsClickListener onArrowsClickListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
        this.screenshot = screenshot;
        this.order = order;
        this.total = total;
        this.onArrowsClickListener = onArrowsClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_zoomed_screenshot);
        imageViewScreenshot = (ImageView)findViewById(R.id.dialog_zoomed_screenshot_image_view);
        textViewScreenshotNumber = (TextView)findViewById(R.id.dialog_zoomed_screenshot_number_text_view);
        textViewScreenshotNumber.setText(context.getString(R.string.game_detail_screenshot_order, order+1, total));
        String imgurl = "https://res.cloudinary.com/igdb/image/upload/t_screenshot_huge_2x/" + screenshot.getCloudinaryId() + ".jpg";
        Glide.with(getContext())
                .load(imgurl)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageViewScreenshot);

        leftArrow = (ImageView)findViewById(R.id.dialog_zoomed_screenshot_left);
        rightArrow = (ImageView)findViewById(R.id.dialog_zoomed_screenshot_right);
        if (order == 0) {
            leftArrow.setVisibility(View.GONE);
        } else {
            leftArrow.setVisibility(View.VISIBLE);
        }
        if (order == total-1) {
            rightArrow.setVisibility(View.GONE);
        } else {
            rightArrow.setVisibility(View.VISIBLE);
        }
        leftArrow.setOnClickListener(this);
        rightArrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == leftArrow) {
            onArrowsClickListener.onLeftArrowClicked(this, screenshot);
        } else if (view == rightArrow) {
            onArrowsClickListener.onRightArrowClicked(this, screenshot);
        }
    }

    public static interface OnArrowsClickListener {
        public void onLeftArrowClicked(ScreenshotDialog screenshotDialog, Screenshot screenshot);
        public void onRightArrowClicked(ScreenshotDialog screenshotDialog, Screenshot screenshot);
    }

}
