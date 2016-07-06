package com.radindustries.radman.catchamovie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesFragment extends Fragment {

    private static final String LOG_TAG = MoviesFragment.class.getSimpleName();
    private static MoviesAdapter moviesAdapter;
    private static ArrayList<GridItem> mGridData;
    private GridView mGridView;

    public MoviesFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //create the GridView, the ArrayList of data, and the ArrayAdapter to the GridView
        mGridView = (GridView) rootView.findViewById(R.id.griditem_movies);
        mGridData = new ArrayList<>();
        moviesAdapter = new MoviesAdapter(getContext(), R.layout.grid_item_movie_posters, mGridData);
        mGridView.setAdapter(moviesAdapter);

        //execute the AsyncTask
        new GetMoviesTask(getContext(), moviesAdapter, mGridData).execute("popular");

        return rootView;
    }

}
