package com.radindustries.radman.catchamovie.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by radman on 7/16/16.
 */
public class MovieDBHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = MovieDBHelper.class.getSimpleName();
    public static final String DATABASE_NAME = "movie_database.db";
    public static final int DATABASE_VERSION = 3;
    Context context;

    public MovieDBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        Log.d(LOG_TAG, "Info: DBHelper created");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_MOVIE_TABLE =
                "CREATE TABLE " + MoviesContract.MoviesEntry.TABLE_NAME + " (" +
                MoviesContract.MoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MoviesContract.MoviesEntry.COL_MOVIE_ID + " INTEGER UNIQUE NOT NULL, " +
                MoviesContract.MoviesEntry.COL_MOVIE_TITLE + " TEXT NOT NULL, " +
                MoviesContract.MoviesEntry.COL_MOVIE_POSTER_URL + " TEXT NOT NULL, " +
                MoviesContract.MoviesEntry.COL_MOVIE_RELEASE_DATE + " TEXT NOT NULL, " +
                MoviesContract.MoviesEntry.COL_MOVIE_USER_RATING + " TEXT NOT NULL, " +
                MoviesContract.MoviesEntry.COL_MOVIE_PLOT_SYNOPSIS + " TEXT NOT NULL, " +
                MoviesContract.MoviesEntry.COL_MOVIE_SORT_TYPE_SETTING + " TEXT NOT NULL, " +
                MoviesContract.MoviesEntry.COL_IS_FAVOURITE + " INTEGER DEFAULT 0, " +
                        "UNIQUE (" + MoviesContract.MoviesEntry.COL_MOVIE_ID +
                        ") ON CONFLICT IGNORE" + ");";
        final String CREATE_TRAILER_TABLE =
                "CREATE TABLE " + MoviesContract.TrailerEntry.TABLE_NAME + " (" +
                MoviesContract.TrailerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MoviesContract.TrailerEntry.COL_MOVIE_ID + " INTEGER NOT NULL, " +
                MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_NAME + " TEXT, " +
                MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_URL + " TEXT UNIQUE, " +
                "FOREIGN KEY (" + MoviesContract.TrailerEntry.COL_MOVIE_ID + ") REFERENCES " +
                MoviesContract.MoviesEntry.TABLE_NAME + "(" +
                        MoviesContract.MoviesEntry.COL_MOVIE_ID + "), " +
                        "UNIQUE (" + MoviesContract.TrailerEntry.COL_MOVIE_TRAILER_URL +
                        ") ON CONFLICT IGNORE" + ");";
        final String CREATE_REVIEWS_TABLE =
                "CREATE TABLE " + MoviesContract.ReviewEntry.TABLE_NAME + " (" +
                MoviesContract.ReviewEntry._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                MoviesContract.ReviewEntry.COL_MOVIE_ID + " INTEGER NOT NULL, " +
                MoviesContract.ReviewEntry.COL_MOVIE_REVIEW_AUTHOR + " TEXT, " +
                MoviesContract.ReviewEntry.COL_MOVIE_REVIEW + " TEXT UNIQUE, " +
                "FOREIGN KEY (" + MoviesContract.ReviewEntry.COL_MOVIE_ID + ") REFERENCES " +
                MoviesContract.MoviesEntry.TABLE_NAME + "(" +
                        MoviesContract.MoviesEntry.COL_MOVIE_ID + "), " +
                        "UNIQUE (" + MoviesContract.ReviewEntry.COL_MOVIE_REVIEW +
                        ") ON CONFLICT IGNORE" + ");";
        try {
            db.execSQL(CREATE_MOVIE_TABLE);
            db.execSQL(CREATE_TRAILER_TABLE);
            db.execSQL(CREATE_REVIEWS_TABLE);
            Log.d(LOG_TAG, "Info: DB tables created");
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MoviesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.TrailerEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.ReviewEntry.TABLE_NAME);
        onCreate(db);
        Log.d(LOG_TAG, "Info: DB tables upgraded");

    }

}
