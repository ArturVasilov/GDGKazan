package ru.gdg.kazan.gdgkazan.repository;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.arturvasilov.rxloader.RxUtils;
import ru.gdg.kazan.gdgkazan.api.OkHttpUtils;
import ru.gdg.kazan.gdgkazan.models.Event;
import ru.gdg.kazan.gdgkazan.models.GsonHolder;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author Artur Vasilov
 */
public class EventsRepository {

    public EventsRepository() {
    }

    @NonNull
    public Observable<List<Event>> fetchEvents() {
        return Observable.fromCallable(() -> {
            String url = "https://drive.google.com/uc?export=download&id=0B0Z-lYDZWlawYVBrM0xXTmI2SG8";
            return OkHttpUtils.downloadJson(url);
        })
                .map(jsonEvents -> {
                    if (TextUtils.isEmpty(jsonEvents)) {
                        return new ArrayList<Event>();
                    }
                    Type eventsListType = new TypeToken<List<Event>>(){}.getType();
                    //noinspection unchecked
                    return (List<Event>) GsonHolder.getGson().fromJson(jsonEvents, eventsListType);
                })
                .compose(RxUtils.async());
    }
}
