package ru.gdg.kazan.gdgkazan.models;

import android.support.annotation.NonNull;

/**
 * @author Artur Vasilov
 */
public class Config {

    public static final String EVENTS_URL = "events_url";

    private String mKey;
    private String mValue;

    @NonNull
    public String getKey() {
        return mKey;
    }

    public void setKey(@NonNull String key) {
        mKey = key;
    }

    @NonNull
    public String getValue() {
        return mValue;
    }

    public void setValue(@NonNull String value) {
        mValue = value;
    }
}
