package com.radindustries.radman.catchamovie.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

public class MoviesProvider extends ContentProvider {

    private MovieDBHelper movieDBHelper;
    private static final UriMatcher matcher = buildUriMatcher();

    public static final int MOVIES = 100;
    public static final int MOVIES_WITH_SORT_TYPE = 200;
    //public static final int MOVIES_WITH_FAV = 300;
    public static final int MOVIES_WITH_SORT_TYPE_AND_ID = 400;
    //public static final int MOVIES_WITH_FAV_AND_ID = 500;
    public static final int TRAILERS = 600;
    //public static final int TRAILERS_WITH_ID = 700;
    public static final int REVIEWS = 800;
    //public static final int REVIEWS_WITH_ID = 900;

//    private static final SQLiteQueryBuilder mTrailerByMovieIdQueryBuilder;
//    private static final SQLiteQueryBuilder mReviewByMovieIdQueryBuilder;
//
//    static {
//        mTrailerByMovieIdQueryBuilder = new SQLiteQueryBuilder();
//        mReviewByMovieIdQueryBuilder = new SQLiteQueryBuilder();
//
//        mTrailerByMovieIdQueryBuilder.setTables(
//                MoviesContract.TrailerEntry.TABLE_NAME + " INNER JOIN " +
//                        MoviesContract.MoviesEntry.TABLE_NAME + " ON " +
//                        MoviesContract.TrailerEntry.TABLE_NAME + "." +
//                        MoviesContract.TrailerEntry.COL_MOVIE_ID + " = " +
//                        MoviesContract.MoviesEntry.TABLE_NAME + "." +
//                        MoviesContract.MoviesEntry.COL_MOVIE_ID
//        );
//        mReviewByMovieIdQueryBuilder.setTables(
//                MoviesContract.ReviewEntry.TABLE_NAME + " INNER JOIN " +
//                        MoviesContract.MoviesEntry.TABLE_NAME + " ON " +
//                        MoviesContract.ReviewEntry.TABLE_NAME + "." +
//                        MoviesContract.ReviewEntry.COL_MOVIE_ID + " = " +
//                        MoviesContract.MoviesEntry.TABLE_NAME + "." +
//                        MoviesContract.MoviesEntry.COL_MOVIE_ID
//        );
//    }

    public MoviesProvider() {
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = movieDBHelper.getWritableDatabase();
        final int match = matcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case MOVIES:
                rowsDeleted = db.delete(
                        MoviesContract.MoviesEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                ); break;
            case TRAILERS:
                rowsDeleted = db.delete(
                        MoviesContract.TrailerEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                ); break;
            case REVIEWS:
                rowsDeleted = db.delete(
                        MoviesContract.ReviewEntry.TABLE_NAME,
                        selection,
                        selectionArgs
                ); break;
            default: throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        if (rowsDeleted != 0) getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;

    }

    public static UriMatcher buildUriMatcher() {

        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String AUTHORITY = MoviesContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(AUTHORITY, MoviesContract.PATH_MOVIES, MOVIES);
        uriMatcher.addURI(AUTHORITY, MoviesContract.PATH_MOVIES + "/*", MOVIES_WITH_SORT_TYPE);
        //uriMatcher.addURI(AUTHORITY, MoviesContract.PATH_MOVIES + "/#", MOVIES_WITH_FAV);
        uriMatcher.addURI(AUTHORITY, MoviesContract.PATH_MOVIES + "/*/#", MOVIES_WITH_SORT_TYPE_AND_ID);
        //uriMatcher.addURI(AUTHORITY, MoviesContract.PATH_MOVIES + "/#/#", MOVIES_WITH_FAV_AND_ID);
        uriMatcher.addURI(AUTHORITY, MoviesContract.PATH_TRAILERS, TRAILERS);
        //uriMatcher.addURI(AUTHORITY, MoviesContract.PATH_TRAILERS + "/#", TRAILERS_WITH_ID);
        uriMatcher.addURI(AUTHORITY, MoviesContract.PATH_REVIEWS, REVIEWS);
        //uriMatcher.addURI(AUTHORITY, MoviesContract.PATH_REVIEWS + "/#", REVIEWS_WITH_ID);

        return uriMatcher;
    }

    @Override
    public String getType(@NonNull Uri uri) {

        final int match = matcher.match(uri);

        switch (match) {
            case MOVIES: return MoviesContract.MoviesEntry.CONTENT_DIR_TYPE;
            case MOVIES_WITH_SORT_TYPE: return MoviesContract.MoviesEntry.CONTENT_DIR_TYPE;
            //case MOVIES_WITH_FAV: return MoviesContract.MoviesEntry.CONTENT_DIR_TYPE;
            case MOVIES_WITH_SORT_TYPE_AND_ID: return MoviesContract.MoviesEntry.CONTENT_ITEM_TYPE;
            //case MOVIES_WITH_FAV_AND_ID: return MoviesContract.MoviesEntry.CONTENT_ITEM_TYPE;
            case TRAILERS: return MoviesContract.TrailerEntry.CONTENT_DIR_TYPE;
            //case TRAILERS_WITH_ID: return MoviesContract.TrailerEntry.CONTENT_DIR_TYPE;
            case REVIEWS: return MoviesContract.ReviewEntry.CONTENT_DIR_TYPE;
            //case REVIEWS_WITH_ID: return MoviesContract.ReviewEntry.CONTENT_DIR_TYPE;
            default: throw new UnsupportedOperationException("Uknown Uri: " + uri);
        }

    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        final SQLiteDatabase db = movieDBHelper.getWritableDatabase();
        final int match = matcher.match(uri);
        Uri resultUri;

        switch (match) {
            case MOVIES:
                long movieId = db.insert(MoviesContract.MoviesEntry.TABLE_NAME, null, values);
                if (movieId > 0) resultUri = MoviesContract.MoviesEntry.buildMovieUri(movieId);
                else throw new SQLException("Failed to insert row into " + uri);
                break;
            case TRAILERS:
                long trailerId = db.insert(MoviesContract.TrailerEntry.TABLE_NAME,
                        MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_URL, values);
                if (trailerId > 0) resultUri = MoviesContract.TrailerEntry.buildTrailerUri(trailerId);
                else throw new SQLException("Failed to insert row into " + uri);
                break;
            case REVIEWS:
                long reviewId = db.insert(MoviesContract.ReviewEntry.TABLE_NAME,
                        MoviesContract.ReviewEntry.COL_MOVIE_REVIEW, values);
                if (reviewId > 0) resultUri = MoviesContract.ReviewEntry.buildReviewUri(reviewId);
                else throw new SQLException("Failed to insert row into " + uri);
                break;
            default: throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return resultUri;

    }

    @Override
    public boolean onCreate() {
        movieDBHelper = new MovieDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = movieDBHelper.getReadableDatabase();
        Cursor returnedCursor;
        switch (matcher.match(uri)) {
            case MOVIES:
                returnedCursor = db.query(MoviesContract.MoviesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder); break;
            case MOVIES_WITH_SORT_TYPE:
                String sortType = MoviesContract.MoviesEntry.getSortTypeSettingFromUri(uri);
                projection = new String[]{ MoviesContract.MoviesEntry._ID,
                        MoviesContract.MoviesEntry.COL_MOVIE_ID,
                        MoviesContract.MoviesEntry.COL_MOVIE_POSTER_URL};
                selection = MoviesContract.MoviesEntry.COL_MOVIE_SORT_TYPE_SETTING + " = ? ";
                selectionArgs = new String[]{sortType};
                returnedCursor = db.query(MoviesContract.MoviesEntry.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, null); break;
//            case MOVIES_WITH_FAV:
//                String fav = Integer.toString(MoviesContract.MoviesEntry.getIsFavSettingFromUri(uri));
//                projection = new String[]{MoviesContract.MoviesEntry.COL_MOVIE_ID,
//                        MoviesContract.MoviesEntry.COL_MOVIE_POSTER_URL};
//                selection = MoviesContract.MoviesEntry.COL_IS_FAVOURITE + " = ? ";
//                selectionArgs = new String[]{fav};
//                returnedCursor = db.query(MoviesContract.MoviesEntry.TABLE_NAME,
//                        projection, selection, selectionArgs, null, null, null); break;
            case MOVIES_WITH_SORT_TYPE_AND_ID:
                String sortType1 = MoviesContract.MoviesEntry.getSortTypeSettingFromUri(uri);
                String id = Integer.toString(MoviesContract.MoviesEntry.getMovieIdFromUri(uri));
                projection = new String[]{
                        MoviesContract.MoviesEntry.COL_MOVIE_TITLE,
                        MoviesContract.MoviesEntry.COL_MOVIE_POSTER_URL,
                        MoviesContract.MoviesEntry.COL_MOVIE_RELEASE_DATE,
                        MoviesContract.MoviesEntry.COL_MOVIE_USER_RATING,
                        MoviesContract.MoviesEntry.COL_MOVIE_PLOT_SYNOPSIS
                };
                selection = MoviesContract.MoviesEntry.COL_MOVIE_ID + " = ? AND " +
                        MoviesContract.MoviesEntry.COL_MOVIE_SORT_TYPE_SETTING + " = ? ";
                selectionArgs = new String[]{id, sortType1};
                returnedCursor = db.query(MoviesContract.MoviesEntry.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, null); break;
//            case MOVIES_WITH_FAV_AND_ID:
//                String fav1 = Integer.toString(MoviesContract.MoviesEntry.getIsFavSettingFromUri(uri));
//                String id1 = Integer.toString(MoviesContract.MoviesEntry.getMovieIdFromUri(uri));
//                projection = new String[]{
//                        MoviesContract.MoviesEntry.COL_MOVIE_TITLE,
//                        MoviesContract.MoviesEntry.COL_MOVIE_POSTER_URL,
//                        MoviesContract.MoviesEntry.COL_MOVIE_RELEASE_DATE,
//                        MoviesContract.MoviesEntry.COL_MOVIE_USER_RATING,
//                        MoviesContract.MoviesEntry.COL_MOVIE_PLOT_SYNOPSIS
//                };
//                selection = MoviesContract.MoviesEntry.COL_MOVIE_ID + " = ? AND " +
//                        MoviesContract.MoviesEntry.COL_IS_FAVOURITE + " = ? ";
//                selectionArgs = new String[]{id1, fav1};
//                returnedCursor = db.query(MoviesContract.MoviesEntry.TABLE_NAME,
//                        projection, selection, selectionArgs, null, null, null); break;
            case TRAILERS:
                returnedCursor = db.query(MoviesContract.TrailerEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder); break;
//            case TRAILERS_WITH_ID:
//                String id2 = Integer.toString(MoviesContract.MoviesEntry.getMovieIdFromUri(uri));
//                projection = new String[]{
//                        MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_NAME,
//                        MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_URL
//                };
//                selection = MoviesContract.TrailerEntry.COL_MOVIE_ID + " = ? ";
//                selectionArgs = new String[]{id2};
//                returnedCursor = db.query(MoviesContract.TrailerEntry.TABLE_NAME,
//                        projection, selection, selectionArgs, null, null, null); break;
            case REVIEWS:
                returnedCursor = db.query(MoviesContract.ReviewEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder); break;
//            case REVIEWS_WITH_ID:
//                String id3 = Integer.toString(MoviesContract.MoviesEntry.getMovieIdFromUri(uri));
//                projection = new String[]{
//                        MoviesContract.ReviewEntry.COL_MOVIE_REVIEW_AUTHOR,
//                        MoviesContract.ReviewEntry.COL_MOVIE_REVIEW
//                };
//                selection = MoviesContract.ReviewEntry.COL_MOVIE_ID + " = ? ";
//                selectionArgs = new String[]{id3};
//                returnedCursor = db.query(MoviesContract.ReviewEntry.TABLE_NAME,
//                        projection, selection, selectionArgs, null, null, null); break;
            default: throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        returnedCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnedCursor;

    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        final SQLiteDatabase db = movieDBHelper.getWritableDatabase();
        final int match = matcher.match(uri);
        int rowsUpdated;
        switch (match) {
            case MOVIES_WITH_SORT_TYPE_AND_ID:
                //values.put(MoviesContract.MoviesEntry.COL_IS_FAVOURITE, 1);
                String id = Integer.toString(MoviesContract.MoviesEntry.getMovieIdFromUri(uri));
                selection = MoviesContract.MoviesEntry.COL_MOVIE_ID + " = ? ";
                selectionArgs = new String[]{id};
                rowsUpdated = db.update(MoviesContract.MoviesEntry.TABLE_NAME,
                        values, selection, selectionArgs); break;
//            case MOVIES_WITH_FAV_AND_ID:
//                values.put(MoviesContract.MoviesEntry.COL_IS_FAVOURITE, 0);
//                String id1 = Integer.toString(MoviesContract.MoviesEntry.getMovieIdFromUri(uri));
//                selection = MoviesContract.MoviesEntry.COL_MOVIE_ID + " = ? ";
//                selectionArgs = new String[]{id1};
//                rowsUpdated = db.update(MoviesContract.MoviesEntry.TABLE_NAME,
//                        values, selection, selectionArgs); break;
            default: throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        final SQLiteDatabase db = movieDBHelper.getWritableDatabase();
        final int match = matcher.match(uri);
        int returnCount;

        switch (match) {
            case MOVIES:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long movie_id = db.insert(MoviesContract.MoviesEntry.TABLE_NAME, null, value);
                        if (movie_id != -1) returnCount++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                } break;
            case TRAILERS:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long trailer_id = db.insert(MoviesContract.TrailerEntry.TABLE_NAME,
                                MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_URL ,value);
                        if (trailer_id != -1) returnCount++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                } break;
            case REVIEWS:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long review_id = db.insert(MoviesContract.ReviewEntry.TABLE_NAME,
                                MoviesContract.ReviewEntry.COL_MOVIE_REVIEW, value);
                        if (review_id != -1) returnCount++;
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                } break;
            default: return super.bulkInsert(uri, values);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnCount;

    }

}
