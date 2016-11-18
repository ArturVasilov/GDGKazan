package ru.gdg.kazan.gdgkazan.app;

import android.content.Context;
import android.support.annotation.NonNull;

import ru.gdg.kazan.gdgkazan.models.Event;
import ru.gdg.kazan.gdgkazan.models.Link;

/**
 * @author Artur Vasilov
 */
@SuppressWarnings("UnusedParameters")
public final class Analytics {

    private Analytics() {
    }

    public static void init(@NonNull Context context) {
        // Do nothing
    }

    //region SPLASH

    public static void logSplashScreenStarted() {
        // Do nothing
    }

    public static void logConfigSuccess() {
        // Do nothing
    }

    public static void logSplashFailed(boolean isConfigSuccess, @NonNull Throwable throwable) {
        // Do nothing
    }

    public static void logLocalSplashAuth() {
        // Do nothing
    }

    public static void logLoadingEvents() {
        // Do nothing
    }

    public static void logRestartSplash(boolean isConfigSuccess) {
        // Do nothing
    }

    //endregion SPLASH

    //region EVENTS

    public static void logEventsScreenStarted() {
        // Do nothing
    }

    public static void logEventLogoClick(@NonNull Event event) {
        // Do nothing
    }

    public static void logEventCardClick(@NonNull Event event) {
        // Do nothing
    }

    public static void logEventMoreButtonClick(@NonNull Event event) {
        // Do nothing
    }

    public static void logEventNotificationsSwitcherEnabled(@NonNull Event event) {
        // Do nothing
    }

    public static void logEventNotificationsSwitcherDisabled(@NonNull Event event) {
        // Do nothing
    }

    //endregion EVENTS

    //region EVENTS

    public static void logEventScreenStarted() {
        // Do nothing
    }

    public static void logEventLinkClicked(@NonNull Event event, @NonNull Link link) {
        // Do nothing
    }

    public static void logEventPhotoClicked(@NonNull Event event, int index) {
        // Do nothing
    }

    //endregion EVENT

    //region PHOTOS

    public static void logPhotoScrolled(int current, int all) {
        // Do nothing
    }

    //endregion PHOTOS

    //region NOTIFICATIONS

    public static void logTokenRefreshed() {
        // Do nothing
    }

    public static void logNotificationReceived(int eventId, int notificationId) {
        // Do nothing
    }

    public static void logShowNotification(int eventId, int notificationId) {
        // Do nothing
    }

    public static void logIgnoreNotification(int eventId, int notificationId) {
        // Do nothing
    }

    public static void logNotificationClicked(int eventId, int notificationId) {
        // Do nothing
    }

    public static void logNotificationDismissed(int eventId, int notificationId) {
        // Do nothing
    }

    //endregion NOTIFICATIONS

}
