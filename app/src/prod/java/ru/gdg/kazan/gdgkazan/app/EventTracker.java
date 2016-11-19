package ru.gdg.kazan.gdgkazan.app;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.flurry.android.FlurryAgent;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Artur Vasilov
 */
public class EventTracker {

    private final Map<String, String> mParamsMap;

    private final FirebaseAnalytics mAnalytics;

    @NonNull
    public static EventTracker newEvent(@NonNull FirebaseAnalytics analytics) {
        return new EventTracker(analytics);
    }

    private EventTracker(@NonNull FirebaseAnalytics analytics) {
        mAnalytics = analytics;
        mParamsMap = new HashMap<>();
    }

    @NonNull
    public EventTracker putString(@NonNull String key, @NonNull String value) {
        mParamsMap.put(key, value);
        return this;
    }

    public void log(@NonNull String eventTag) {
        Bundle bundle = new Bundle();
        for (Map.Entry<String, String> pair : mParamsMap.entrySet()) {
            bundle.putString(pair.getKey(), pair.getValue());
        }
        mAnalytics.logEvent(eventTag, bundle);

        FlurryAgent.logEvent(eventTag, mParamsMap);
    }

}
