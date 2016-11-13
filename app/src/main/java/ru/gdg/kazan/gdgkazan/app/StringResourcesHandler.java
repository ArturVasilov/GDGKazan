package ru.gdg.kazan.gdgkazan.app;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

/**
 * @author Artur Vasilov
 */
public interface StringResourcesHandler {

    @NonNull
    String getString(@StringRes int stringResId, @NonNull Object... args);

}
