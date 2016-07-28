package com.radindustries.radman.catchamovie.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by radman on 7/28/16.
 */
public class MovieAuthenticatorService extends Service {

    private MovieAuthenticator movieAuthenticator;

    @Override
    public void onCreate() {
        movieAuthenticator = new MovieAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return movieAuthenticator.getIBinder();
    }

}
