package com.radindustries.radman.catchamovie.adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.radindustries.radman.catchamovie.R;
import com.radindustries.radman.catchamovie.database.MoviesContract;

/**
 * Created by radman on 7/11/16.
 */
public class MovieReviewsAdapter extends
        RecyclerViewCursorAdapter<MovieReviewsAdapter.MovieReviewViewHolder> {

    public MovieReviewsAdapter(Cursor cursor) {
        super(cursor);
    }

    @Override
    public MovieReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_review_view, parent, false);
        return new MovieReviewViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(MovieReviewViewHolder holder, Cursor cursor) {

        String author = cursor.getString(cursor
                .getColumnIndex(MoviesContract.ReviewEntry.COL_MOVIE_REVIEW_AUTHOR));
        String review = cursor.getString(cursor
                .getColumnIndex(MoviesContract.ReviewEntry.COL_MOVIE_REVIEW));
        holder.reviewAuthor.setText(author);
        holder.reviewStr.setText(review);

    }

    public static class MovieReviewViewHolder extends RecyclerView.ViewHolder {

        TextView reviewAuthor;
        TextView reviewStr;
        LinearLayout movieReviewItem;

        public MovieReviewViewHolder(View itemView) {

            super(itemView);
            reviewAuthor = (TextView) itemView.findViewById(R.id.recyclerview_item_review_author_textview);
            reviewStr = (TextView) itemView.findViewById(R.id.recyclerview_item_review_textview);
            movieReviewItem = (LinearLayout) itemView.findViewById(R.id.movie_review_item);

        }

    }

}
