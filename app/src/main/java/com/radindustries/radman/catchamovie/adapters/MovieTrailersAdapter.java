package com.radindustries.radman.catchamovie.adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.radindustries.radman.catchamovie.R;
import com.radindustries.radman.catchamovie.database.MoviesContract;

/**
 * Created by radman on 7/12/16.
 */
public class MovieTrailersAdapter extends
        RecyclerViewCursorAdapter<MovieTrailersAdapter.MovieTrailerViewHolder> {

    public MovieTrailersAdapter(Cursor cursor) {
        super(cursor);
    }

    @Override
    public MovieTrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_trailer_view, parent, false);
        return new MovieTrailerViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(MovieTrailerViewHolder holder, Cursor cursor) {

        holder.trailerImage.setImageResource(R.drawable.youtube_512);
        String trailerName = cursor.getString(cursor
                .getColumnIndex(MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_NAME));
        holder.trailerName.setText(trailerName);

    }

    public static class MovieTrailerViewHolder
            extends RecyclerView.ViewHolder {

        ImageView trailerImage;
        TextView trailerName;
        LinearLayout movieTrailerItem;

        public MovieTrailerViewHolder(View itemView) {

            super(itemView);
            movieTrailerItem = (LinearLayout) itemView.findViewById(R.id.movie_trailer_item);
            trailerImage = (ImageView) itemView.findViewById(R.id.movie_trailer_yt_image);
            trailerName = (TextView) itemView.findViewById(R.id.recyclerview_item_trailer_title_textview);

        }

    }

}
