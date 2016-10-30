package ru.gdg.kazan.gdgkazan.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.gdg.kazan.gdgkazan.api.ApiFactory;
import ru.gdg.kazan.gdgkazan.repository.events.EventsRepository;
import ru.gdg.kazan.gdgkazan.repository.events.EventsService;

/**
 * @author Valiev Timur.
 */
public class RepositoryProvider {

    @Nullable
    private static EventsRepository sEventsRepository;


    private RepositoryProvider() {
        //Not implemented
    }

    @NonNull
    private static <T> T getServiceInstance(Class<T> clazz) {
        return ApiFactory.getRetrofitInstance().create(clazz);
    }

    @NonNull
    public static EventsRepository provideShopsRepository() {
        if (sEventsRepository == null) {
            sEventsRepository = new EventsRepository(getServiceInstance(EventsService.class));
        }
        return sEventsRepository;
    }

}
