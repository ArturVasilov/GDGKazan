package ru.gdg.kazan.gdgkazan.utils;

import android.support.annotation.ColorRes;
import android.support.annotation.StyleRes;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import ru.gdg.kazan.gdgkazan.R;

/**
 * @author Artur Vasilov
 */
public final class ThemeUtils {

    private static final String THEME_KEY = "theme";

    private static final String RED_THEME = "red_theme";
    private static final String GREEN_THEME = "green_theme";
    private static final String BLUE_THEME = "blue_theme";

    private ThemeUtils() {
    }

    @StyleRes
    public static int obtainThemeFromConfig() {
        String theme = FirebaseRemoteConfig.getInstance().getString(THEME_KEY);
        if (TextUtils.equals(RED_THEME, theme)) {
            return R.style.AppTheme_Red;
        } else if (TextUtils.equals(GREEN_THEME, theme)) {
            return R.style.AppTheme_Green;
        } else if (TextUtils.equals(BLUE_THEME, theme)) {
            return R.style.AppTheme_Blue;
        }
        return R.style.AppTheme_Red;
    }

    @ColorRes
    public static int obtainAccentColorFromConfig() {
        String theme = FirebaseRemoteConfig.getInstance().getString(THEME_KEY);
        if (TextUtils.equals(RED_THEME, theme)) {
            return R.color.redThemeColorAccent;
        } else if (TextUtils.equals(GREEN_THEME, theme)) {
            return R.color.greenThemeColorAccent;
        } else if (TextUtils.equals(BLUE_THEME, theme)) {
            return R.color.blueThemeColorAccent;
        }
        return R.color.redThemeColorAccent;
    }

}
