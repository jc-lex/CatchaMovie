package com.radindustries.radman.catchamovie.dbtests;

import android.test.AndroidTestCase;

import com.radindustries.radman.catchamovie.database.MovieDBHelper;
import com.radindustries.radman.catchamovie.database.MoviesContract;

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

    public void testGetType(){
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

        //content://com.radindustries.radman.catchamovie.database/movies/1
        int fav = 1;
        type = mContext.getContentResolver().getType(MoviesContract.MoviesEntry
                .buildMovieUriWithIsFavSetting(fav));
        assertEquals("Error: The MovieEntry CONTENT_URI with isFav setting " +
                "should return CONTENT_DIR_TYPE", MoviesContract.MoviesEntry.CONTENT_DIR_TYPE, type);

        //content://com.radindustries.radman.catchamovie.database/movies/1/23
        type = mContext.getContentResolver().getType(MoviesContract.MoviesEntry
                .buildMovieUriWithIsFavSettingAndId(fav, testId));
        assertEquals("Error: The MovieEntry CONTENT_URI with isFav setting and id " +
                "should return CONTENT_ITEM_TYPE", MoviesContract.MoviesEntry.CONTENT_ITEM_TYPE, type);

        //content://com.radindustries.radman.catchamovie.database/trailers
        type = mContext.getContentResolver().getType(MoviesContract.TrailerEntry.CONTENT_URI);
        assertEquals("Error: The TrailerEntry CONTENT_URI should return CONTENT_DIR_TYPE",
                MoviesContract.TrailerEntry.CONTENT_DIR_TYPE, type);

        //content://com.radindustries.radman.catchamovie.database/trailers/23
        type = mContext.getContentResolver().getType(MoviesContract.TrailerEntry
                .buildTrailerUriWithId(testId));
        assertEquals("Error: The TrailerEntry CONTENT_URI with id " +
                "should return CONTENT_DIR_TYPE", MoviesContract.TrailerEntry.CONTENT_DIR_TYPE, type);

        //content://com.radindustries.radman.catchamovie.database/reviews
        type = mContext.getContentResolver().getType(MoviesContract.ReviewEntry.CONTENT_URI);
        assertEquals("Error: the ReviewEntry CONTENT_URI should return CONTENT_DIR_TYPE",
                MoviesContract.ReviewEntry.CONTENT_DIR_TYPE, type);

        //content://com.radindustries.radman.catchamovie.database/reviews/23
        type = mContext.getContentResolver().getType(MoviesContract.ReviewEntry.buidReviewUriWithId(testId));
        assertEquals("Errors: the ReviewEntry CONTENT_URI with id " +
                "should return CONTENT_DIR_TYPE", MoviesContract.ReviewEntry.CONTENT_DIR_TYPE, type);

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        mContext.deleteDatabase(MovieDBHelper.DATABASE_NAME);
    }
}
