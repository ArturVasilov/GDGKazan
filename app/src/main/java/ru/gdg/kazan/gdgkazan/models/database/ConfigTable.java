package ru.gdg.kazan.gdgkazan.models.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import org.sqlite.database.sqlite.SQLiteDatabase;

import ru.arturvasilov.sqlite.core.BaseTable;
import ru.arturvasilov.sqlite.core.Table;
import ru.arturvasilov.sqlite.utils.TableBuilder;
import ru.gdg.kazan.gdgkazan.models.Config;

/**
 * @author Artur Vasilov
 */
public class ConfigTable extends BaseTable<Config> {

    public static final Table<Config> TABLE = new ConfigTable();

    public static final String KEY = "key";
    public static final String VALUE = "value";

    @Override
    public void onCreate(@NonNull SQLiteDatabase database) {
        TableBuilder.create(this)
                .textColumn(KEY)
                .textColumn(VALUE)
                .primaryKey(KEY)
                .execute(database);
    }

    @NonNull
    @Override
    public ContentValues toValues(@NonNull Config config) {
        ContentValues values = new ContentValues();
        values.put(KEY, config.getKey());
        values.put(VALUE, config.getValue());
        return values;
    }

    @NonNull
    @Override
    public Config fromCursor(@NonNull Cursor cursor) {
        Config config = new Config();
        config.setKey(cursor.getString(cursor.getColumnIndex(KEY)));
        config.setValue(cursor.getString(cursor.getColumnIndex(VALUE)));
        return config;
    }
}
