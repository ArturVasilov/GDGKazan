package ru.gdg.kazan.gdgkazan.app;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * @author Artur Vasilov
 */
public class FirebaseEventBuilder {

    private final Bundle mBundle;

    private final FirebaseAnalytics mAnalytics;

    @NonNull
    public static FirebaseEventBuilder newEvent(@NonNull FirebaseAnalytics analytics) {
        return new FirebaseEventBuilder(analytics);
    }

    private FirebaseEventBuilder(@NonNull FirebaseAnalytics analytics) {
        mAnalytics = analytics;
        mBundle = new Bundle();
    }

    @NonNull
    public FirebaseEventBuilder putString(@NonNull String key, @NonNull String value) {
        mBundle.putString(key, value);
        return this;
    }

    public void log(@NonNull String eventTag) {
        mAnalytics.logEvent(eventTag, mBundle);
    }

}
