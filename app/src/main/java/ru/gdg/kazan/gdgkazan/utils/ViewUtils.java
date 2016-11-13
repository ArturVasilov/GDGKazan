package ru.gdg.kazan.gdgkazan.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.Window;

/**
 * @author Artur Vasilov
 */
public final class ViewUtils {

    private ViewUtils() {
    }

    public static int getStatusBarHeight(@NonNull Activity activity) {
        Rect rectangle = new Rect();
        Window window = activity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;
        int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return Math.abs(contentViewTop - statusBarHeight);
    }

}
