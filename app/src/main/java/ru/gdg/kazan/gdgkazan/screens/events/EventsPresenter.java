package ru.gdg.kazan.gdgkazan.screens.events;

import android.support.annotation.NonNull;

import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.gdg.kazan.gdgkazan.R;
import ru.gdg.kazan.gdgkazan.models.Event;
import ru.gdg.kazan.gdgkazan.repository.RepositoryProvider;

/**
 * @author Artur Vasilov
 */
public class EventsPresenter {

    private final LifecycleHandler mLifecycleHandler;
    private final EventsView mView;

    public EventsPresenter(@NonNull EventsView view, @NonNull LifecycleHandler lifecycleHandler) {
        mView = view;
        mLifecycleHandler = lifecycleHandler;
    }

    public void init(int showEventId) {
        RepositoryProvider.provideEventsRepository()
                .fetchLocalEvents()
                .compose(mLifecycleHandler.load(R.id.events_request))
                .subscribe(events -> {
                    mView.showEvents(events);
                    if (showEventId >= 0) {
                        for (Event event : events) {
                            if (event.getId() == showEventId) {
                                mView.showEventScreen(event);
                                break;
                            }
                        }
                    }
                }, throwable -> {
                });
    }
}
