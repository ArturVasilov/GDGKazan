package ru.gdg.kazan.gdgkazan.screens.event;

import android.support.annotation.NonNull;

import ru.gdg.kazan.gdgkazan.app.Analytics;
import ru.gdg.kazan.gdgkazan.models.Event;
import ru.gdg.kazan.gdgkazan.models.Link;

/**
 * @author Artur Vasilov
 */
public class EventPresenter {

    private final EventView mView;

    private final Event mEvent;

    public EventPresenter(@NonNull EventView view, @NonNull Event event) {
        mView = view;
        mEvent = event;
    }

    public void init() {
        mView.showTitle(mEvent.getName());
        mView.showDescription(mEvent.getDescription());

        if (mEvent.getLinks().isEmpty()) {
            mView.hideLinks();
        } else {
            mView.showLinks(mEvent.getLinks());
        }

        if (mEvent.getPhotos().isEmpty()) {
            mView.hidePhotos();
        } else {
            mView.showPhotos(mEvent.getPhotos());
        }
    }

    public void onPhotoClick(int selectedPosition) {
        Analytics.logEventPhotoClicked(mEvent, selectedPosition);
        mView.showPhotosPager(mEvent.getPhotos(), selectedPosition);
    }

    public void onLinkClick(@NonNull Link link) {
        Analytics.logEventLinkClicked(mEvent, link);
        mView.openLink(link.getUrl());
    }
}
