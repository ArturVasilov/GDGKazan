package ru.gdg.kazan.gdgkazan.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author Artur Vasilov
 */
public class Link {

    @SerializedName("title")
    private String mTitle;

    @SerializedName("mUrl")
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
