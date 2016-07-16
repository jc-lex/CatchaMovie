package com.radindustries.radman.catchamovie.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.radindustries.radman.catchamovie.datamodels.GridItem;
import com.radindustries.radman.catchamovie.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by radman on 7/5/16.
 */
public class MoviesAdapter extends ArrayAdapter<GridItem> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<GridItem> mGridData = new ArrayList<>();

    public MoviesAdapter(Context context, int layoutResourceId, ArrayList<GridItem> mGridData) {
        super(context, layoutResourceId, mGridData);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.mGridData = mGridData;
    }

    static class ViewHolder {
        ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) { //if the view has no data, inflate it and set its resource
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) row.findViewById(R.id.grid_item_movie_posters_imageview);
            row.setTag(holder);
        } else { //if the view has data,
            holder = (ViewHolder) row.getTag();
        }

        GridItem item = mGridData.get(position);
        Picasso.with(context).load(item.getImage()).into(holder.imageView);
        return row;
    }

    public void setGridData(ArrayList<GridItem> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }
}
