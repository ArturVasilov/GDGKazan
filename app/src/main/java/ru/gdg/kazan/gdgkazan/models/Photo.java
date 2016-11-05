package ru.gdg.kazan.gdgkazan.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author Artur Vasilov
 */
public class Photo {

    @SerializedName("title")
    private String mTitle;

    @SerializedName("url")
    private String mUrl;

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public String getUrl() {
        return mUrl;
    }
}
