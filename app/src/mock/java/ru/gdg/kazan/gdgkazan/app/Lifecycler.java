package ru.gdg.kazan.gdgkazan.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

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
        // Do nothing
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
        // Do nothing
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
