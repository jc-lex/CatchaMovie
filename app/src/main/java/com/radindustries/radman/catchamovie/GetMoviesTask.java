package com.radindustries.radman.catchamovie;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by radman on 7/6/16.
 */
public class GetMoviesTask extends AsyncTask<String, Void, Integer> {

    private static final String LOG_TAG = GetMoviesTask.class.getSimpleName();
    private Context context;
    private ArrayList<GridItem> mGridData;
    private MoviesAdapter moviesAdapter;

    public GetMoviesTask(Context context, MoviesAdapter moviesAdapter, ArrayList<GridItem> gridItems) {
        this.context = context;
        this.moviesAdapter = moviesAdapter;
        this.mGridData = gridItems;
    }

    @Override
    protected Integer doInBackground(String... params) {

        if (params.length == 0) {
            return null;
        }
        String movieQuery = params[0];

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String movieJsonStr = null;

        String apiKey = "b27bf3ea7724c708e10e78138ef74f26"; //my movieDB api key

        try{

            final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie";
            final String API_QUERY_PARAM = "api_key";

            Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendPath(movieQuery)
                    .appendQueryParameter(API_QUERY_PARAM, apiKey).build();

            Log.v(LOG_TAG, "Built URI: " + builtUri.toString());

            URL url = new URL(builtUri.toString());

            // Create the request to TMDB, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return 0;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return 0;
            }
            movieJsonStr = buffer.toString();

            Log.v(LOG_TAG, "JSON String: " + movieJsonStr);
            inputStream.close();

        } catch(IOException e) {
            Log.e(LOG_TAG, "IOException error: ", e);
            return 0;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        //parse the data out
        try{
            getMoviePosterData(movieJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return 1;
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (result == 1) {
            moviesAdapter.setGridData(mGridData);
        } else {
            Toast.makeText(context, "Failed to get data", Toast.LENGTH_LONG).show();
        }

    }

    private void getMoviePosterData(String movieJsonStr) throws JSONException {

        final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185";
        final String BASE_POSTER_URI_SMALL = "http://image.tmdb.org/t/p/w92";

        final String MDB_ID = "id";
        final String MDB_OVERVIEW = "overview";
        final String MDB_POSTER_PATH = "poster_path";
        final String MDB_TITLE = "title";
        final String MDB_VOTER_AVERAGE = "vote_average";
        final String MDB_RELEASE_DATE = "release_date";
        final String MDB_RESULTS = "results";


        try{
            JSONObject movielist = new JSONObject(movieJsonStr);
            JSONArray movies = movielist.getJSONArray(MDB_RESULTS);
            GridItem item;

            //initialise variables
            int id = 0;
            String overviewStr = null;
            String posterRawPathStr;
            String posterProperPathStr;
            String titleStr = null;
            float voteAvgStr = 0;
            String releaseDateStr;
            JSONObject movie;

            for (int i = 0; i < movies.length(); i++) {

                movie = movies.getJSONObject(i);

                //extract data
                id = movie.getInt(MDB_ID);
                overviewStr = movie.getString(MDB_OVERVIEW);
                titleStr = movie.getString(MDB_TITLE);
                posterRawPathStr = movie.getString(MDB_POSTER_PATH);
                voteAvgStr = (float) movie.getDouble(MDB_VOTER_AVERAGE);
                releaseDateStr = formatReleaseDate(movie.getString(MDB_RELEASE_DATE));
                Log.v(LOG_TAG, "Release date: " + releaseDateStr);

                //deal with movie posters
                item = new GridItem();

                posterProperPathStr = correctPosterPath(posterRawPathStr);

                Uri posterUri = Uri.parse(BASE_POSTER_URL)
                        .buildUpon().appendPath(posterProperPathStr).build();
                Log.v(LOG_TAG, "Movie poster Uri: " + posterUri.toString());

                String posterPath = posterUri.toString();
                item.setImage(posterPath);
                mGridData.add(item);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

    }

    private String formatReleaseDate(String releaseDate) {
        String[] tokens = releaseDate.split("-");
        String month = null, day;
        switch (tokens[1]) {
            case "01": month = "Jan"; break;
            case "02": month = "Feb"; break;
            case "03": month = "Mar"; break;
            case "04": month = "Apr"; break;
            case "05": month = "May"; break;
            case "06": month = "Jun"; break;
            case "07": month = "Jul"; break;
            case "08": month = "Aug"; break;
            case "09": month = "Sep"; break;
            case "10": month = "Oct"; break;
            case "11": month = "Nov"; break;
            case "12": month = "Dec"; break;
        }
        int dayInt = Integer.parseInt(tokens[2]);
        day = Integer.toString(dayInt);
        return day + " " + month + " " + tokens[0];
    }

    private String correctPosterPath(String posterRawPath) {
        StringBuilder correctStr = new StringBuilder(posterRawPath);
        correctStr.deleteCharAt(0);
        return correctStr.toString();
    }

}
