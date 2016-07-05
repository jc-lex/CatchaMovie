package com.radindustries.radman.catchamovie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private MoviesAdapter moviesAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        moviesAdapter = new MoviesAdapter(getContext());
        GridView gridView = (GridView) rootView.findViewById(R.id.griditem_movies);
        gridView.setAdapter(moviesAdapter);


        return rootView;
    }

}
