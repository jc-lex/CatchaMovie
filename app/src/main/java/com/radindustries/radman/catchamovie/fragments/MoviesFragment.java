package com.radindustries.radman.catchamovie.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.radindustries.radman.catchamovie.GetMoviesTask;
import com.radindustries.radman.catchamovie.MovieDetailActivity;
import com.radindustries.radman.catchamovie.R;
import com.radindustries.radman.catchamovie.adapters.MoviesAdapter;
import com.radindustries.radman.catchamovie.database.MoviesContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    //private static final String LOG_TAG = MoviesFragment.class.getSimpleName();
    private static MoviesAdapter moviesAdapter;
    private static final int MOVIE_LOADER = 1;

    public MoviesFragment() {}

    private void updateMovies() {
        GetMoviesTask getMoviesTask = new GetMoviesTask(getContext());
        String sortType = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(getString(R.string.pref_movie_sort_type_key),
                        getString(R.string.pref_movie_sort_type_default));
        getMoviesTask.execute(sortType);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //create the GridView and the CursorAdapter to the GridView
        GridView mGridView = (GridView) rootView.findViewById(R.id.griditem_movies);
        moviesAdapter = new MoviesAdapter(getContext(), null, 0);
        mGridView.setAdapter(moviesAdapter);

        //create intent for the MovieDetail activity
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String sortType = PreferenceManager.getDefaultSharedPreferences(getActivity())
                        .getString(getString(R.string.pref_movie_sort_type_key),
                                getString(R.string.pref_movie_sort_type_default));
                if (sortType.equals("favourites")) {
                    Cursor c = getContext().getContentResolver().query(
                            MoviesContract.MoviesEntry.CONTENT_URI,
                            new String[] {
                                    MoviesContract.MoviesEntry._ID,
                                    MoviesContract.MoviesEntry.COL_MOVIE_ID,
                                    MoviesContract.MoviesEntry.COL_MOVIE_POSTER_URL,
                                    MoviesContract.MoviesEntry.COL_MOVIE_TITLE,
                                    MoviesContract.MoviesEntry.COL_MOVIE_RELEASE_DATE,
                                    MoviesContract.MoviesEntry.COL_MOVIE_USER_RATING,
                                    MoviesContract.MoviesEntry.COL_MOVIE_PLOT_SYNOPSIS,
                                    MoviesContract.MoviesEntry.COL_MOVIE_SORT_TYPE_SETTING
                            },
                            MoviesContract.MoviesEntry.COL_IS_FAVOURITE + " = ? AND " +
                                    MoviesContract.MoviesEntry._ID + " = ? ",
                            new String[] {Integer.toString(1), Long.toString(id)},
                            null
                    );
                    if (c != null && c.moveToFirst()) {
                        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                        intent.putExtra("Id", c.getInt(c.getColumnIndex(MoviesContract
                                .MoviesEntry.COL_MOVIE_ID)));
                        intent.putExtra("Image Url", c.getString(c.getColumnIndex(MoviesContract
                                .MoviesEntry.COL_MOVIE_POSTER_URL)));
                        intent.putExtra("Title", c.getString(c.getColumnIndex(MoviesContract
                                .MoviesEntry.COL_MOVIE_TITLE)));
                        intent.putExtra("Release Date", c.getString(c.getColumnIndex(MoviesContract
                                .MoviesEntry.COL_MOVIE_RELEASE_DATE)));
                        intent.putExtra("User Rating", c.getString(c.getColumnIndex(MoviesContract
                                .MoviesEntry.COL_MOVIE_USER_RATING)));
                        intent.putExtra("Plot", c.getString(c.getColumnIndex(MoviesContract
                                .MoviesEntry.COL_MOVIE_PLOT_SYNOPSIS)));
                        intent.putExtra("Sort Type", c.getString(c.getColumnIndex(MoviesContract
                                .MoviesEntry.COL_MOVIE_SORT_TYPE_SETTING)));
                        startActivity(intent);
                        c.close();
                    }
                } else {
                    Cursor c = getContext().getContentResolver().query(
                            MoviesContract.MoviesEntry.CONTENT_URI,
                            new String[]{
                                    MoviesContract.MoviesEntry._ID,
                                    MoviesContract.MoviesEntry.COL_MOVIE_ID,
                                    MoviesContract.MoviesEntry.COL_MOVIE_POSTER_URL,
                                    MoviesContract.MoviesEntry.COL_MOVIE_TITLE,
                                    MoviesContract.MoviesEntry.COL_MOVIE_RELEASE_DATE,
                                    MoviesContract.MoviesEntry.COL_MOVIE_USER_RATING,
                                    MoviesContract.MoviesEntry.COL_MOVIE_PLOT_SYNOPSIS,
                                    MoviesContract.MoviesEntry.COL_MOVIE_SORT_TYPE_SETTING
                            },
                            MoviesContract.MoviesEntry.COL_MOVIE_SORT_TYPE_SETTING + " = ? AND " +
                                    MoviesContract.MoviesEntry._ID + " = ? ",
                            new String[]{sortType, Long.toString(id)},
                            null
                    );
                    if (c != null && c.moveToFirst()) {
                        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                        intent.putExtra("Id", c.getInt(c.getColumnIndex(MoviesContract
                                .MoviesEntry.COL_MOVIE_ID)));
                        intent.putExtra("Image Url", c.getString(c.getColumnIndex(MoviesContract
                                .MoviesEntry.COL_MOVIE_POSTER_URL)));
                        intent.putExtra("Title", c.getString(c.getColumnIndex(MoviesContract
                                .MoviesEntry.COL_MOVIE_TITLE)));
                        intent.putExtra("Release Date", c.getString(c.getColumnIndex(MoviesContract
                                .MoviesEntry.COL_MOVIE_RELEASE_DATE)));
                        intent.putExtra("User Rating", c.getString(c.getColumnIndex(MoviesContract
                                .MoviesEntry.COL_MOVIE_USER_RATING)));
                        intent.putExtra("Plot", c.getString(c.getColumnIndex(MoviesContract
                                .MoviesEntry.COL_MOVIE_PLOT_SYNOPSIS)));
                        intent.putExtra("Sort Type", c.getString(c.getColumnIndex(MoviesContract
                                .MoviesEntry.COL_MOVIE_SORT_TYPE_SETTING)));
                        startActivity(intent);
                        c.close();
                    }
                }
            }
        });

        return rootView;

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortType = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(getString(R.string.pref_movie_sort_type_key),
                        getString(R.string.pref_movie_sort_type_default));
        if (sortType.equals("favourites")) {
            return new CursorLoader(
                    getContext(),
                    MoviesContract.MoviesEntry.CONTENT_URI,
                    null,
                    MoviesContract.MoviesEntry.COL_IS_FAVOURITE + " = ? ",
                    new String[] {Integer.toString(1)},
                    MoviesContract.MoviesEntry._ID + " ASC"
            );
        } else {
            return new CursorLoader(
                    getContext(),
                    MoviesContract.MoviesEntry.buildMovieUriWithSortType(sortType),
                    null, null, null,
                    MoviesContract.MoviesEntry._ID + " ASC"
            );
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        moviesAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        moviesAdapter.swapCursor(null);
    }

}
