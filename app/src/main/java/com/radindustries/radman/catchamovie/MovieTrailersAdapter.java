package com.radindustries.radman.catchamovie;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by radman on 7/12/16.
 */
public class MovieTrailersAdapter extends ArrayAdapter<String> {

    private List<String> mTrailerData;

    public MovieTrailersAdapter(Context context, int resource,
                                int textViewResourceId, List<String> objects) {
        super(context, resource, textViewResourceId, objects);
        this.mTrailerData = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
