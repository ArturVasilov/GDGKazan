package ru.gdg.kazan.gdgkazan.repository.events;

import android.support.annotation.NonNull;

import java.util.List;

import retrofit2.http.GET;
import ru.gdg.kazan.gdgkazan.models.Event;
import rx.Observable;

/**
 * @author Valiev Timur.
 */
public interface EventsService {

    @NonNull
    @GET("/api/v1/events")
    Observable<List<Event>> fetchEvents();
}
