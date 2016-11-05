package ru.gdg.kazan.gdgkazan.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

/**
 * @author Artur Vasilov
 */
public class Event {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("date_start")
    private String mDateStart;

    @SerializedName("date_finish")
    private String mDateFinish;

    @SerializedName("status")
    private EventStatus mStatus;

    @SerializedName("preview_img")
    private String mPreviewImage;

    @SerializedName("photos")
    private List<Photo> mPhotos;

    @SerializedName("links")
    private List<Link> mLinks;

    public int getId() {
        return mId;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    @NonNull
    public String getDescription() {
        return mDescription;
    }

    @NonNull
    public String getDateStart() {
        return mDateStart;
    }

    @NonNull
    public String getDateFinish() {
        return mDateFinish;
    }

    @NonNull
    public EventStatus getStatus() {
        return mStatus;
    }

    @NonNull
    public String getPreviewImage() {
        return mPreviewImage;
    }

    @NonNull
    public List<Photo> getPhotos() {
        if (mPhotos == null) {
            return Collections.emptyList();
        }
        return mPhotos;
    }

    @NonNull
    public List<Link> getLinks() {
        if (mLinks == null) {
            return Collections.emptyList();
        }
        return mLinks;
    }
}
