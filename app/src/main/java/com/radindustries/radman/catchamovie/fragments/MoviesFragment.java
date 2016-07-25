package com.radindustries.radman.catchamovie.fragments;

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
import com.radindustries.radman.catchamovie.R;
import com.radindustries.radman.catchamovie.adapters.MoviesAdapter;
import com.radindustries.radman.catchamovie.database.MoviesContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    //private static final String LOG_TAG = MoviesFragment.class.getSimpleName();
    private static MoviesAdapter moviesAdapter;
    //private static ArrayList<GridItem> mGridData;
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
        //updateMovies();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //create the GridView, the ArrayList of data, and the ArrayAdapter to the GridView
        GridView mGridView = (GridView) rootView.findViewById(R.id.griditem_movies);
        //mGridData = new ArrayList<>();
        moviesAdapter = new MoviesAdapter(getContext(), null, 0);
        mGridView.setAdapter(moviesAdapter);

        //execute the AsyncTask
        //new GetMoviesTask(getContext(), moviesAdapter, mGridData).execute("popular");

        //create intent for the MovieDetail activity
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                GridItem item = (GridItem) parent.getItemAtPosition(position);
//                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
//                intent.putExtra("Movie", item);
//                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortType = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(getString(R.string.pref_movie_sort_type_key),
                        getString(R.string.pref_movie_sort_type_default));
        return new CursorLoader(
                getContext(),
                MoviesContract.MoviesEntry.buildMovieUriWithSortType(sortType),
                null, null, null, null
        );
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
