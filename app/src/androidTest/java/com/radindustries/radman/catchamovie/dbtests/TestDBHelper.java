package com.radindustries.radman.catchamovie.dbtests;

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

    public void testTrailerTable() {

        SQLiteDatabase db = movieDBHelper.getWritableDatabase();
        assertTrue("Error: database is not open", db.isOpen());

        ContentValues cv = new ContentValues();
        cv.put(MoviesContract.TrailerEntry.COL_MOVIE_ID, 5986);
        cv.put(MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_NAME, "Trailer 1");
        cv.put(MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_URL,
                "http://www.youtube.com/watch?v=96599yfw");

        long rowId = db.insert(MoviesContract.TrailerEntry.TABLE_NAME,
                MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_URL, cv);
        assertTrue("Error: could not insert the row in the Trailer Table", rowId != -1);

        Cursor cursor = db.query(MoviesContract.TrailerEntry.TABLE_NAME, null, null, null,
                null, null, null);
        assertTrue("Error: could not get results from the table", cursor.moveToFirst());

        int id = cursor.getInt(cursor.getColumnIndex(MoviesContract.TrailerEntry.COL_MOVIE_ID));
        String name = cursor.getString(cursor
                .getColumnIndex(MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_NAME));
        String url = cursor.getString(cursor
                .getColumnIndex(MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_URL));

        assertEquals(5986, id);
        assertEquals("Trailer 1", name);
        assertEquals("http://www.youtube.com/watch?v=96599yfw", url);

        cursor.close();
        db.close();

    }

    public void testReviewTable() {

        SQLiteDatabase db = movieDBHelper.getWritableDatabase();
        assertTrue("Error: db is not open", db.isOpen());

        ContentValues cv = new ContentValues();
        cv.put(MoviesContract.ReviewEntry.COL_MOVIE_ID, 5964);
        cv.put(MoviesContract.ReviewEntry.COL_MOVIE_REVIEW_AUTHOR, "Sir. Hesketh Bell");
        cv.put(MoviesContract.ReviewEntry.COL_MOVIE_REVIEW, "it was the best movie of all time!");

        long rowId = db.insert(MoviesContract.ReviewEntry.TABLE_NAME,
                MoviesContract.ReviewEntry.COL_MOVIE_REVIEW, cv);
        assertTrue("Error: the row was not inserted in the table", rowId != -1);

        Cursor cursor = db.query(MoviesContract.ReviewEntry.TABLE_NAME, null, null, null,
                null, null, null);
        assertTrue("Error: could not get a row of data back", cursor.moveToFirst());

        int id = cursor.getInt(cursor.getColumnIndex(MoviesContract.ReviewEntry.COL_MOVIE_ID));
        String author = cursor.getString(cursor
                .getColumnIndex(MoviesContract.ReviewEntry.COL_MOVIE_REVIEW_AUTHOR));
        String review = cursor.getString(cursor.getColumnIndex(MoviesContract.ReviewEntry.COL_MOVIE_REVIEW));

        assertEquals(5964, id);
        assertEquals("Sir. Hesketh Bell", author);
        assertEquals("it was the best movie of all time!", review);

        cursor.close();
        db.close();

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        mContext.deleteDatabase(MovieDBHelper.DATABASE_NAME);
    }

}
