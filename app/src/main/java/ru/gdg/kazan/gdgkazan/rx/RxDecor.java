package ru.gdg.kazan.gdgkazan.rx;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import ru.gdg.kazan.gdgkazan.R;
import ru.gdg.kazan.gdgkazan.base.view.EmptyView;
import ru.gdg.kazan.gdgkazan.base.view.ErrorView;
import ru.gdg.kazan.gdgkazan.base.view.LoadingView;
import ru.gdg.kazan.gdgkazan.utils.Logger;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

/**
 * @author Daniel Serdyukov
 */
public final class RxDecor {

    private static final SparseArrayCompat<Integer> HTTP_ERRORS = new SparseArrayCompat<>();

    private static final List<Class<?>> NETWORK_EXCEPTIONS = Arrays.asList(
            UnknownHostException.class,
            SocketTimeoutException.class,
            ConnectException.class
    );

    static {
        HTTP_ERRORS.put(500, R.string.error_unexpected);
        HTTP_ERRORS.put(502, R.string.error_unexpected);
    }

    private RxDecor() {
        //Unnecessary constructor
    }

    @NonNull
    public static <T> Observable.Transformer<T, T> loading(@NonNull LoadingView view) {
        return observable -> observable
                .doOnSubscribe(view::showLoadingIndicator)
                .doOnTerminate(view::hideLoadingIndicator)
                .doOnUnsubscribe(view::hideLoadingIndicator);
    }

    @NonNull
    public static Action1<Throwable> error(@NonNull ErrorView view) {
        return e -> {
            Logger.e(RxDecor.class, e.getMessage(), e);
            if (e instanceof HttpException) {
                dispatchHttpException(view, (HttpException) e);
            } else if (NETWORK_EXCEPTIONS.contains(e.getClass())) {
                view.showNetworkError();
            } else {
                view.showUnexpectedError();
            }
        };
    }

    private static void dispatchHttpException(@NonNull ErrorView view, @NonNull HttpException e) {
        final Integer resourceName = HTTP_ERRORS.get(e.code());
        if (resourceName != null) {
            view.showErrorMessage(resourceName);
        } else {
            view.showErrorMessage(e.message());
        }
    }

    @NonNull
    public static <T> Observable.Transformer<T, T> emptyStub(@NonNull EmptyView view) {
        return observable -> observable
                .doOnSubscribe(view::hideEmptyStub)
                .switchIfEmpty(emptyObservable(view));
    }

    private static <T> Observable<T> emptyObservable(@NonNull EmptyView view) {
        return Observable.create((Observable.OnSubscribe<T>) Observer::onCompleted)
                .doOnCompleted(view::showEmptyStub);
    }

}
