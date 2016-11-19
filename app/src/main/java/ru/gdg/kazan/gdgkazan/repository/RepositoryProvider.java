package ru.gdg.kazan.gdgkazan.repository;

import android.support.annotation.NonNull;

import ru.gdg.kazan.gdgkazan.repository.preferences.FirebaseConfigStorage;
import ru.gdg.kazan.gdgkazan.repository.preferences.KeyValueStorage;

/**
 * @author Artur Vasilov
 */
public class RepositoryProvider {

    private static EventsRepository sEventsRepository;
    private static GeneralRepository sGeneralRepository;

    private static KeyValueStorage sKeyValueStorage;

    private RepositoryProvider() {
        //Not implemented
    }

    @NonNull
    public static EventsRepository provideEventsRepository() {
        if (sEventsRepository == null) {
            sEventsRepository = new EventsRepository();
        }
        return sEventsRepository;
    }

    @NonNull
    public static GeneralRepository provideGeneralRepository() {
        if (sGeneralRepository == null) {
            sGeneralRepository = new GeneralRepository();
        }
        return sGeneralRepository;
    }

    @NonNull
    public static KeyValueStorage provideKeyValueStorage() {
        if (sKeyValueStorage == null) {
            sKeyValueStorage = new FirebaseConfigStorage();
        }
        return sKeyValueStorage;
    }

}
