package ru.gdg.kazan.gdgkazan.screens.events;

import android.support.annotation.NonNull;

import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.gdg.kazan.gdgkazan.R;
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

    public void init() {
        RepositoryProvider.provideEventsRepository()
                .fetchEvents()
                .doOnSubscribe(mView::showLoading)
                .doOnTerminate(mView::hideLoading)
                .compose(mLifecycleHandler.load(R.id.events_request))
                .subscribe(mView::showEvents, throwable -> {
                });
    }
}
