package ru.gdg.kazan.gdgkazan;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author Artur Vasilov
 */
public class GDGKazanApp extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context sAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = this;
    }

    @NonNull
    public static Context getAppContext() {
        return sAppContext;
    }
}
