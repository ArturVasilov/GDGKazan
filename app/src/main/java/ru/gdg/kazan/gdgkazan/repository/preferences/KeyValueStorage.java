package ru.gdg.kazan.gdgkazan.repository.preferences;

import android.support.annotation.NonNull;

/**
 * @author Artur Vasilov
 */
public interface KeyValueStorage {

    String COLOR_PRIMARY = "colorPrimary";
    String COLOR_PRIMARY_DARK = "colorPrimaryDark";
    String COLOR_ACCENT = "colorAccent";
    String COLOR_PRIMARY_TEXT = "colorPrimaryText";
    String COLOR_SECONDARY_DARK_TEXT = "colorSecondaryDarkText";
    String COLOR_DARK_TEXT = "colorDarkText";
    String COLOR_PROGRESS = "colorProgress";
    String COLOR_PROGRESS_SEMI_TRANSPARENT = "colorProgressSemiTransparent";

    String EVENTS_URL = "events_url";

    @NonNull
    String getString(@NonNull String key);

    @NonNull
    String getString(@NonNull String key, @NonNull String defaultValue);

}
