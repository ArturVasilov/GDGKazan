package ru.gdg.kazan.gdgkazan.repository;

import android.support.annotation.NonNull;

import okhttp3.OkHttpClient;

/**
 * @author Artur Vasilov
 */
public final class OkHttpProvider {

    private OkHttpProvider() {
    }

    private static volatile OkHttpClient sClient;

    @NonNull
    public static OkHttpClient provideClient() {
        OkHttpClient client = sClient;
        if (client == null) {
            synchronized (OkHttpProvider.class) {
                client = sClient;
                if (client == null) {
                    client = sClient = buildClient();
                }
            }
        }
        return client;
    }

    @NonNull
    private static OkHttpClient buildClient() {
        return new OkHttpClient.Builder().build();
    }

}
