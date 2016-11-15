package ru.gdg.kazan.gdgkazan.repository;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.arturvasilov.rxloader.RxUtils;
import ru.arturvasilov.sqlite.core.SQLite;
import ru.arturvasilov.sqlite.core.Where;
import ru.arturvasilov.sqlite.rx.RxSQLite;
import ru.arturvasilov.sqlite.utils.SQLiteUtils;
import ru.gdg.kazan.gdgkazan.app.OkHttpUtils;
import ru.gdg.kazan.gdgkazan.models.Event;
import ru.gdg.kazan.gdgkazan.models.EventSubscription;
import ru.gdg.kazan.gdgkazan.models.GsonHolder;
import ru.gdg.kazan.gdgkazan.models.database.EventSubscriptionsTable;
import ru.gdg.kazan.gdgkazan.models.database.EventsTable;
import ru.gdg.kazan.gdgkazan.repository.preferences.KeyValueStorage;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class EventsRepository {

    @NonNull
    public Observable<List<Event>> fetchEvents() {
        String eventsUrl = RepositoryProvider.provideKeyValueStorage().getString(KeyValueStorage.EVENTS_URL);
        return Observable.just(eventsUrl)
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
                .compose(RxUtils.async());
    }

    @NonNull
    public Observable<List<Event>> fetchLocalEvents() {
        return RxSQLite.get().query(EventsTable.TABLE)
                .map(events -> {
                    Collections.sort(events);
                    return events;
                })
                .compose(RxUtils.async());
    }

    public boolean hasLocalEvents() {
        return !SQLiteUtils.isTableEmpty(EventsTable.TABLE);
    }

    @NonNull
    public Observable<EventSubscription> subscriptionForEvent(@NonNull Event event) {
        Where eventWhere = Where.create().equalTo(EventSubscriptionsTable.EVENT_ID, event.getId());
        return RxSQLite.get().querySingle(EventSubscriptionsTable.TABLE, eventWhere)
                .switchIfEmpty(Observable.just(new EventSubscription(event.getId(), true)))
                .compose(RxUtils.async());
    }
}
