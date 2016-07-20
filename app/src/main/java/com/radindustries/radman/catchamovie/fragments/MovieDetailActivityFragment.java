package com.radindustries.radman.catchamovie.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.radindustries.radman.catchamovie.R;
import com.radindustries.radman.catchamovie.adapters.MovieReviewsAdapter;
import com.radindustries.radman.catchamovie.adapters.MovieTrailersAdapter;
import com.radindustries.radman.catchamovie.listeners.RecyclerItemClickListener;
import com.radindustries.radman.catchamovie.datamodels.GridItem;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {

    private ImageView moviePosterImageView;
    private TextView movieTitleTextView;
    private TextView movieReleaseDateTextView;
    private TextView movieUserRatingTextView;
    private TextView moviePlotSynopsisTextView;
    private RecyclerView movieReviewsRecyclerView;
    private RecyclerView movieTrailersRecyclerView;

    private MovieReviewsAdapter reviewsAdapter;
    private MovieTrailersAdapter trailersAdapter;

    public MovieDetailActivityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        //get data form intent
        final GridItem item = getActivity().getIntent().getExtras().getParcelable("Movie");

        //get the views to display the received data
        moviePosterImageView = (ImageView) rootView.findViewById(R.id.movie_poster_imageView);
        movieTitleTextView = (TextView) rootView.findViewById(R.id.movie_title_textview);
        movieReleaseDateTextView = (TextView) rootView.findViewById(R.id.movie_release_date_textview);
        movieUserRatingTextView = (TextView) rootView.findViewById(R.id.movie_rating_textview);
        moviePlotSynopsisTextView = (TextView) rootView.findViewById(R.id.movie_overview_textview);

        movieReviewsRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_reviews);
        movieReviewsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager reviewManager = new LinearLayoutManager(getContext());
        reviewManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        movieReviewsRecyclerView.setLayoutManager(reviewManager);

        movieTrailersRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_trailers);
        movieTrailersRecyclerView.setHasFixedSize(true);
        LinearLayoutManager trailerManager = new LinearLayoutManager(getContext());
        trailerManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        movieTrailersRecyclerView.setLayoutManager(trailerManager);

        //set the data to the views
        //int id = item.getMovieId();
        Picasso.with(getContext()).load(item.getImage()).into(moviePosterImageView);
        movieTitleTextView.setText(item.getTitle());
        movieReleaseDateTextView.setText(item.getReleaseDate());
        movieUserRatingTextView.setText(item.getUserRating());
        moviePlotSynopsisTextView.setText(item.getPlotSynopsis());

        reviewsAdapter = new MovieReviewsAdapter(item.getReviewArrayList());
        trailersAdapter = new MovieTrailersAdapter(item.getTrailerArrayList());
        movieReviewsRecyclerView.setAdapter(reviewsAdapter);
        movieTrailersRecyclerView.setAdapter(trailersAdapter);

        //set click listener to the trailers
        movieTrailersRecyclerView
                .addOnItemTouchListener(
                        new RecyclerItemClickListener(getContext(),
                                movieTrailersRecyclerView,
                                new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(item.getTrailerArrayList().get(position)
                        .getUrl()).buildUpon().build());
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {}
        }));

        return rootView;
    }
}
