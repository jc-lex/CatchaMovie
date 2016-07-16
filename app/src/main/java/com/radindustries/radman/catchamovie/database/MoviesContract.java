package com.radindustries.radman.catchamovie.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by radman on 7/16/16.
 */
public class MoviesContract {

    public static final String CONTENT_AUTHORITY = "com.radindustries.radman.catchamovie.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies";
    public static final String PATH_TRAILERS = "trailers";
    public static final String PATH_REVIEWS = "reviews";

    public static final class MoviesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        //table name and columns
        public static final String TABLE_NAME = "movies";
        public static final String COL_MOVIE_ID = "movie_id";
        public static final String COL_MOVIE_TITLE = "movie_title";
        public static final String COL_MOVIE_POSTER_URL = "movie_poster_url";
        public static final String COL_MOVIE_RELEASE_DATE = "movie_release_date";
        public static final String COL_MOVIE_USER_RATING = "movie_user_rating";
        public static final String COL_MOVIE_PLOT_SYNOPSIS = "movie_plot_synopsis";
        public static final String COL_MOVIE_SORT_TYPE_SETTING = "movie_sort_type_setting";
        public static final String COL_IS_FAVOURITE = "is_favourite";

    }

    public static final class TrailerEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILERS).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILERS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILERS;

        //table name and columns
        public static final String TABLE_NAME = "trailers";
        public static final String COL_MOVIE_ID = "movie_id";
        public static final String COL_MOVIE_TRAILER_NAME = "movie_trailer_name";
        public static final String COL_MOVIE_TRAILER_URL = "movie_trailer_url";

    }

    public static final class ReviewEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEWS).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEWS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEWS;

        //table name and columns
        public static final String TABLE_NAME = "reviews";
        public static final String COL_MOVIE_ID = "movie_id";
        public static final String COL_MOVIE_REVIEW_AUTHOR = "movie_review_author";
        public static final String COL_MOVIE_REVIEW = "movie_review";

    }

}
