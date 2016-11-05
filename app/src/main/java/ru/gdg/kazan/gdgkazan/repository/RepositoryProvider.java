package ru.gdg.kazan.gdgkazan.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author Artur Vasilov
 */
public class RepositoryProvider {

    @Nullable
    private static EventsRepository sEventsRepository;

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

}
