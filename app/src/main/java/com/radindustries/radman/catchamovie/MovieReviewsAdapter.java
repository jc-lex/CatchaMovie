package com.radindustries.radman.catchamovie;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by radman on 7/11/16.
 */
public class MovieReviewsAdapter extends ArrayAdapter<String> {

    private List<String> mReviewData;

    public MovieReviewsAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
        super(context, resource, textViewResourceId, objects);
        this.mReviewData = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
