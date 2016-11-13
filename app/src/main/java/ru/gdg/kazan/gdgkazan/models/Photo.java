package ru.gdg.kazan.gdgkazan.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Artur Vasilov
 */
public class Photo implements Serializable {

    @SerializedName("title")
    private String mTitle;

    @SerializedName("url")
    private String mUrl;

    public Photo(@NonNull String title, @NonNull String url) {
        mTitle = title;
        mUrl = url;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public String getUrl() {
        return mUrl;
    }
}
