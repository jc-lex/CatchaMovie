package com.radindustries.radman.catchamovie.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.radindustries.radman.catchamovie.R;
import com.radindustries.radman.catchamovie.datamodels.Trailer;

import java.util.List;

/**
 * Created by radman on 7/12/16.
 */
public class MovieTrailersAdapter extends
        RecyclerView.Adapter<MovieTrailersAdapter.MovieTrailerViewHolder> {

    private List<Trailer> mTrailerData;

    public MovieTrailersAdapter(List<Trailer> objects) {
        this.mTrailerData = objects;
    }

    @Override
    public MovieTrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_trailer_view, parent, false);
        return new MovieTrailerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieTrailerViewHolder holder, int position) {
        holder.trailerImage.setImageResource(R.drawable.youtube_512);
        holder.trailerName.setText(mTrailerData.get(position).getName());
        //holder.trailerUrl.setText(mTrailerData.get(position).getUrl());
    }

    @Override
    public int getItemCount() {
        return mTrailerData.size();
    }

    public static class MovieTrailerViewHolder
            extends RecyclerView.ViewHolder {

        ImageView trailerImage;
        TextView trailerName;
        //TextView trailerUrl;
        LinearLayout movieTrailerItem;
        

        public MovieTrailerViewHolder(View itemView) {
            super(itemView);
            movieTrailerItem = (LinearLayout) itemView.findViewById(R.id.movie_trailer_item);
            trailerImage = (ImageView) itemView.findViewById(R.id.movie_trailer_yt_image);
            trailerName = (TextView) itemView.findViewById(R.id.recyclerview_item_trailer_title_textview);
            //trailerUrl = (TextView) itemView.findViewById(R.id.recyclerview_item_trailer_url_textview);
        }
    }
}
