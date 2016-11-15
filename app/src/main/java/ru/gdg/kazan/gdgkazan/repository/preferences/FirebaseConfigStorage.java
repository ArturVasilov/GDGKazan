package ru.gdg.kazan.gdgkazan.repository.preferences;

import android.support.annotation.NonNull;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

/**
 * @author Artur Vasilov
 */
public class FirebaseConfigStorage implements KeyValueStorage {

    private final FirebaseRemoteConfig mRemoteConfig;

    public FirebaseConfigStorage() {
        mRemoteConfig = FirebaseRemoteConfig.getInstance();
    }

    @NonNull
    @Override
    public String getString(@NonNull String key) {
        return mRemoteConfig.getString(key);
    }

    @NonNull
    @Override
    public String getString(@NonNull String key, @NonNull String defaultValue) {
        return mRemoteConfig.getString(key, defaultValue);
    }
}
