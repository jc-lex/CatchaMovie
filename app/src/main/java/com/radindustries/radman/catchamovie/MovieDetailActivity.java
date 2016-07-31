package com.radindustries.radman.catchamovie;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.radindustries.radman.catchamovie.fragments.MovieDetailActivityFragment;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String MOVIE_TRAILER_SHARING_HASHTAG = "\n\n#CatchaMovieApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpActionBar();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        MenuItem item = menu.findItem(R.id.action_send_trailer);
        android.widget.ShareActionProvider shareActionProvider =
                (android.widget.ShareActionProvider) item.getActionProvider();
        if (shareActionProvider != null
                && MovieDetailActivityFragment.mSharedTrailerUrl != null) {
            shareActionProvider.setShareIntent(createShareTrailerIntent());
        } else {
            Toast.makeText(this, "Unable to share trailer", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private Intent createShareTrailerIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,
                MovieDetailActivityFragment.mSharedTrailerUrl.toString() +
                        MOVIE_TRAILER_SHARING_HASHTAG);
        return intent;
    }

}
