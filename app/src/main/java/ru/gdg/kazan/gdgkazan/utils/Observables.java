package ru.gdg.kazan.gdgkazan.utils;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;

import rx.Emitter;
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
        return Observable.fromEmitter(new Action1<Emitter<T>>() {
            @Override
            public void call(@NonNull Emitter<T> emitter) {
                task.addOnSuccessListener(emitter::onNext)
                        .addOnFailureListener(emitter::onError)
                        .addOnCompleteListener(task1 -> emitter.onCompleted());
            }
        }, Emitter.BackpressureMode.LATEST);
    }

}
