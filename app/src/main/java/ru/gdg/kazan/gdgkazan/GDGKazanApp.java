package ru.gdg.kazan.gdgkazan;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

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

        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        remoteConfig.setConfigSettings(configSettings);
        remoteConfig.setDefaults(R.xml.remofe_config_defaults);
    }

    @NonNull
    public static Context getAppContext() {
        return sAppContext;
    }
}
