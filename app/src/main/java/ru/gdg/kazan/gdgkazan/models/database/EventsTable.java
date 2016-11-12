package ru.gdg.kazan.gdgkazan.models.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;

import org.sqlite.database.sqlite.SQLiteDatabase;

import java.util.List;

import ru.arturvasilov.sqlite.core.BaseTable;
import ru.arturvasilov.sqlite.core.Table;
import ru.arturvasilov.sqlite.utils.TableBuilder;
import ru.gdg.kazan.gdgkazan.models.Event;
import ru.gdg.kazan.gdgkazan.models.GsonHolder;
import ru.gdg.kazan.gdgkazan.models.Link;
import ru.gdg.kazan.gdgkazan.models.Photo;

/**
 * @author Artur Vasilov
 */
public class EventsTable extends BaseTable<Event> {

    public static final Table<Event> TABLE = new EventsTable();

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PREVIEW_IMAGE = "preview_image";
    public static final String PREVIEW_DESCRIPTION = "preview_description";
    public static final String DESCRIPTION = "description";
    public static final String IS_SUBSCRIPTION_POSSIBLE = "is_subscription_possible";
    public static final String IS_PINNED = "is_pinned";
    public static final String PHOTOS = "photos";
    public static final String LINKS = "links";

    @Override
    public void onCreate(@NonNull SQLiteDatabase database) {
        TableBuilder.create(this)
                .intColumn(ID)
                .textColumn(NAME)
                .textColumn(PREVIEW_IMAGE)
                .textColumn(PREVIEW_DESCRIPTION)
                .textColumn(DESCRIPTION)
                .intColumn(IS_SUBSCRIPTION_POSSIBLE)
                .intColumn(IS_PINNED)
                .textColumn(PHOTOS)
                .textColumn(LINKS)
                .primaryKey(ID)
                .execute(database);
    }

    @NonNull
    @Override
    public ContentValues toValues(@NonNull Event event) {
        ContentValues values = new ContentValues();
        values.put(ID, event.getId());
        values.put(NAME, event.getName());
        values.put(PREVIEW_DESCRIPTION, event.getPreviewDescription());
        values.put(PREVIEW_IMAGE, event.getPreviewImage());
        values.put(DESCRIPTION, event.getDescription());
        values.put(IS_SUBSCRIPTION_POSSIBLE, event.isSubscriptionPossible());
        values.put(IS_PINNED, event.isPinned());
        values.put(PHOTOS, GsonHolder.getGson().toJson(event.getPhotos()));
        values.put(LINKS, GsonHolder.getGson().toJson(event.getLinks()));
        return values;
    }

    @NonNull
    @Override
    public Event fromCursor(@NonNull Cursor cursor) {
        Event event = new Event();
        event.setId(cursor.getInt(cursor.getColumnIndex(ID)));
        event.setName(cursor.getString(cursor.getColumnIndex(NAME)));
        event.setPreviewImage(cursor.getString(cursor.getColumnIndex(PREVIEW_IMAGE)));
        event.setPreviewDescription(cursor.getString(cursor.getColumnIndex(PREVIEW_DESCRIPTION)));
        event.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
        event.setSubscriptionPossible(cursor.getInt(cursor.getColumnIndex(IS_SUBSCRIPTION_POSSIBLE)) > 0);
        event.setPinned(cursor.getInt(cursor.getColumnIndex(IS_PINNED)) > 0);

        String photosJson = cursor.getString(cursor.getColumnIndex(PHOTOS));
        List<Photo> photos = GsonHolder.getGson().fromJson(photosJson, new TypeToken<List<Photo>>() {
        }.getType());
        event.setPhotos(photos);

        String linksJson = cursor.getString(cursor.getColumnIndex(LINKS));
        List<Link> links = GsonHolder.getGson().fromJson(linksJson, new TypeToken<List<Link>>() {
        }.getType());
        event.setLinks(links);

        return event;
    }
}
