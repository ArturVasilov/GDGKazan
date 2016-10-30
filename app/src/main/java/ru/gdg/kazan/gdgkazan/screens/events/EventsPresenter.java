package ru.gdg.kazan.gdgkazan.screens.events;

import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.gdg.kazan.gdgkazan.R;
import ru.gdg.kazan.gdgkazan.base.view.ErrorView;
import ru.gdg.kazan.gdgkazan.repository.RepositoryProvider;
import ru.gdg.kazan.gdgkazan.rx.RxDecor;

/**
 * @author Valiev Timur.
 */
public class EventsPresenter {

    private LifecycleHandler mLifecycleHandler;
    private ErrorView mErrorView;
    private final EventsView mView;

    public EventsPresenter(EventsView view, LifecycleHandler lifecycleHandler, ErrorView errorView) {
        mView = view;
        mLifecycleHandler = lifecycleHandler;
        mErrorView = errorView;
    }

    public void init() {
        RepositoryProvider.provideShopsRepository()
                .fetchEvents()
                .doOnSubscribe(mView::showLoadingIndicator)
                .doOnTerminate(mView::hideLoadingIndicator)
                .compose(mLifecycleHandler.load(R.id.events_request))
                .subscribe(mView::showEvents, throwable -> RxDecor.error(mErrorView));
    }
}
