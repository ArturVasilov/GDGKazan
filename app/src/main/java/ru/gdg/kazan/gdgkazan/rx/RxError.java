package ru.gdg.kazan.gdgkazan.rx;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import ru.gdg.kazan.gdgkazan.base.view.ErrorView;
import ru.gdg.kazan.gdgkazan.dialogs.MessageDialog;
import ru.gdg.kazan.gdgkazan.dialogs.NetworkErrorDialog;
import ru.gdg.kazan.gdgkazan.dialogs.UnexpectedErrorDialog;
import ru.gdg.kazan.gdgkazan.utils.Logger;
import rx.functions.Action1;

/**
 * @author Daniel Serdyukov
 */
public final class RxError {

    private RxError() {
        //
    }

    @NonNull
    public static ErrorView view(@NonNull Activity activity) {
        return view(activity, activity.getFragmentManager());
    }

    @NonNull
    public static ErrorView view(@NonNull Fragment fragment) {
        return view(fragment.getActivity(), fragment.getFragmentManager());
    }

    @NonNull
    public static ErrorView view(@NonNull Activity activity, @NonNull FragmentManager fm) {
        return new ErrorView() {
            @Override
            public void showNetworkError() {
                new NetworkErrorDialog().show(fm, NetworkErrorDialog.class.getName());
            }

            @Override
            public void showUnexpectedError() {
                new UnexpectedErrorDialog().show(fm, UnexpectedErrorDialog.class.getName());
            }

            @Override
            public void showErrorMessage(@NonNull String message) {
                MessageDialog.showWithText(fm, message);
            }

            @Override
            public void showErrorMessage(@StringRes int messageRes) {
                MessageDialog.showWithText(fm, activity.getString(messageRes));
            }

            @Override
            public void hideErrorMessage() {
                //Not implemented yet
            }
        };
    }

    @NonNull
    public static Action1<Throwable> doNothing() {
        return throwable -> Logger.e(throwable.getMessage(), throwable);
    }
}
