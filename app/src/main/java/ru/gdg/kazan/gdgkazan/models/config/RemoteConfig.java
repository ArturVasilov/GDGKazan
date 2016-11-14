package ru.gdg.kazan.gdgkazan.models.config;

import android.support.annotation.NonNull;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.orhanobut.hawk.Hawk;

/**
 * @author Artur Vasilov
 */
public final class RemoteConfig {

    public static final String COLOR_PRIMARY = "colorPrimary";
    public static final String COLOR_PRIMARY_DARK = "colorPrimaryDark";
    public static final String COLOR_ACCENT = "colorAccent";
    public static final String COLOR_PRIMARY_TEXT = "colorPrimaryText";
    public static final String COLOR_SECONDARY_DARK_TEXT = "colorSecondaryDarkText";
    public static final String COLOR_DARK_TEXT = "colorDarkText";
    public static final String COLOR_PROGRESS = "colorProgress";
    public static final String COLOR_PROGRESS_SEMI_TRANSPARENT = "colorProgressSemiTransparent";

    public static final String EVENTS_URL = "events_url";

    private RemoteConfig() {
    }

    @NonNull
    public static String getString(@NonNull String key) {
        return Hawk.get(key);
    }

    @NonNull
    public static String getString(@NonNull String key, @NonNull String defaultValue) {
        return Hawk.get(key, defaultValue);
    }

    public static void saveConfig(@NonNull FirebaseRemoteConfig config) {
        Hawk.put(COLOR_PRIMARY, config.getString(COLOR_PRIMARY));
        Hawk.put(COLOR_PRIMARY_DARK, config.getString(COLOR_PRIMARY_DARK));
        Hawk.put(COLOR_ACCENT, config.getString(COLOR_ACCENT));
        Hawk.put(COLOR_PRIMARY_TEXT, config.getString(COLOR_PRIMARY_TEXT));
        Hawk.put(COLOR_SECONDARY_DARK_TEXT, config.getString(COLOR_SECONDARY_DARK_TEXT));
        Hawk.put(COLOR_DARK_TEXT, config.getString(COLOR_DARK_TEXT));
        Hawk.put(COLOR_PROGRESS, config.getString(COLOR_PROGRESS));
        Hawk.put(COLOR_PROGRESS_SEMI_TRANSPARENT, config.getString(COLOR_PROGRESS_SEMI_TRANSPARENT));

        Hawk.put(EVENTS_URL, config.getString(EVENTS_URL));
    }

}
