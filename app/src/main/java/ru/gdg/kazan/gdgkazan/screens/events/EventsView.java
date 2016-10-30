package ru.gdg.kazan.gdgkazan.screens.events;

import android.support.annotation.NonNull;

import java.util.List;

import ru.gdg.kazan.gdgkazan.base.view.LoadingView;
import ru.gdg.kazan.gdgkazan.models.Event;

/**
 * @author Valiev Timur.
 */
public interface EventsView extends LoadingView {

    void showEvents(@NonNull List<Event> events);
}
