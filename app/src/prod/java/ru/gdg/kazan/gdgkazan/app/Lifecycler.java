package ru.gdg.kazan.gdgkazan.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.flurry.android.FlurryAgent;

/**
 * @author Artur Vasilov
 */
public class Lifecycler implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        // Do nothing
    }

    @Override
    public void onActivityStarted(Activity activity) {
        FlurryAgent.onStartSession(activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        // Do nothing
    }

    @Override
    public void onActivityPaused(Activity activity) {
        // Do nothing
    }

    @Override
    public void onActivityStopped(Activity activity) {
        FlurryAgent.onEndSession(activity);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        // Do nothing
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        // Do nothing
    }
}
