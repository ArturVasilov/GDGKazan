package ru.gdg.kazan.gdgkazan.app;

import android.content.Context;
import android.support.annotation.NonNull;

import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import ru.gdg.kazan.gdgkazan.OkHttp3Downloader;

/**
 * @author Artur Vasilov
 */
public final class PicassoTools {

    private static final int CACHE_SIZE_BYTES = 50 * 1024 * 1024; //50 megabytes

    private PicassoTools() {
    }

    public static void initPicasso(@NonNull Context context) {
        Cache cache = new Cache(CacheDir.getCacheDir(), CACHE_SIZE_BYTES);
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .build();
        Downloader downloader = new OkHttp3Downloader(client);
        Picasso picasso = new Picasso.Builder(context).downloader(downloader).build();
        try {
            Picasso.setSingletonInstance(picasso);
        } catch (Exception ignored) {
            //Picasso instance was already set
        }
    }

}
