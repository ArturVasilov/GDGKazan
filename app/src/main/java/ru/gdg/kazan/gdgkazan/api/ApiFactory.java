package ru.gdg.kazan.gdgkazan.api;

import android.content.Context;
import android.support.annotation.NonNull;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ru.gdg.kazan.gdgkazan.BuildConfig;
import ru.gdg.kazan.gdgkazan.repository.OkHttpProvider;

/**
 * @author Valiev Timur.
 */
public class ApiFactory {

    private static Retrofit sRetrofit;

    private static OkHttpClient sHttpClient;

    private ApiFactory() {
    }

    @NonNull
    public static Retrofit getRetrofitInstance() {
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_BASE_URL)
                    .client(sHttpClient)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return sRetrofit;
    }

    public static void provideClient(@NonNull Context context) {
        sHttpClient = OkHttpProvider.provideClient(context);
    }
}
