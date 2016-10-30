package ru.gdg.kazan.gdgkazan.base.view;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

/**
 * @author Daniel Serdyukov
 */
public interface ErrorView {

    void showNetworkError();

    void showUnexpectedError();

    void showErrorMessage(@NonNull String message);

    void hideErrorMessage();

    void showErrorMessage(@StringRes int messageRes);
}
