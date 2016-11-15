package ru.gdg.kazan.gdgkazan.utils;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;

import rx.AsyncEmitter;
import rx.Observable;
import rx.functions.Action1;

/**
 * @author Artur Vasilov
 */
public final class Observables {

    private Observables() {
    }

    @NonNull
    public static <T> Observable<T> taskObservable(@NonNull Task<T> task) {
        return Observable.fromEmitter(new Action1<AsyncEmitter<T>>() {
            @Override
            public void call(@NonNull AsyncEmitter<T> emitter) {
                task.addOnSuccessListener(emitter::onNext)
                        .addOnFailureListener(emitter::onError)
                        .addOnCompleteListener(task1 -> emitter.onCompleted());
            }
        }, AsyncEmitter.BackpressureMode.LATEST);
    }

}
