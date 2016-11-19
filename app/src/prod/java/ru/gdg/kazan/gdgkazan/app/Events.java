package ru.gdg.kazan.gdgkazan.app;

/**
 * @author Artur Vasilov
 */
public interface Events {

    //region SPLASH

    String SPLASH_SCREEN_STARTED = "splash_screen_started";
    String SPLASH_CONFIG_LOAD_SUCCESS = "splash_config_load_success";
    String SPLASH_FAILED = "splash_failed";
    String SPLASH_LOCAL_AUTH = "splash_local_auth";
    String SPLASH_LOADING_EVENTS = "splash_loading_events";
    String SPLASH_RESTART_LOADING = "splash_restart_loading";

    //endregion SPLASH

    //region EVENTS

    String EVENTS_SCREEN_STARTED = "events_screen_started";
    String EVENTS_EVENT_LOGO_CLICK = "events_event_logo_click";
    String EVENTS_EVENT_CARD_CLICK = "events_event_card_click";
    String EVENTS_EVENT_MORE_BUTTON_CLICK = "events_event_more_button_click";
    String EVENTS_EVENT_NOTIFICATION_SWITCHER_ENABLED = "events_event_notification_switcher_enabled";
    String EVENTS_EVENT_NOTIFICATION_SWITCHER_DISABLED = "events_event_notification_switcher_disabled";

    //endregion EVENTS

    //region EVENTS

    String EVENT_SCREEN_STARTED = "event_screen_started";
    String EVENT_LINK_CLICK = "event_link_click";
    String EVENT_PHOTO_CLICK = "event_photo_click";

    //endregion EVENT

    //region PHOTOS

    String PHOTOS_PHOTO_SCROLLED = "photos_photo_scrolled";

    //endregion PHOTOS

    //region NOTIFICATIONS

    String NOTIFICATIONS_TOKEN_REFRESHED = "notification_token_refreshed";
    String NOTIFICATION_RECEIVE = "notification_receive";
    String NOTIFICATION_SHOW = "notification_show";
    String NOTIFICATION_IGNORE = "notification_ignored";
    String NOTIFICATION_CLICK = "notification_click";
    String NOTIFICATION_DISMISS = "notification_dismiss";
    String NOTIFICATION_SPECIAL_ACTION = "notification_special_action";

    //endregion NOTIFICATIONS

}
