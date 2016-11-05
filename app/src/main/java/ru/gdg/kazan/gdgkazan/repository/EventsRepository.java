package ru.gdg.kazan.gdgkazan.repository;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.rxloader.RxUtils;
import ru.arturvasilov.sqlite.core.SQLite;
import ru.arturvasilov.sqlite.core.Where;
import ru.arturvasilov.sqlite.rx.RxSQLite;
import ru.gdg.kazan.gdgkazan.api.OkHttpUtils;
import ru.gdg.kazan.gdgkazan.models.Config;
import ru.gdg.kazan.gdgkazan.models.Event;
import ru.gdg.kazan.gdgkazan.models.GsonHolder;
import ru.gdg.kazan.gdgkazan.models.database.ConfigTable;
import ru.gdg.kazan.gdgkazan.models.database.EventsTable;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class EventsRepository {

    public EventsRepository() {
    }

    @NonNull
    public Observable<List<Event>> fetchEvents() {
        Where eventsUrlWhere = Where.create().equalTo(ConfigTable.KEY, Config.EVENTS_URL);
        return RxSQLite.get().querySingle(ConfigTable.TABLE, eventsUrlWhere)
                .map(Config::getValue)
                .flatMap(url -> {
                    try {
                        return Observable.just(OkHttpUtils.downloadJson(url));
                    } catch (IOException e) {
                        return Observable.error(e);
                    }
                })
                .map(jsonEvents -> {
                    if (TextUtils.isEmpty(jsonEvents)) {
                        return new ArrayList<Event>();
                    }
                    Type eventsListType = new TypeToken<List<Event>>() {
                    }.getType();
                    //noinspection unchecked
                    return (List<Event>) GsonHolder.getGson().fromJson(jsonEvents, eventsListType);
                })
                .doOnNext(events -> {
                    SQLite.get().delete(EventsTable.TABLE);
                    SQLite.get().insert(EventsTable.TABLE, events);
                })
                .onErrorResumeNext(throwable -> RxSQLite.get().query(EventsTable.TABLE))
                .compose(RxUtils.async());
    }
}
