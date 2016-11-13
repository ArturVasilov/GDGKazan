package ru.gdg.kazan.gdgkazan;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import ru.arturvasilov.sqlite.core.SQLite;
import ru.gdg.kazan.gdgkazan.app.CacheDir;
import ru.gdg.kazan.gdgkazan.app.PicassoTools;
import ru.gdg.kazan.gdgkazan.models.Config;
import ru.gdg.kazan.gdgkazan.models.database.ConfigTable;

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

        //TODO : remove this stub
        SQLite.initialize(this);
        Config config = new Config();
        config.setKey(Config.EVENTS_URL);
        config.setValue("https://drive.google.com/uc?export=download&id=0B0Z-lYDZWlawYVBrM0xXTmI2SG8");
        SQLite.get().insert(ConfigTable.TABLE, config);

        CacheDir.init(this);
        PicassoTools.initPicasso(this);
    }

    @NonNull
    public static Context getAppContext() {
        return sAppContext;
    }
}
