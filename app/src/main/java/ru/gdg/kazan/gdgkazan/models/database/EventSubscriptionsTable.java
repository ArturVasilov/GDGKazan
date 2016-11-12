package ru.gdg.kazan.gdgkazan.models.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import org.sqlite.database.sqlite.SQLiteDatabase;

import ru.arturvasilov.sqlite.core.BaseTable;
import ru.arturvasilov.sqlite.core.Table;
import ru.arturvasilov.sqlite.utils.TableBuilder;
import ru.gdg.kazan.gdgkazan.models.Event;
import ru.gdg.kazan.gdgkazan.models.EventSubscription;

/**
 * @author Artur Vasilov
 */
public class EventSubscriptionsTable extends BaseTable<EventSubscription> {

    public static final Table<EventSubscription> TABLE = new EventSubscriptionsTable();

    public static final String EVENT_ID = "event_id";
    public static final String IS_SUBSCRIBED = "is_subscribed";

    @Override
    public void onCreate(@NonNull SQLiteDatabase database) {
        TableBuilder.create(this)
                .intColumn(EVENT_ID)
                .intColumn(IS_SUBSCRIBED)
                .primaryKey(EVENT_ID)
                .execute(database);
    }

    @NonNull
    @Override
    public ContentValues toValues(@NonNull EventSubscription eventSubscription) {
        ContentValues values = new ContentValues();
        values.put(EVENT_ID, eventSubscription.getEventId());
        values.put(IS_SUBSCRIBED, eventSubscription.isSubscribed() ? 1 : 0);
        return values;
    }

    @NonNull
    @Override
    public EventSubscription fromCursor(@NonNull Cursor cursor) {
        int eventId = cursor.getInt(cursor.getColumnIndex(EVENT_ID));
        boolean isSubscribed = cursor.getInt(cursor.getColumnIndex(IS_SUBSCRIBED)) > 0;
        return new EventSubscription(eventId, isSubscribed);
    }
}
