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

        if (event.getLinks().isEmpty()) {
            mView.hideLinks();
        } else {
            mView.showLinks(event.getLinks());
        }

        if (event.getPhotos().isEmpty()) {
            mView.hidePhotos();
        } else {
            mView.showPhotos(event.getPhotos());
        }
    }
}
