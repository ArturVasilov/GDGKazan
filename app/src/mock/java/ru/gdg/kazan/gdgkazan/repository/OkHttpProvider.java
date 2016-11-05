package ru.gdg.kazan.gdgkazan.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import ru.gdg.kazan.gdgkazan.models.EventsFactory;
import rx.functions.Func2;

/**
 * @author Artur Vasilov
 */
public class OkHttpProvider {

    private static final Random RANDOM = new SecureRandom();

    private static final List<String> WITH_MOCK_ENABLED = new ArrayList<>(20);

    private static final Map<String, Func2<Context, Request, Response>> RESPONSES = new ConcurrentHashMap<>();

    static {
        RESPONSES.put("/api/v1/events", EventsFactory.response());

        ArrayList<String> enableMethods = new ArrayList<>();
        enableMethods.add("/api/v1/events");
        refreshEnabled(enableMethods);
    }

    private OkHttpProvider() {
    }

    public static boolean refreshEnabled(@NonNull List<String> enabledMethods) {
        WITH_MOCK_ENABLED.clear();
        return WITH_MOCK_ENABLED.addAll(enabledMethods);
    }

    @NonNull
    public static OkHttpClient provideClient(@NonNull Context context) {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(chain -> {
                    final Request request = chain.request();
                    try {
                        final String encodedPath = request.url().encodedPath();

                        final Func2<Context, Request, Response> func = RESPONSES.get(encodedPath);
                        if (WITH_MOCK_ENABLED.contains(encodedPath) && func != null) {
                            TimeUnit.MILLISECONDS.sleep(500 + RANDOM.nextInt(1000));
                            return func.call(context, request);
                        }
                        return chain.proceed(chain.request()); // proxy to real method
                    } catch (InterruptedException e) {
                        return OkHttpResponse.error(request, 500, e.getMessage());
                    }
                })
                .build();
    }

}
