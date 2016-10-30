package ru.gdg.kazan.gdgkazan;

import android.app.Application;

import com.facebook.stetho.Stetho;

import ru.gdg.kazan.gdgkazan.api.ApiFactory;

/**
 * @author Valiev Timur.
 */
public class GDGKazanApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApiFactory.provideClient(this);
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }
}
