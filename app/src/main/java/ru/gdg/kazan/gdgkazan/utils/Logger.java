package ru.gdg.kazan.gdgkazan.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import ru.gdg.kazan.gdgkazan.BuildConfig;

/**
 * @author Maksim Radko
 *         on 10/6/16
 */
public final class Logger {

    private Logger() {
        throw new IllegalStateException("Utility class can not be instantiated");
    }

    public static void e(@NonNull String message, @NonNull Throwable throwable) {
        Log.e(BuildConfig.APPLICATION_ID, message, throwable);
    }

    public static void e(@NonNull Class<?> cls, @NonNull String msg, @NonNull Throwable thr) {
        Log.e(cls.getSimpleName(), msg, thr);
    }

    public static void w(@NonNull Class<?> cls, @NonNull String msg) {
        Log.w(cls.getSimpleName(), msg);
    }
}
