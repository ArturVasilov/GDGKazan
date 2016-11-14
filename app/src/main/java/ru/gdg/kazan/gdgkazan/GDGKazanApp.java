package ru.gdg.kazan.gdgkazan;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.orhanobut.hawk.Hawk;

import ru.arturvasilov.sqlite.core.SQLite;
import ru.gdg.kazan.gdgkazan.app.CacheDir;
import ru.gdg.kazan.gdgkazan.app.PicassoTools;

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

        SQLite.initialize(this);
        CacheDir.init(this);
        PicassoTools.initPicasso(this);

        Hawk.init(this).build();
    }

    @NonNull
    public static Context getAppContext() {
        return sAppContext;
    }
}
