package com.radindustries.radman.catchamovie;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by radman on 7/6/16.
 */
public class GetMoviesTask extends AsyncTask<String, Void, Integer> {

    public static final String LOG_TAG = GetMoviesTask.class.getSimpleName();

    @Override
    protected Integer doInBackground(String... params) {
        int result = 0;

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

        String apiKey = "b27bf3ea7724c708e10e78138ef74f26"; //my movie api key

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
                return null;
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
                return null;
            }
            movieJsonStr = buffer.toString();

            Log.v(LOG_TAG, "JSON String: " + movieJsonStr);

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

        return 0;
    }
}
