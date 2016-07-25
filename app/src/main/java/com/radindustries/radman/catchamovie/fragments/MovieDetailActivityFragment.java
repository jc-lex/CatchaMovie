package com.radindustries.radman.catchamovie.fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.radindustries.radman.catchamovie.R;
import com.radindustries.radman.catchamovie.adapters.MovieReviewsAdapter;
import com.radindustries.radman.catchamovie.adapters.MovieTrailersAdapter;
import com.radindustries.radman.catchamovie.database.MoviesContract;
import com.radindustries.radman.catchamovie.listeners.RecyclerItemClickListener;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int TRAILER_LOADER = 2;
    private static final int REVIEW_LOADER = 3;
    private static int movie_id;
    private static String sortType;
    private MovieTrailersAdapter trailersAdapter;
    private MovieReviewsAdapter reviewsAdapter;

    public MovieDetailActivityFragment() {}

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == TRAILER_LOADER) {
            return new CursorLoader(
                    getContext(),
                    MoviesContract.TrailerEntry.CONTENT_URI,
                    null,
                    MoviesContract.TrailerEntry.COL_MOVIE_ID + " = ? ",
                    new String[] {Integer.toString(movie_id)},
                    MoviesContract.TrailerEntry._ID + " ASC"
            );
        } else if (id == REVIEW_LOADER) {
            return new CursorLoader(
                    getContext(),
                    MoviesContract.ReviewEntry.CONTENT_URI,
                    null,
                    MoviesContract.ReviewEntry.COL_MOVIE_ID + " = ? ",
                    new String[] {Integer.toString(movie_id)},
                    MoviesContract.ReviewEntry._ID + " ASC"
            );
        } else return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (loader.getId() == TRAILER_LOADER) trailersAdapter.swapCursor(data);
        if (loader.getId() == REVIEW_LOADER) reviewsAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        if (loader.getId() == TRAILER_LOADER) trailersAdapter.swapCursor(null);
        if (loader.getId() == REVIEW_LOADER) reviewsAdapter.swapCursor(null);

    }

    public static class ViewHolder {

        ImageView moviePosterImageView;
        TextView movieTitleTextView;
        TextView movieReleaseDateTextView;
        TextView movieUserRatingTextView;
        TextView moviePlotSynopsisTextView;
        RecyclerView movieReviewsRecyclerView;
        RecyclerView movieTrailersRecyclerView;
        Button favButton;

        public ViewHolder(View view) {

            moviePosterImageView = (ImageView) view.findViewById(R.id.movie_poster_imageView);
            movieTitleTextView = (TextView) view.findViewById(R.id.movie_title_textview);
            movieReleaseDateTextView = (TextView) view.findViewById(R.id.movie_release_date_textview);
            movieUserRatingTextView = (TextView) view.findViewById(R.id.movie_rating_textview);
            moviePlotSynopsisTextView = (TextView) view.findViewById(R.id.movie_overview_textview);
            movieReviewsRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_reviews);
            movieTrailersRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_trailers);
            favButton = (Button) view.findViewById(R.id.fav_button);

        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(TRAILER_LOADER, null, this);
        getLoaderManager().initLoader(REVIEW_LOADER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ViewHolder holder = new ViewHolder(rootView);
        rootView.setTag(holder);

        //get data form intent and set the data to the views
        sortType = getActivity().getIntent().getStringExtra("Sort Type");

        holder.movieReviewsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager reviewManager = new LinearLayoutManager(getContext());
        reviewManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.movieReviewsRecyclerView.setLayoutManager(reviewManager);

        holder.movieTrailersRecyclerView.setHasFixedSize(true);
        LinearLayoutManager trailerManager = new LinearLayoutManager(getContext());
        trailerManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.movieTrailersRecyclerView.setLayoutManager(trailerManager);

        movie_id = getActivity().getIntent().getIntExtra("Id", 0);
        Picasso.with(getContext()).load(getActivity().getIntent()
                .getStringExtra("Image Url")).into(holder.moviePosterImageView);
        holder.movieTitleTextView.setText(getActivity().getIntent().getStringExtra("Title"));
        holder.movieReleaseDateTextView.setText(getActivity()
                .getIntent().getStringExtra("Release Date"));
        holder.movieUserRatingTextView.setText(getActivity()
                .getIntent().getStringExtra("User Rating"));
        holder.moviePlotSynopsisTextView.setText(getActivity()
                .getIntent().getStringExtra("Plot"));

        reviewsAdapter = new MovieReviewsAdapter(null);
        trailersAdapter = new MovieTrailersAdapter(null);
        holder.movieReviewsRecyclerView.setAdapter(reviewsAdapter);
        holder.movieTrailersRecyclerView.setAdapter(trailersAdapter);

        //set click listener to the trailers
        holder.movieTrailersRecyclerView
                .addOnItemTouchListener(
                        new RecyclerItemClickListener(getContext(),
                                holder.movieTrailersRecyclerView,
                                new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                Cursor c = getContext().getContentResolver().query(
                        MoviesContract.TrailerEntry.CONTENT_URI,
                        new String[] {
                                MoviesContract.TrailerEntry._ID,
                                MoviesContract.TrailerEntry.COL_MOVIE_ID,
                                MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_URL
                        },
                        MoviesContract.TrailerEntry.COL_MOVIE_ID + " = ? ",
                        new String[] {Integer.toString(movie_id)},
                        MoviesContract.TrailerEntry._ID + " ASC"
                );
                if (c != null && c.moveToFirst()) {
                    if (checkTrailers(movie_id, c)) {
                        c.moveToPosition(position);
                        Uri trailerUrl = Uri.parse(c.getString(c.getColumnIndex(MoviesContract
                                .TrailerEntry.COL_MOVIE_TRAILER_URL))).buildUpon().build();
                        intent.setData(trailerUrl);
                        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                            startActivity(intent);
                        }
                        c.close();
                    }
                }

            }

            @Override
            public void onLongItemClick(View view, int position) {}
        }));

        //set listener for the favourite button
        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFav(movie_id)) {
                    ContentValues cv = new ContentValues();
                    cv.put(MoviesContract.MoviesEntry.COL_IS_FAVOURITE, 0);
                    int favouriting = getContext().getContentResolver().update(
                            MoviesContract.MoviesEntry.buildMovieUriWithSortTypeAndId(sortType, movie_id),
                            cv, null, null
                    );
                    if (favouriting == 1) {
                        Toast.makeText(getContext(), "Removed from Favourites", Toast.LENGTH_LONG).show();
                    }
                } else {
                    ContentValues cv = new ContentValues();
                    cv.put(MoviesContract.MoviesEntry.COL_IS_FAVOURITE, 1);
                    int favouriting = getContext().getContentResolver().update(
                            MoviesContract.MoviesEntry.buildMovieUriWithSortTypeAndId(sortType, movie_id),
                            cv, null, null
                    );
                    if (favouriting == 1) {
                        Toast.makeText(getContext(), "Added to Favourites", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        return rootView;
    }

    private boolean isFav(int movie_id) {

        Cursor c = getContext().getContentResolver().query(
                MoviesContract.MoviesEntry.CONTENT_URI,
                new String[] {MoviesContract.MoviesEntry.COL_IS_FAVOURITE},
                MoviesContract.MoviesEntry.COL_MOVIE_ID + " = ? ",
                new String[] {Integer.toString(movie_id)},
                null
        );
        if (c != null && c.moveToFirst()) {
            int fav = c.getInt(c.getColumnIndex(MoviesContract.MoviesEntry.COL_IS_FAVOURITE));
            if (fav == 1) {
                c.close();
                return true;
            }
            else if (fav == 0) {
                c.close();
                return false;
            }
        }
        return false;

    }

    private boolean checkTrailers(int movie_id, Cursor c) {

        int count = 0;
        c.moveToFirst();
        do {
            int id = c.getInt(c.getColumnIndex(MoviesContract
                    .TrailerEntry.COL_MOVIE_ID));
            if (id == movie_id) count++;
        } while (c.moveToNext());
        return count == c.getCount();

    }

}
