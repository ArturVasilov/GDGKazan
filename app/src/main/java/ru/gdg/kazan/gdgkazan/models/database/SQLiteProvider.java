package ru.gdg.kazan.gdgkazan.models.database;

import android.support.annotation.NonNull;

import ru.arturvasilov.sqlite.core.SQLiteConfig;
import ru.arturvasilov.sqlite.core.SQLiteContentProvider;
import ru.arturvasilov.sqlite.core.SQLiteSchema;

/**
 * @author Artur Vasilov
 */
public class SQLiteProvider extends SQLiteContentProvider {

    private static final String DATABASE_NAME = "database.db";
    private static final String CONTENT_AUTHORITY = "ru.gdg.kazan.gdgkazan";

    @Override
    protected void prepareConfig(@NonNull SQLiteConfig config) {
        config.setDatabaseName(DATABASE_NAME);
        config.setAuthority(CONTENT_AUTHORITY);
    }

    @Override
    protected void prepareSchema(@NonNull SQLiteSchema schema) {
        schema.register(EventsTable.TABLE);
        schema.register(EventSubscriptionsTable.TABLE);
    }
}
