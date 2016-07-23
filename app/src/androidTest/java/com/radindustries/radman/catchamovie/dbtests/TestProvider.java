package com.radindustries.radman.catchamovie.dbtests;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.radindustries.radman.catchamovie.database.MovieDBHelper;
import com.radindustries.radman.catchamovie.database.MoviesContract;
import com.radindustries.radman.catchamovie.database.MoviesProvider;

/**
 * Created by radman on 7/19/16.
 */
public class TestProvider extends AndroidTestCase {

    private MovieDBHelper movieDBHelper;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        movieDBHelper = new MovieDBHelper(mContext);
    }

    public void testGetType() {
        //content://com.radindustries.radman.catchamovie.database/movies
        String type = mContext.getContentResolver().getType(MoviesContract.MoviesEntry.CONTENT_URI);
        assertEquals("Error: The MovieEntry CONTENT_URI should return CONTENT_DIR_TYPE",
                MoviesContract.MoviesEntry.CONTENT_DIR_TYPE, type);

        //content://com.radindustries.radman.catchamovie.database/movies/popular
        String testSortType = "popular";
        type = mContext.getContentResolver().getType(MoviesContract.MoviesEntry
                .buildMovieUriWithSortType(testSortType));
        assertEquals("Error: The MovieEntry CONTENT_URI with sortType " +
                "should return CONTENT_DIR_TYPE",MoviesContract.MoviesEntry.CONTENT_DIR_TYPE, type);

        //content://com.radindustries.radman.catchamovie.database/movies/popular/23
        int testId = 23;
        type = mContext.getContentResolver().getType(MoviesContract.MoviesEntry
                .buildMovieUriWithSortTypeAndId(testSortType, testId));
        assertEquals("Error: The MovieEntry CONTENT_URI with sort type " +
                "and id should return CONTENT_ITEM_TYPE", MoviesContract.MoviesEntry.CONTENT_ITEM_TYPE, type);

        //content://com.radindustries.radman.catchamovie.database/trailers
        type = mContext.getContentResolver().getType(MoviesContract.TrailerEntry.CONTENT_URI);
        assertEquals("Error: The TrailerEntry CONTENT_URI should return CONTENT_DIR_TYPE",
                MoviesContract.TrailerEntry.CONTENT_DIR_TYPE, type);

        //content://com.radindustries.radman.catchamovie.database/reviews
        type = mContext.getContentResolver().getType(MoviesContract.ReviewEntry.CONTENT_URI);
        assertEquals("Error: the ReviewEntry CONTENT_URI should return CONTENT_DIR_TYPE",
                MoviesContract.ReviewEntry.CONTENT_DIR_TYPE, type);

    }

    public void testProviderRegistry() {

        PackageManager pm = mContext.getPackageManager();
        ComponentName componentName = new ComponentName(mContext.getPackageName(),
                MoviesProvider.class.getName());
        try {
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);
            // Make sure that the registered authority matches the authority from the Contract.
            assertEquals("Error: MovieProvider registered with authority: " + providerInfo.authority +
                            " instead of authority: " + MoviesContract.CONTENT_AUTHORITY,
                    providerInfo.authority, MoviesContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            assertTrue("Error: MovieProvider not registered at " + mContext.getPackageName(),
                    false);
        }

    }

    public void testMovieQuery() {

        SQLiteDatabase db = movieDBHelper.getWritableDatabase();
        assertTrue("Error: the db is not open", db.isOpen());

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

//        //this tests the query method in the provider for Uri = CONTENT_URI
//        //using the content provider through the content resolver
//        Cursor cursor = mContext.getContentResolver()
//                .query(MoviesContract.MoviesEntry.CONTENT_URI,
//                        null, null, null, null);
//        assertTrue("Error: no data result set received", cursor.moveToFirst());
//
//        int movieId = cursor.getInt(cursor
//                .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_ID));
//        String title = cursor.getString(cursor
//                .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_TITLE));
//        String posterUrl = cursor.getString(cursor
//                .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_POSTER_URL));
//        String releaseDate = cursor.getString(cursor
//                .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_RELEASE_DATE));
//        String plot = cursor.getString(cursor
//                .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_PLOT_SYNOPSIS));
//        String sort = cursor.getString(cursor
//                .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_SORT_TYPE_SETTING));
//        int isFav = cursor.getInt(cursor
//                .getColumnIndex(MoviesContract.MoviesEntry.COL_IS_FAVOURITE));
//        String rating = cursor.getString(cursor
//                .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_USER_RATING));
//
//        assertEquals("Batman Vs Superman", title);
//        assertEquals("http://www.image.com/alsbravierhnv.jpg", posterUrl);
//        assertEquals("3 Jul 2013", releaseDate);
//        assertEquals("It was a dark and stormy night...", plot);
//        assertEquals("popular", sort);
//        assertEquals(1, isFav);
//        assertEquals("7 / 10", rating);
//        assertEquals(6785, movieId);

        //this test the query method in provider for Uri = CONTENT_URI/popular
//        Cursor cursor = mContext.getContentResolver().query(
//                MoviesContract.MoviesEntry.buildMovieUriWithSortType("popular"),
//                null, null, null, null
//        );
//        assertTrue("Error: no data result set received", cursor.moveToFirst());
//        assertTrue(cursor.getCount() == 1);
//        assertTrue(cursor.getColumnCount() == 2);
//
//        int movieId = cursor.getInt(cursor
//                .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_ID));
//        String posterUrl = cursor.getString(cursor
//                .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_POSTER_URL));
//
//        assertEquals("http://www.image.com/alsbravierhnv.jpg", posterUrl);
//        assertEquals(6785, movieId);

        //this tests the query method in the provider for Uri = CONTENT_URI/1 <-- it doesnt work
        //for some reason. will have to use the original Uri to query for favourites manually
        //like as shown below
//        Cursor cursor = mContext.getContentResolver().query(
//                MoviesContract.MoviesEntry.CONTENT_URI,
//                new String[] {MoviesContract.MoviesEntry.COL_MOVIE_ID, MoviesContract.MoviesEntry.COL_MOVIE_POSTER_URL},
//                MoviesContract.MoviesEntry.COL_IS_FAVOURITE + " = ? ",
//                new String[] {"1"}, null
//        );
//        assertTrue("Error: no data result set received", cursor.moveToFirst());
//        assertTrue(cursor.getCount() == 1);
//        assertTrue(cursor.getColumnCount() == 2);
//
//        int movieId = cursor.getInt(cursor
//                .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_ID));
//        String posterUrl = cursor.getString(cursor
//                .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_POSTER_URL));
//
//        assertEquals("http://www.image.com/alsbravierhnv.jpg", posterUrl);
//        assertEquals(6785, movieId);

        //this tests the query method in the provider for Uri = CONTENT_URI/1/6785 <--- this does
        //not work. use this process below to get the favourite movie details
        Cursor cursor = mContext.getContentResolver().query(
//                MoviesContract.MoviesEntry.CONTENT_URI,
//                new String[] {
//                        MoviesContract.MoviesEntry.COL_MOVIE_TITLE,
//                        MoviesContract.MoviesEntry.COL_MOVIE_POSTER_URL,
//                        MoviesContract.MoviesEntry.COL_MOVIE_RELEASE_DATE,
//                        MoviesContract.MoviesEntry.COL_MOVIE_USER_RATING,
//                        MoviesContract.MoviesEntry.COL_MOVIE_PLOT_SYNOPSIS
//                },
//                MoviesContract.MoviesEntry.COL_MOVIE_ID + " = ? AND " +
//                        MoviesContract.MoviesEntry.COL_IS_FAVOURITE + " = ? ",
//                new String[] {"6785", "1"},
//                null
                MoviesContract.MoviesEntry.buildMovieUriWithSortTypeAndId("popular", 6785), //it works
                null, null, null, null
        );
        assertTrue("Error: no data result set received", cursor.moveToFirst());
        assertTrue(cursor.getCount() == 1);
        assertTrue(cursor.getColumnCount() == 5);

        String title = cursor.getString(cursor
                .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_TITLE));
        String posterUrl = cursor.getString(cursor
                .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_POSTER_URL));
        String releaseDate = cursor.getString(cursor
                .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_RELEASE_DATE));
        String plot = cursor.getString(cursor
                .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_PLOT_SYNOPSIS));
        String rating = cursor.getString(cursor
                .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_USER_RATING));

        assertEquals("Batman Vs Superman", title);
        assertEquals("http://www.image.com/alsbravierhnv.jpg", posterUrl);
        assertEquals("3 Jul 2013", releaseDate);
        assertEquals("It was a dark and stormy night...", plot);
        assertEquals("7 / 10", rating);

        cursor.close();
        db.close();

    }

    public void testTrailerQuery() {

        SQLiteDatabase db = movieDBHelper.getWritableDatabase();
        assertTrue("Error: db couldn't be opened", db.isOpen());

        ContentValues cv = new ContentValues();
        cv.put(MoviesContract.TrailerEntry.COL_MOVIE_ID, 5986);
        cv.put(MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_NAME, "Trailer 1");
        cv.put(MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_URL,
                "http://www.youtube.com/watch?v=96599yfw");

        long rowId = db.insert(MoviesContract.TrailerEntry.TABLE_NAME,
                MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_URL, cv);
        assertTrue("Error: could not insert the row in the Trailer Table", rowId != -1);

//        Cursor cursor = mContext.getContentResolver().query(
//                MoviesContract.TrailerEntry.CONTENT_URI, null, null, null, null
//        );

        //use the following code to query trailers of a given movie by its id
        Cursor cursor = mContext.getContentResolver().query(
                MoviesContract.TrailerEntry.CONTENT_URI,
                new String[]{
                        MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_NAME,
                        MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_URL
                },
                MoviesContract.TrailerEntry.COL_MOVIE_ID + " = ? ",
                new String[] {"5986"},
                null
        );
        assertTrue("Error: could not get results from the table", cursor.moveToFirst());
        assertTrue(cursor.getCount() == 1);
        assertTrue(cursor.getColumnCount() == 2);

        //int id = cursor.getInt(cursor.getColumnIndex(MoviesContract.TrailerEntry.COL_MOVIE_ID));
        String name = cursor.getString(cursor
                .getColumnIndex(MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_NAME));
        String url = cursor.getString(cursor
                .getColumnIndex(MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_URL));

        //assertEquals(5986, id);
        assertEquals("Trailer 1", name);
        assertEquals("http://www.youtube.com/watch?v=96599yfw", url);

        cursor.close();
        db.close();

    }

    public void testReviewQuery() {

        SQLiteDatabase db = movieDBHelper.getWritableDatabase();
        assertTrue("Error: db could not be opened", db.isOpen());

        ContentValues cv = new ContentValues();
        cv.put(MoviesContract.ReviewEntry.COL_MOVIE_ID, 5964);
        cv.put(MoviesContract.ReviewEntry.COL_MOVIE_REVIEW_AUTHOR, "Sir. Hesketh Bell");
        cv.put(MoviesContract.ReviewEntry.COL_MOVIE_REVIEW, "it was the best movie of all time!");

        long rowId = db.insert(MoviesContract.ReviewEntry.TABLE_NAME,
                MoviesContract.ReviewEntry.COL_MOVIE_REVIEW, cv);
        assertTrue("Error: the row was not inserted in the table", rowId != -1);

//        Cursor cursor = mContext.getContentResolver().query(
//                MoviesContract.ReviewEntry.CONTENT_URI, null, null, null, null
//        );

        Cursor cursor = mContext.getContentResolver().query(
                MoviesContract.ReviewEntry.CONTENT_URI,
                new String[]{
                        MoviesContract.ReviewEntry.COL_MOVIE_REVIEW_AUTHOR,
                        MoviesContract.ReviewEntry.COL_MOVIE_REVIEW
                },
                MoviesContract.ReviewEntry.COL_MOVIE_ID + " = ? ",
                new String[] {"5964"},
                null
        );
        assertTrue("Error: could not get a row of data back", cursor.moveToFirst());
        assertTrue(cursor.getCount() == 1);
        assertTrue(cursor.getColumnCount() == 2);

        //int id = cursor.getInt(cursor.getColumnIndex(MoviesContract.ReviewEntry.COL_MOVIE_ID));
        String author = cursor.getString(cursor
                .getColumnIndex(MoviesContract.ReviewEntry.COL_MOVIE_REVIEW_AUTHOR));
        String review = cursor.getString(cursor.getColumnIndex(MoviesContract.ReviewEntry.COL_MOVIE_REVIEW));

        //assertEquals(5964, id);
        assertEquals("Sir. Hesketh Bell", author);
        assertEquals("it was the best movie of all time!", review);

        cursor.close();
        db.close();

    }

    public void testMovieInsert() {

        SQLiteDatabase db = movieDBHelper.getWritableDatabase();
        assertTrue("Error: the db is not open", db.isOpen());

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

        Uri movieUri = mContext.getContentResolver()
                .insert(MoviesContract.MoviesEntry.CONTENT_URI, cv);
        assertTrue(movieUri != null);

        long _id = ContentUris.parseId(movieUri);
        assertTrue(_id != -1);

        Cursor cursor = mContext.getContentResolver().query(
                MoviesContract.MoviesEntry.CONTENT_URI,
                new String[] {
                        MoviesContract.MoviesEntry.COL_MOVIE_TITLE,
                        MoviesContract.MoviesEntry.COL_MOVIE_POSTER_URL,
                        MoviesContract.MoviesEntry.COL_MOVIE_RELEASE_DATE,
                        MoviesContract.MoviesEntry.COL_MOVIE_USER_RATING,
                        MoviesContract.MoviesEntry.COL_MOVIE_PLOT_SYNOPSIS
                },
                MoviesContract.MoviesEntry.COL_MOVIE_ID + " = ? AND " +
                        MoviesContract.MoviesEntry.COL_IS_FAVOURITE + " = ? ",
                new String[] {"6785", "1"},
                null
        );
        assertTrue("Error: no data result set received", cursor.moveToFirst());
        assertTrue(cursor.getCount() == 1);
        assertTrue(cursor.getColumnCount() == 5);

        String title = cursor.getString(cursor
                .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_TITLE));
        String posterUrl = cursor.getString(cursor
                .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_POSTER_URL));
        String releaseDate = cursor.getString(cursor
                .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_RELEASE_DATE));
        String plot = cursor.getString(cursor
                .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_PLOT_SYNOPSIS));
        String rating = cursor.getString(cursor
                .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_USER_RATING));

        assertEquals("Batman Vs Superman", title);
        assertEquals("http://www.image.com/alsbravierhnv.jpg", posterUrl);
        assertEquals("3 Jul 2013", releaseDate);
        assertEquals("It was a dark and stormy night...", plot);
        assertEquals("7 / 10", rating);

        cursor.close();
        db.close();

    }

    public void testTrailerInsert() {

        SQLiteDatabase db = movieDBHelper.getWritableDatabase();
        assertTrue("Error: db couldn't be opened", db.isOpen());

        ContentValues cv = new ContentValues();
        cv.put(MoviesContract.TrailerEntry.COL_MOVIE_ID, 5986);
        cv.put(MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_NAME, "Trailer 1");
        cv.put(MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_URL,
                "http://www.youtube.com/watch?v=96599yfw");

        Uri trailerUri = mContext.getContentResolver().insert(MoviesContract.TrailerEntry.CONTENT_URI, cv);
        assertTrue(trailerUri != null);

        long _id = ContentUris.parseId(trailerUri);
        assertTrue(_id != -1);

        Cursor cursor = mContext.getContentResolver().query(
                MoviesContract.TrailerEntry.CONTENT_URI,
                new String[]{
                        MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_NAME,
                        MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_URL
                },
                MoviesContract.TrailerEntry.COL_MOVIE_ID + " = ? ",
                new String[] {"5986"},
                null
        );
        assertTrue("Error: could not get results from the table", cursor.moveToFirst());
        assertTrue(cursor.getCount() == 1);
        assertTrue(cursor.getColumnCount() == 2);

        //int id = cursor.getInt(cursor.getColumnIndex(MoviesContract.TrailerEntry.COL_MOVIE_ID));
        String name = cursor.getString(cursor
                .getColumnIndex(MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_NAME));
        String url = cursor.getString(cursor
                .getColumnIndex(MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_URL));

        //assertEquals(5986, id);
        assertEquals("Trailer 1", name);
        assertEquals("http://www.youtube.com/watch?v=96599yfw", url);

        cursor.close();
        db.close();

    }

    public void testReviewInsert() {

        SQLiteDatabase db = movieDBHelper.getWritableDatabase();
        assertTrue("Error: db could not be opened", db.isOpen());

        ContentValues cv = new ContentValues();
        cv.put(MoviesContract.ReviewEntry.COL_MOVIE_ID, 5964);
        cv.put(MoviesContract.ReviewEntry.COL_MOVIE_REVIEW_AUTHOR, "Sir. Hesketh Bell");
        cv.put(MoviesContract.ReviewEntry.COL_MOVIE_REVIEW, "it was the best movie of all time!");

        Uri reviewUri = mContext.getContentResolver().insert(MoviesContract.ReviewEntry.CONTENT_URI, cv);
        assertTrue(reviewUri != null);

        long _id = ContentUris.parseId(reviewUri);
        assertTrue(_id != -1);

        Cursor cursor = mContext.getContentResolver().query(
                MoviesContract.ReviewEntry.CONTENT_URI,
                new String[]{
                        MoviesContract.ReviewEntry.COL_MOVIE_REVIEW_AUTHOR,
                        MoviesContract.ReviewEntry.COL_MOVIE_REVIEW
                },
                MoviesContract.ReviewEntry.COL_MOVIE_ID + " = ? ",
                new String[] {"5964"},
                null
        );
        assertTrue("Error: could not get a row of data back", cursor.moveToFirst());
        assertTrue(cursor.getCount() == 1);
        assertTrue(cursor.getColumnCount() == 2);

        //int id = cursor.getInt(cursor.getColumnIndex(MoviesContract.ReviewEntry.COL_MOVIE_ID));
        String author = cursor.getString(cursor
                .getColumnIndex(MoviesContract.ReviewEntry.COL_MOVIE_REVIEW_AUTHOR));
        String review = cursor.getString(cursor.getColumnIndex(MoviesContract.ReviewEntry.COL_MOVIE_REVIEW));

        //assertEquals(5964, id);
        assertEquals("Sir. Hesketh Bell", author);
        assertEquals("it was the best movie of all time!", review);

        cursor.close();
        db.close();

    }

    private static final int BULK_INSERT_RECORDS_TO_INSERT = 10;

    private static ContentValues[] bulkInsertMovieValues() {
        ContentValues[] cvs = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {

            ContentValues cv = new ContentValues();
            cv.put(MoviesContract.MoviesEntry.COL_MOVIE_TITLE, "Batman Vs Superman");
            cv.put(MoviesContract.MoviesEntry.COL_MOVIE_POSTER_URL,
                    "http://www.image.com/alsbravierhnv.jpg");
            cv.put(MoviesContract.MoviesEntry.COL_MOVIE_RELEASE_DATE, "3 Jul 2013");
            cv.put(MoviesContract.MoviesEntry.COL_MOVIE_USER_RATING, "7 / 10");
            cv.put(MoviesContract.MoviesEntry.COL_MOVIE_PLOT_SYNOPSIS,
                    "It was a dark and stormy night...");
            cv.put(MoviesContract.MoviesEntry.COL_MOVIE_SORT_TYPE_SETTING, "popular");
            cv.put(MoviesContract.MoviesEntry.COL_IS_FAVOURITE, 0);
            cv.put(MoviesContract.MoviesEntry.COL_MOVIE_ID, i + 1);
            cvs[i] = cv;

        }
        return cvs;

    }

    private static void checkRows(Cursor cursor, ContentValues[] cvs) {

        cursor.moveToFirst();

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++, cursor.moveToNext()) {

            String title = cursor.getString(cursor
                    .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_TITLE));
            String posterUrl = cursor.getString(cursor
                    .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_POSTER_URL));
            String releaseDate = cursor.getString(cursor
                    .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_RELEASE_DATE));
            String plot = cursor.getString(cursor
                    .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_PLOT_SYNOPSIS));
            String rating = cursor.getString(cursor
                    .getColumnIndex(MoviesContract.MoviesEntry.COL_MOVIE_USER_RATING));

            assertEquals(cvs[i].getAsString(MoviesContract.MoviesEntry.COL_MOVIE_TITLE), title);
            assertEquals(cvs[i].getAsString(MoviesContract.MoviesEntry.COL_MOVIE_POSTER_URL), posterUrl);
            assertEquals(cvs[i].getAsString(MoviesContract.MoviesEntry.COL_MOVIE_RELEASE_DATE), releaseDate);
            assertEquals(cvs[i].getAsString(MoviesContract.MoviesEntry.COL_MOVIE_PLOT_SYNOPSIS), plot);
            assertEquals(cvs[i].getAsString(MoviesContract.MoviesEntry.COL_MOVIE_USER_RATING), rating);

        }

    }

    public void testBulkInsert() {

        ContentValues[] cvs = bulkInsertMovieValues();

        int insertCount = mContext.getContentResolver().bulkInsert(
                MoviesContract.MoviesEntry.CONTENT_URI, cvs
        );
        assertEquals(BULK_INSERT_RECORDS_TO_INSERT, insertCount);

        Cursor cursor = mContext.getContentResolver().query(
                MoviesContract.MoviesEntry.CONTENT_URI,
                new String[] {
                        MoviesContract.MoviesEntry.COL_MOVIE_TITLE,
                        MoviesContract.MoviesEntry.COL_MOVIE_POSTER_URL,
                        MoviesContract.MoviesEntry.COL_MOVIE_RELEASE_DATE,
                        MoviesContract.MoviesEntry.COL_MOVIE_USER_RATING,
                        MoviesContract.MoviesEntry.COL_MOVIE_PLOT_SYNOPSIS
                },
                null,
                null,
                MoviesContract.MoviesEntry.COL_MOVIE_ID + " ASC"
        );
        assertTrue("Error: could not get rows back", cursor.moveToFirst());
        assertEquals(BULK_INSERT_RECORDS_TO_INSERT, cursor.getCount());

        checkRows(cursor, cvs);
        int rowsDeleted = mContext.getContentResolver().delete(
                MoviesContract.MoviesEntry.CONTENT_URI, null, null
        );
        assertEquals(10, rowsDeleted);
        cursor.close();

    }

    public void testUpdateMovieFavs() {

        SQLiteDatabase db = movieDBHelper.getWritableDatabase();
        assertTrue("Error: the db is not open", db.isOpen());

        ContentValues cv = new ContentValues();
        cv.put(MoviesContract.MoviesEntry.COL_MOVIE_TITLE, "Batman Vs Superman");
        cv.put(MoviesContract.MoviesEntry.COL_MOVIE_POSTER_URL,
                "http://www.image.com/alsbravierhnv.jpg");
        cv.put(MoviesContract.MoviesEntry.COL_MOVIE_RELEASE_DATE, "3 Jul 2013");
        cv.put(MoviesContract.MoviesEntry.COL_MOVIE_USER_RATING, "7 / 10");
        cv.put(MoviesContract.MoviesEntry.COL_MOVIE_PLOT_SYNOPSIS,
                "It was a dark and stormy night...");
        cv.put(MoviesContract.MoviesEntry.COL_MOVIE_SORT_TYPE_SETTING, "popular");
        cv.put(MoviesContract.MoviesEntry.COL_IS_FAVOURITE, 0);
        cv.put(MoviesContract.MoviesEntry.COL_MOVIE_ID, 6785);

        long rowId = db.insert(MoviesContract.MoviesEntry.TABLE_NAME, null, cv);
        assertTrue("Error: failed to insert data", rowId != -1);

        ContentValues favUpdate = new ContentValues();
        favUpdate.put(MoviesContract.MoviesEntry.COL_IS_FAVOURITE, 1);

        int rowUpdated = mContext.getContentResolver().update(
                MoviesContract.MoviesEntry.buildMovieUriWithSortTypeAndId("popular", 6785),
                favUpdate,
                null,
                null
        );
        assertEquals("Error: could not update the row", 1, rowUpdated);

        Cursor cursor = mContext.getContentResolver().query(
                MoviesContract.MoviesEntry.CONTENT_URI,
                new String[] {MoviesContract.MoviesEntry.COL_IS_FAVOURITE},
                MoviesContract.MoviesEntry.COL_MOVIE_ID + " = ? ",
                new String[] {"6785"},
                null
        );
        assertTrue(cursor.moveToFirst());
        assertTrue(cursor.getColumnCount() == 1);
        assertTrue(cursor.getCount() == 1);

        int favValue = cursor.getInt(cursor
                .getColumnIndex(MoviesContract.MoviesEntry.COL_IS_FAVOURITE));
        assertEquals("Error: didnt update correctly", 1, favValue);

        cursor.close();
        db.close();

    }

    public void testDelete() {

        SQLiteDatabase db = movieDBHelper.getWritableDatabase();
        assertTrue("Error: the db is not open", db.isOpen());

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

        Uri movieUri = mContext.getContentResolver()
                .insert(MoviesContract.MoviesEntry.CONTENT_URI, cv);
        assertTrue(movieUri != null);

        long _id = ContentUris.parseId(movieUri);
        assertTrue(_id != -1);

        ContentValues cv1 = new ContentValues();
        cv1.put(MoviesContract.TrailerEntry.COL_MOVIE_ID, 5986);
        cv1.put(MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_NAME, "Trailer 1");
        cv1.put(MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_URL,
                "http://www.youtube.com/watch?v=96599yfw");

        Uri trailerUri = mContext.getContentResolver().insert(MoviesContract.TrailerEntry.CONTENT_URI, cv1);
        assertTrue(trailerUri != null);

        long _id2 = ContentUris.parseId(trailerUri);
        assertTrue(_id2 != -1);

        ContentValues cv2 = new ContentValues();
        cv2.put(MoviesContract.ReviewEntry.COL_MOVIE_ID, 5964);
        cv2.put(MoviesContract.ReviewEntry.COL_MOVIE_REVIEW_AUTHOR, "Sir. Hesketh Bell");
        cv2.put(MoviesContract.ReviewEntry.COL_MOVIE_REVIEW, "it was the best movie of all time!");

        Uri reviewUri = mContext.getContentResolver().insert(MoviesContract.ReviewEntry.CONTENT_URI, cv2);
        assertTrue(reviewUri != null);

        long _id3 = ContentUris.parseId(reviewUri);
        assertTrue(_id3 != -1);

        int deletedMovies = mContext.getContentResolver().delete(
                MoviesContract.MoviesEntry.CONTENT_URI, null, null
        );
        int deletedTrailers = mContext.getContentResolver().delete(
                MoviesContract.TrailerEntry.CONTENT_URI, null, null
        );
        int deletedReviews = mContext.getContentResolver().delete(
                MoviesContract.ReviewEntry.CONTENT_URI, null, null
        );
        assertEquals(1, deletedMovies);
        assertEquals(1, deletedTrailers);
        assertEquals(1, deletedReviews);

        db.close();

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        mContext.deleteDatabase(MovieDBHelper.DATABASE_NAME);
    }
}
