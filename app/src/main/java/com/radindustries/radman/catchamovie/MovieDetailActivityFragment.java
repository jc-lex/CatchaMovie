package com.radindustries.radman.catchamovie;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {
    private ImageView moviePosterImageView;
    private TextView movieTitleTextView;
    private TextView movieReleaseDateTextView;
    private TextView movieUserRatingTextView;
    private TextView moviePlotSynopsisTextView;
    private ListView movieReviewsListView;
    private ListView movieTrailersListView;

    private List<String> reviewsList;
    private List<String> trailersList;
    private MovieReviewsAdapter reviewsAdapter;
    private MovieTrailersAdapter trailersAdapter;


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
        String[] reviews = getActivity().getIntent().getStringArrayExtra("Reviews");
        reviewsList = new ArrayList<>(Arrays.asList(reviews));
        String[] trailers = getActivity().getIntent().getStringArrayExtra("Trailers");
        trailersList = new ArrayList<>(Arrays.asList(trailers));

        //get the views to display the received data
        moviePosterImageView = (ImageView) rootView.findViewById(R.id.movie_poster_imageView);
        movieTitleTextView = (TextView) rootView.findViewById(R.id.movie_title_textview);
        movieReleaseDateTextView = (TextView) rootView.findViewById(R.id.movie_release_date_textview);
        movieUserRatingTextView = (TextView) rootView.findViewById(R.id.movie_rating_textview);
        moviePlotSynopsisTextView = (TextView) rootView.findViewById(R.id.movie_overview_textview);
        movieReviewsListView = (ListView) rootView.findViewById(R.id.list_item_reviews);
        movieTrailersListView = (ListView) rootView.findViewById(R.id.list_item_trailers);

        //set the data to the views
        Picasso.with(getContext()).load(image).into(moviePosterImageView);
        movieTitleTextView.setText(title);
        movieReleaseDateTextView.setText(releaseDate);
        movieUserRatingTextView.setText(userRating);
        moviePlotSynopsisTextView.setText(plotSynopsis);

        reviewsAdapter = new MovieReviewsAdapter(getActivity(),
                R.layout.list_item_review_textview,
                R.id.list_item_review_textview,
                reviewsList);
        trailersAdapter = new MovieTrailersAdapter(getActivity(), R.layout.list_item_trailer_view,
                R.id.list_item_trailer_textview, trailersList);
        movieReviewsListView.setAdapter(reviewsAdapter);
        movieTrailersListView.setAdapter(trailersAdapter);

        movieTrailersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri trailerUri = Uri.parse(trailersList.get(position)).buildUpon().build();
                intent.setData(trailerUri);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null)
                    startActivity(intent);
            }
        });

        return rootView;
    }
}
