package com.radindustries.radman.catchamovie;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.radindustries.radman.catchamovie.database.MovieDBHelper;
import com.radindustries.radman.catchamovie.database.MoviesContract;

/**
 * Created by radman on 7/17/16.
 */
public class TestDBHelper extends AndroidTestCase {

    private MovieDBHelper movieDBHelper;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        movieDBHelper = new MovieDBHelper(mContext);
    }

    public void testMoviesTable() {
        SQLiteDatabase db = movieDBHelper.getWritableDatabase();
        assertTrue("Error: database not open", db.isOpen());

        ContentValues cv = new ContentValues();
        cv.put(MoviesContract.MoviesEntry.COL_MOVIE_TITLE, "Batman Vs Superman");
        cv.put(MoviesContract.MoviesEntry.COL_MOVIE_POSTER_URL,
                "http://www.image.com/alsbravierhnv.jpg");
        cv.put(MoviesContract.MoviesEntry.COL_MOVIE_RELEASE_DATE, "3 Jul 2013");
        cv.put(MoviesContract.MoviesEntry.COL_MOVIE_USER_RATING, "7 / 10");
        cv.put(MoviesContract.MoviesEntry.COL_MOVIE_PLOT_SYNOPSIS,
                "It was a dark and stormy night...");
        cv.put(MoviesContract.MoviesEntry.COL_MOVIE_SORT_TYPE_SETTING, "popular");
        cv.put(MoviesContract.MoviesEntry.COL_IS_FAVOURITE, 1);
        cv.put(MoviesContract.MoviesEntry.COL_MOVIE_ID, 6785);

        long rowId = db.insert(MoviesContract.MoviesEntry.TABLE_NAME, null, cv);
        assertTrue("Error: failed to insert data", rowId != -1);

        Cursor cursor = db.query(MoviesContract.MoviesEntry.TABLE_NAME,
                null, null, null, null, null, null);
        assertTrue("Error: no data result set received", cursor.moveToFirst());

        int movieId = cursor.getInt(cursor
                    .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_ID));
        String title = cursor.getString(cursor
                    .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_TITLE));
        String posterUrl = cursor.getString(cursor
                    .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_POSTER_URL));
        String releaseDate = cursor.getString(cursor
                    .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_RELEASE_DATE));
        String plot = cursor.getString(cursor
                    .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_PLOT_SYNOPSIS));
        String sort = cursor.getString(cursor
                    .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_SORT_TYPE_SETTING));
        int isFav = cursor.getInt(cursor
                    .getColumnIndex(MoviesContract.MoviesEntry.COL_IS_FAVOURITE));
        String rating = cursor.getString(cursor
                    .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_USER_RATING));

        assertEquals("Batman Vs Superman", title);
        assertEquals("http://www.image.com/alsbravierhnv.jpg", posterUrl);
        assertEquals("3 Jul 2013", releaseDate);
        assertEquals("It was a dark and stormy night...", plot);
        assertEquals("popular", sort);
        assertEquals(1, isFav);
        assertEquals("7 / 10", rating);
        assertEquals(6785, movieId);

        cursor.close();
        db.close();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        mContext.deleteDatabase(MovieDBHelper.DATABASE_NAME);
    }
}
