package ru.gdg.kazan.gdgkazan.repository;

import android.support.annotation.NonNull;

import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import ru.gdg.kazan.gdgkazan.GDGKazanApp;

/**
 * @author Artur Vasilov
 */
public final class OkHttpProvider {

    private static final Random RANDOM = new SecureRandom();

    private OkHttpProvider() {
    }

    @NonNull
    public static OkHttpClient provideClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    final Request request = chain.request();
                    try {
                        TimeUnit.MILLISECONDS.sleep(500 + RANDOM.nextInt(1000));
                        InputStream inputStream = GDGKazanApp.getAppContext().getAssets().open("events.json");
                        return OkHttpResponse.success(request, inputStream);
                    } catch (InterruptedException e) {
                        return OkHttpResponse.error(request, 500, e.getMessage());
                    }
                })
                .build();
    }

}
