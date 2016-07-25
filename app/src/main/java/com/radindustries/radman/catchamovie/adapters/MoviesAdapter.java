package com.radindustries.radman.catchamovie.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.radindustries.radman.catchamovie.R;
import com.radindustries.radman.catchamovie.database.MoviesContract;
import com.squareup.picasso.Picasso;

/**
 * Created by radman on 7/5/16.
 */
public class MoviesAdapter extends CursorAdapter {

    public MoviesAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    static class ViewHolder {
        public ImageView imageView;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.grid_item_movie_posters_imageview);
        }

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.grid_item_movie_posters, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        String imageUrl = cursor.getString(cursor.
                getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_POSTER_URL));
        Picasso.with(context).load(imageUrl).into(holder.imageView);
    }

}
