package com.radindustries.radman.catchamovie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {
    ImageView moviePosterImageView;
    TextView movieTitleTextView;
    TextView movieReleaseDateTextView;
    TextView movieUserRatingTextView;
    TextView moviePlotSynopsisTextView;

    public MovieDetailActivityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        //get data form intent
        String image = getActivity().getIntent().getStringExtra("Image");
        String title = getActivity().getIntent().getStringExtra("Title");
        String releaseDate = getActivity().getIntent().getStringExtra("Release Date");
        String userRating = getActivity().getIntent().getStringExtra("User Rating");
        String plotSynopsis = getActivity().getIntent().getStringExtra("Plot Synopsis");

        //get the views to display the received data
        moviePosterImageView = (ImageView) rootView.findViewById(R.id.movie_poster_imageView);
        movieTitleTextView = (TextView) rootView.findViewById(R.id.movie_title_textview);
        movieReleaseDateTextView = (TextView) rootView.findViewById(R.id.movie_release_date_textview);
        movieUserRatingTextView = (TextView) rootView.findViewById(R.id.movie_rating_textview);
        moviePlotSynopsisTextView = (TextView) rootView.findViewById(R.id.movie_overview_textview);

        //set the data to the views
        Picasso.with(getContext()).load(image).into(moviePosterImageView);
        movieTitleTextView.setText(title);
        movieReleaseDateTextView.setText(releaseDate);
        movieUserRatingTextView.setText(userRating);
        moviePlotSynopsisTextView.setText(plotSynopsis);

        return rootView;
    }
}
