package ru.gdg.kazan.gdgkazan.repository.events;

import android.support.annotation.NonNull;

import java.util.List;

import ru.arturvasilov.rxloader.RxUtils;
import rx.Observable;

/**
 * @author Valiev Timur.
 */
public class EventsRepository {

    @NonNull
    private final EventsService mApi;

    public EventsRepository(@NonNull EventsService service) {
        mApi = service;
    }

    @NonNull
    public Observable<List> fetchEvents() {
        return mApi.fetchEvents()
                .compose(RxUtils.async());
    }
}
