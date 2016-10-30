package ru.gdg.kazan.gdgkazan.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Request;
import okhttp3.Response;
import ru.gdg.kazan.gdgkazan.repository.OkHttpResponse;
import rx.functions.Func2;

/**
 * @author Valiev Timur.
 */
public final class EventsFactory {

    private EventsFactory() {
    }

    @NonNull
    public static Func2<Context, Request, Response> response() {
        return (context, request) -> {
            try {
                final InputStream stream = context.getAssets().open("events.json");
                try {
                    return OkHttpResponse.success(request, stream);
                } finally {
                    stream.close();
                }
            } catch (IOException e) {
                Log.e(EventsFactory.class.getSimpleName(), e.getMessage(), e);
                return OkHttpResponse.error(request, 500, e.getMessage());
            }
        };
    }
}
