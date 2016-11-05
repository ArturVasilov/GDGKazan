package ru.gdg.kazan.gdgkazan.api;

import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.Request;
import okhttp3.Response;
import ru.gdg.kazan.gdgkazan.repository.OkHttpProvider;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public final class OkHttpUtils {

    private OkHttpUtils() {
    }

    @NonNull
    public static String downloadJson(@NonNull String fileUrl) throws IOException {
        Request request = new Request.Builder()
                .url(fileUrl)
                .build();
        Response response = OkHttpProvider.provideClient().newCall(request).execute();
        InputStream inputStream = response.body().byteStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

}
