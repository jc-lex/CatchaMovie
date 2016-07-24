package com.radindustries.radman.catchamovie.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.radindustries.radman.catchamovie.GetMoviesTask;
import com.radindustries.radman.catchamovie.datamodels.GridItem;
import com.radindustries.radman.catchamovie.MovieDetailActivity;
import com.radindustries.radman.catchamovie.R;
import com.radindustries.radman.catchamovie.adapters.MoviesAdapter;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesFragment extends Fragment {

    //private static final String LOG_TAG = MoviesFragment.class.getSimpleName();
    private static MoviesAdapter moviesAdapter;
    private static ArrayList<GridItem> mGridData;

    public MoviesFragment() {}

    private void updateMovies() {
        GetMoviesTask getMoviesTask = new GetMoviesTask(getContext(), moviesAdapter, mGridData);
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
        mGridData = new ArrayList<>();
        moviesAdapter = new MoviesAdapter(getContext(), R.layout.grid_item_movie_posters, mGridData);
        mGridView.setAdapter(moviesAdapter);

        //execute the AsyncTask
        //new GetMoviesTask(getContext(), moviesAdapter, mGridData).execute("popular");

        //create intent for the MovieDetail activity
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GridItem item = (GridItem) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                intent.putExtra("Movie", item);
                startActivity(intent);
            }
        });

        return rootView;
    }

}
