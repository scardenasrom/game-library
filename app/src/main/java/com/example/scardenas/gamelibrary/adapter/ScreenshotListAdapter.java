package com.example.scardenas.gamelibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.scardenas.gamelibrary.R;
import com.example.scardenas.gamelibrary.data.Screenshot;
import com.example.scardenas.gamelibrary.widget.ScreenshotView;

import java.util.List;

public class ScreenshotListAdapter extends RecyclerView.Adapter<ScreenshotView> {

    private final Context context;
    private List<Screenshot> screenshotList;
    private ScreenshotView.ScreenshotItemClickListener screenshotItemClickListener;

    public ScreenshotListAdapter(Context context, List<Screenshot> screenshots, ScreenshotView.ScreenshotItemClickListener screenshotItemClickListener) {
        this.context = context;
        this.screenshotList = screenshots;
        this.screenshotItemClickListener = screenshotItemClickListener;
    }

    public void setScreenshotList(List<Screenshot> screenshotList) {
        this.screenshotList = screenshotList;
    }

    @Override
    public ScreenshotView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ScreenshotView(LayoutInflater.from(context).inflate(R.layout.view_screenshot, parent, false),
                context,
                screenshotItemClickListener);
    }

    @Override
    public void onBindViewHolder(ScreenshotView holder, int position) {
        Screenshot screenshot = screenshotList.get(position);
        holder.bind(screenshot);
    }

    @Override
    public int getItemCount() {
        return screenshotList.size();
    }

}
