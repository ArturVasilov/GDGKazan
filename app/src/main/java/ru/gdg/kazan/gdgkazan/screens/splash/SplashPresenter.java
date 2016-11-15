package ru.gdg.kazan.gdgkazan.screens.splash;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.gdg.kazan.gdgkazan.R;
import ru.gdg.kazan.gdgkazan.repository.EventsRepository;
import ru.gdg.kazan.gdgkazan.repository.RepositoryProvider;
import ru.gdg.kazan.gdgkazan.repository.preferences.KeyValueStorage;
import ru.gdg.kazan.gdgkazan.utils.TextUtils;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class SplashPresenter {

    private static final int SPLASH_DELAY_MS = 800;

    private final SplashView mView;
    private final LifecycleHandler mLifecycleHandler;

    private final AtomicBoolean mIsConfigSuccess = new AtomicBoolean(false);

    public SplashPresenter(@NonNull SplashView view, @NonNull LifecycleHandler lifecycleHandler) {
        mView = view;
        mLifecycleHandler = lifecycleHandler;
    }

    public void init() {
        loadSplashData(false);
    }

    public void reload() {
        if (!mIsConfigSuccess.get()) {
            RepositoryProvider.provideEventsRepository()
                    .fetchEvents()
                    .doOnSubscribe(mView::showLoading)
                    .subscribe(events -> mView.showEvents(), throwable -> mView.showError());
        } else {
            loadSplashData(true);
        }
    }

    private void loadSplashData(boolean restart) {
        mView.showLoading();

        final EventsRepository eventsRepository = RepositoryProvider.provideEventsRepository();
        final KeyValueStorage keyValueStorage = RepositoryProvider.provideKeyValueStorage();
        final String beforeUrl = keyValueStorage.getString(KeyValueStorage.EVENTS_URL);

        Observable.Transformer<Object, Object> lifecycle = restart
                                                           ? mLifecycleHandler.reload(R.id.splash_request)
                                                           : mLifecycleHandler.load(R.id.splash_request);

        RepositoryProvider.provideGeneralRepository()
                .config()
                .take(1)
                .flatMap(o -> {
                    mIsConfigSuccess.set(true);
                    String newUrl = keyValueStorage.getString(KeyValueStorage.EVENTS_URL);
                    if (TextUtils.equals(newUrl, beforeUrl) && eventsRepository.hasLocalEvents()) {
                        return Observable.just(o);
                    }
                    return eventsRepository.fetchEvents();
                })
                .zipWith(Observable.just(1).delay(SPLASH_DELAY_MS, TimeUnit.MILLISECONDS), (o, integer) -> o)
                .compose(lifecycle)
                .subscribe(o -> mView.showEvents(), throwable -> mView.showError());
    }
}
