package ru.gdg.kazan.gdgkazan.screens.events;

import android.support.annotation.NonNull;

import java.util.List;

import ru.gdg.kazan.gdgkazan.models.Event;

/**
 * @author Artur Vasilov
 */
public interface EventsView {

    void showEvents(@NonNull List<Event> events);
}
