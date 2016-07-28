package com.radindustries.radman.catchamovie.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by radman on 7/28/16.
 */
public class MovieSyncService extends Service {

    private static final Object syncAdapterLock = new Object();
    private static MovieSyncAdapter adapter = null;

    @Override
    public void onCreate() {
        synchronized (syncAdapterLock) {
            if (adapter == null) adapter = new MovieSyncAdapter(getApplicationContext(), true);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return adapter.getSyncAdapterBinder();
    }

}
