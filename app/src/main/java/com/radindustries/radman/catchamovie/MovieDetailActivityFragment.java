package com.radindustries.radman.catchamovie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {
    ImageView imageView;

    public MovieDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        String image = getActivity().getIntent().getStringExtra("Image");
        imageView = (ImageView) rootView.findViewById(R.id.movie_poster_imageView);
        Picasso.with(getContext()).load(image).into(imageView);

        return rootView;
    }
}
