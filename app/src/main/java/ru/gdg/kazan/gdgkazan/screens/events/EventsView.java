package ru.gdg.kazan.gdgkazan.screens.events;

import android.support.annotation.NonNull;

import java.util.List;

import ru.gdg.kazan.gdgkazan.models.Event;
import ru.gdg.kazan.gdgkazan.screens.general.LoadingView;

/**
 * @author Artur Vasilov
 */
public interface EventsView extends LoadingView {

    void showEvents(@NonNull List<Event> events);
}
