package ru.gdg.kazan.gdgkazan.screens.event;

import android.support.annotation.NonNull;

import ru.gdg.kazan.gdgkazan.models.Event;

/**
 * @author Artur Vasilov
 */
public class EventPresenter {

    private final EventView mView;

    public EventPresenter(@NonNull EventView view) {
        mView = view;
    }

    public void init(@NonNull Event event) {
        mView.showTitle(event.getName());
        mView.showDescription(event.getDescription());
        mView.showLinks(event.getLinks());
        mView.showPhotos(event.getPhotos());
    }
}
