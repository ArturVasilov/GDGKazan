package ru.gdg.kazan.gdgkazan.app;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.File;

import ru.gdg.kazan.gdgkazan.BuildConfig;

/**
 * @author Artur Vasilov
 */
public final class CacheDir {

    private static volatile File sCacheDir;

    private CacheDir() {
    }

    public static void init(@NonNull Context context) {
        if (sCacheDir == null) {
            synchronized (CacheDir.class) {
                if (sCacheDir == null) {
                    if (sCacheDir == null) {
                        try {
                            sCacheDir = context.getCacheDir();
                        } catch (Exception e) {
                            //Do nothing. Leave sCacheDir = null
                        }
                    }

                    if (sCacheDir == null) {
                        try {
                            sCacheDir = new File(context.getFilesDir().getPath() + BuildConfig.APPLICATION_ID + "/cache/");
                        } catch (Exception e) {
                            //Do nothing. Leave sCacheDir = null
                        }
                    }
                }
            }
        }
    }

    @NonNull
    public static File getCacheDir() {
        File cacheDir = sCacheDir;
        if (cacheDir == null) {
            synchronized (CacheDir.class) {
                cacheDir = sCacheDir;
                if (cacheDir == null) {
                    throw new NullPointerException("Cache dir is null, try to call init first");
                }
            }
        }
        return cacheDir;
    }

}
