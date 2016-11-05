package ru.gdg.kazan.gdgkazan.repository;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.rxloader.RxUtils;
import ru.gdg.kazan.gdgkazan.models.Event;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class EventsRepository {

    public EventsRepository() {
    }

    @NonNull
    public Observable<List<Event>> fetchEvents() {
       return Observable.just(new ArrayList<Event>())
               .compose(RxUtils.async());
    }
}
