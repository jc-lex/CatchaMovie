package com.radindustries.radman.catchamovie.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.radindustries.radman.catchamovie.R;
import com.radindustries.radman.catchamovie.utilities.Review;

import java.util.List;

/**
 * Created by radman on 7/11/16.
 */
public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.MovieReviewViewHolder> {

    private List<Review> mReviewData;

    public MovieReviewsAdapter(List<Review> objects) {
        this.mReviewData = objects;
    }


    @Override
    public MovieReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_review_view, parent, false);
        return new MovieReviewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieReviewViewHolder holder, int position) {
        holder.reviewAuthor.setText(mReviewData.get(position).getAuthor());
        holder.reviewStr.setText(mReviewData.get(position).getReviewStr());
    }

    @Override
    public int getItemCount() {
        return mReviewData.size();
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
