package ru.gdg.kazan.gdgkazan.screens.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import ru.gdg.kazan.gdgkazan.BuildConfig;
import ru.gdg.kazan.gdgkazan.R;
import ru.gdg.kazan.gdgkazan.models.config.RemoteConfig;
import ru.gdg.kazan.gdgkazan.repository.RepositoryProvider;
import ru.gdg.kazan.gdgkazan.screens.events.EventsActivity;

/**
 * @author Artur Vasilov
 */
public class SplashActivity extends AppCompatActivity {

    //TODO : refactor

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_splash);

        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        final String beforeUrl = RemoteConfig.getString(RemoteConfig.EVENTS_URL);
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        remoteConfig.setConfigSettings(configSettings);
        remoteConfig.setDefaults(R.xml.remofe_config_defaults);

        remoteConfig.fetch(0)
                .addOnCompleteListener(task -> {
                    remoteConfig.activateFetched();
                    RemoteConfig.saveConfig(remoteConfig);
                    String newUrl = RemoteConfig.getString(RemoteConfig.EVENTS_URL);
                    if (TextUtils.isEmpty(newUrl)) {
                        showSplashError();
                        return;
                    }

                    if (TextUtils.equals(newUrl, beforeUrl)) {
                        showEvents();
                    } else {
                        RepositoryProvider.provideEventsRepository()
                                .fetchEvents()
                                .subscribe(events -> {
                                    showEvents();
                                }, throwable -> showSplashError());
                    }
                })
                .addOnFailureListener(e -> showSplashError());
    }

    private void showSplashError() {
        //TODO
    }

    private void showEvents() {
        EventsActivity.start(this);
        supportFinishAfterTransition();
    }
}
