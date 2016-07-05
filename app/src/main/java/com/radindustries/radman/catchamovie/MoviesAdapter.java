package com.radindustries.radman.catchamovie;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by radman on 7/5/16.
 */
public class MoviesAdapter extends BaseAdapter {

    private Context c;
    private Integer[] fakePosterData = {
            R.drawable.ic_launcher,
            R.drawable.ic_launcher_sunshine,
            R.drawable.ic_sunshine,
    };

    public MoviesAdapter (Context c) {
        this.c = c;
    }

    @Override
    public int getCount() {
        return fakePosterData.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(c);
            imageView.setLayoutParams(new GridView
                    .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(fakePosterData[position]);
        return imageView;
    }

}
