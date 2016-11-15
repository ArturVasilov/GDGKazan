package ru.gdg.kazan.gdgkazan.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import ru.gdg.kazan.gdgkazan.utils.TextUtils;

/**
 * @author Artur Vasilov
 */
public final class Env {

    private Env() {
    }

    public static void browseUrl(@NonNull Context context, @NonNull String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        try {
            Uri uri = Uri.parse(url);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(browserIntent);
        } catch (Exception ignored) {
        }
    }

}
