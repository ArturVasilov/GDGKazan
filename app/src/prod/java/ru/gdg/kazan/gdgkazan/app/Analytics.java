package ru.gdg.kazan.gdgkazan.app;

import android.content.Context;
import android.support.annotation.NonNull;

import com.flurry.android.FlurryAgent;
import com.google.firebase.analytics.FirebaseAnalytics;

import ru.gdg.kazan.gdgkazan.BuildConfig;
import ru.gdg.kazan.gdgkazan.models.Event;
import ru.gdg.kazan.gdgkazan.models.Link;

/**
 * @author Artur Vasilov
 */
public final class Analytics {

    private static FirebaseAnalytics sFirebaseAnalytics;

    private Analytics() {
    }

    public static void init(@NonNull Context context) {
        sFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

        FlurryAgent.init(context, "tH2W6T44X24JXGKTCW2SB");
        FlurryAgent.setVersionName(BuildConfig.VERSION_NAME);
        FlurryAgent.setLogEnabled(BuildConfig.DEBUG);
        FlurryAgent.setLogEvents(BuildConfig.DEBUG);
    }

    //region SPLASH

    public static void logSplashScreenStarted() {
        EventTracker.newEvent(sFirebaseAnalytics).log(Events.SPLASH_SCREEN_STARTED);
    }

    public static void logConfigSuccess() {
        EventTracker.newEvent(sFirebaseAnalytics).log(Events.SPLASH_CONFIG_LOAD_SUCCESS);
    }

    public static void logSplashFailed(boolean isConfigSuccess, @NonNull Throwable throwable) {
        EventTracker.newEvent(sFirebaseAnalytics)
                .putString(ParametersKeys.SPLASH_FAILED_CONFIG_SUCCESS, String.valueOf(isConfigSuccess))
                .putString(ParametersKeys.SPLASH_FAILED_MESSAGE, throwable.getMessage())
                .log(Events.SPLASH_FAILED);
    }

    public static void logLocalSplashAuth() {
        EventTracker.newEvent(sFirebaseAnalytics).log(Events.SPLASH_LOCAL_AUTH);
    }

    public static void logLoadingEvents() {
        EventTracker.newEvent(sFirebaseAnalytics).log(Events.SPLASH_LOADING_EVENTS);
    }

    public static void logRestartSplash(boolean isConfigSuccess) {
        EventTracker.newEvent(sFirebaseAnalytics)
                .putString(ParametersKeys.SPLASH_RESTART_CONFIG_SUCCESS, String.valueOf(isConfigSuccess))
                .log(Events.SPLASH_RESTART_LOADING);
    }

    //endregion SPLASH

    //region EVENTS

    public static void logEventsScreenStarted() {
        EventTracker.newEvent(sFirebaseAnalytics).log(Events.EVENTS_SCREEN_STARTED);
    }

    public static void logEventLogoClick(@NonNull Event event) {
        EventTracker.newEvent(sFirebaseAnalytics)
                .putString(ParametersKeys.EVENT_ID, String.valueOf(event.getId()))
                .log(Events.EVENTS_EVENT_LOGO_CLICK);
    }

    public static void logEventCardClick(@NonNull Event event) {
        EventTracker.newEvent(sFirebaseAnalytics)
                .putString(ParametersKeys.EVENT_ID, String.valueOf(event.getId()))
                .log(Events.EVENTS_EVENT_CARD_CLICK);
    }

    public static void logEventMoreButtonClick(@NonNull Event event) {
        EventTracker.newEvent(sFirebaseAnalytics)
                .putString(ParametersKeys.EVENT_ID, String.valueOf(event.getId()))
                .log(Events.EVENTS_EVENT_MORE_BUTTON_CLICK);
    }

    public static void logEventNotificationsSwitcherEnabled(@NonNull Event event) {
        EventTracker.newEvent(sFirebaseAnalytics)
                .putString(ParametersKeys.EVENT_ID, String.valueOf(event.getId()))
                .log(Events.EVENTS_EVENT_NOTIFICATION_SWITCHER_ENABLED);
    }

    public static void logEventNotificationsSwitcherDisabled(@NonNull Event event) {
        EventTracker.newEvent(sFirebaseAnalytics)
                .putString(ParametersKeys.EVENT_ID, String.valueOf(event.getId()))
                .log(Events.EVENTS_EVENT_NOTIFICATION_SWITCHER_DISABLED);
    }

    //endregion EVENTS

    //region EVENTS

    public static void logEventScreenStarted() {
        EventTracker.newEvent(sFirebaseAnalytics).log(Events.EVENT_SCREEN_STARTED);
    }

    public static void logEventLinkClick(@NonNull Event event, @NonNull Link link) {
        EventTracker.newEvent(sFirebaseAnalytics)
                .putString(ParametersKeys.EVENT_ID, String.valueOf(event.getId()))
                .putString(ParametersKeys.LINK, link.getTitle())
                .log(Events.EVENT_LINK_CLICK);
    }

    public static void logEventPhotoClick(@NonNull Event event, int index) {
        EventTracker.newEvent(sFirebaseAnalytics)
                .putString(ParametersKeys.EVENT_ID, String.valueOf(event.getId()))
                .putString(ParametersKeys.INDEX, String.valueOf(index))
                .log(Events.EVENT_PHOTO_CLICK);
    }

    //endregion EVENT

    //region PHOTOS

    public static void logPhotoScrolled(int current, int all) {
        EventTracker.newEvent(sFirebaseAnalytics)
                .putString(ParametersKeys.PHOTO_CURRENT_INDEX, String.valueOf(current))
                .putString(ParametersKeys.PHOTO_ALL_COUNT, String.valueOf(all))
                .log(Events.PHOTOS_PHOTO_SCROLLED);
    }

    //endregion PHOTOS

    //region NOTIFICATIONS

    public static void logTokenRefreshed() {
        EventTracker.newEvent(sFirebaseAnalytics).log(Events.NOTIFICATIONS_TOKEN_REFRESHED);
    }

    public static void logNotificationReceived(int eventId, int notificationId) {
        EventTracker.newEvent(sFirebaseAnalytics)
                .putString(ParametersKeys.EVENT_ID, String.valueOf(eventId))
                .putString(ParametersKeys.NOTIFICATION_ID, String.valueOf(notificationId))
                .log(Events.NOTIFICATION_RECEIVE);
    }

    public static void logNotificationReceived(int notificationId) {
        EventTracker.newEvent(sFirebaseAnalytics)
                .putString(ParametersKeys.NOTIFICATION_ID, String.valueOf(notificationId))
                .log(Events.NOTIFICATION_RECEIVE);
    }

    public static void logShowNotification(int eventId, int notificationId) {
        EventTracker.newEvent(sFirebaseAnalytics)
                .putString(ParametersKeys.EVENT_ID, String.valueOf(eventId))
                .putString(ParametersKeys.NOTIFICATION_ID, String.valueOf(notificationId))
                .log(Events.NOTIFICATION_SHOW);
    }

    public static void logIgnoreNotification(int eventId, int notificationId) {
        EventTracker.newEvent(sFirebaseAnalytics)
                .putString(ParametersKeys.EVENT_ID, String.valueOf(eventId))
                .putString(ParametersKeys.NOTIFICATION_ID, String.valueOf(notificationId))
                .log(Events.NOTIFICATION_IGNORE);
    }

    public static void logNotificationClick(int eventId, int notificationId) {
        EventTracker.newEvent(sFirebaseAnalytics)
                .putString(ParametersKeys.EVENT_ID, String.valueOf(eventId))
                .putString(ParametersKeys.NOTIFICATION_ID, String.valueOf(notificationId))
                .log(Events.NOTIFICATION_CLICK);
    }

    public static void logNotificationDismiss(int eventId, int notificationId) {
        EventTracker.newEvent(sFirebaseAnalytics)
                .putString(ParametersKeys.EVENT_ID, String.valueOf(eventId))
                .putString(ParametersKeys.NOTIFICATION_ID, String.valueOf(notificationId))
                .log(Events.NOTIFICATION_DISMISS);
    }

    //endregion NOTIFICATIONS

}
