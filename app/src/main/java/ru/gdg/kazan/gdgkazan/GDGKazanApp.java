package ru.gdg.kazan.gdgkazan;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;

/**
 * @author Artur Vasilov
 */
public class GDGKazanApp extends Application {

    private static final int MEGABYTES_IN_BYTES = 1024 * 1024;
    private static final int DEFAULT_MEGABYTES_COUNT = 50;

    @SuppressLint("StaticFieldLeak")
    private static Context sAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = this;

        int megabytes = DEFAULT_MEGABYTES_COUNT * MEGABYTES_IN_BYTES; //50 megabytes
        Downloader downloader = new OkHttp3Downloader(this, megabytes);
        Picasso picasso = new Picasso.Builder(this).downloader(downloader).build();
        try {
            Picasso.setSingletonInstance(picasso);
        } catch (Exception ignored) {
            //Picasso instance was already set
        }
    }

    @NonNull
    public static Context getAppContext() {
        return sAppContext;
    }
}
