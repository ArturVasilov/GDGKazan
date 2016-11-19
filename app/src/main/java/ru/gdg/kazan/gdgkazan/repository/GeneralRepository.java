package ru.gdg.kazan.gdgkazan.repository;

import android.support.annotation.NonNull;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import ru.gdg.kazan.gdgkazan.BuildConfig;
import ru.gdg.kazan.gdgkazan.utils.Observables;
import rx.Observable;
import rx.functions.Func1;

/**
 * @author Artur Vasilov
 */
public class GeneralRepository {

    private static final long CACHE_EXPIRATION_MS = 43200;

    @NonNull
    public Observable<?> config() {
        FirebaseRemoteConfig config = FirebaseRemoteConfig.getInstance();
        long cacheExpiration = BuildConfig.DEBUG ? 1 : CACHE_EXPIRATION_MS;
        return Observables.taskObservable(config.fetch(cacheExpiration))
                .flatMap(value -> {
                    FirebaseRemoteConfig.getInstance().activateFetched();
                    return Observable.just(value);
                });
    }

}
