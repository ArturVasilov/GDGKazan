package ru.gdg.kazan.gdgkazan.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author Artur Vasilov
 */
public class Event implements Serializable {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("preview_description")
    private String mPreviewDescription;

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

    private boolean mIsSubscribed;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    public void setName(@NonNull String name) {
        mName = name;
    }

    @NonNull
    public String getPreviewDescription() {
        return mPreviewDescription;
    }

    public void setPreviewDescription(@NonNull String previewDescription) {
        mPreviewDescription = previewDescription;
    }

    @NonNull
    public String getDescription() {
        return mDescription;
    }

    public void setDescription(@NonNull String description) {
        mDescription = description;
    }

    @NonNull
    public String getDateStart() {
        return mDateStart;
    }

    public void setDateStart(@NonNull String dateStart) {
        mDateStart = dateStart;
    }

    @NonNull
    public String getDateFinish() {
        return mDateFinish;
    }

    public void setDateFinish(@NonNull String dateFinish) {
        mDateFinish = dateFinish;
    }

    @NonNull
    public EventStatus getStatus() {
        return mStatus;
    }

    public void setStatus(@NonNull EventStatus status) {
        mStatus = status;
    }

    @NonNull
    public String getPreviewImage() {
        return mPreviewImage;
    }

    public void setPreviewImage(@NonNull String previewImage) {
        mPreviewImage = previewImage;
    }

    @NonNull
    public List<Photo> getPhotos() {
        if (mPhotos == null) {
            return Collections.emptyList();
        }
        return mPhotos;
    }

    public void setPhotos(@NonNull List<Photo> photos) {
        mPhotos = photos;
    }

    @NonNull
    public List<Link> getLinks() {
        if (mLinks == null) {
            return Collections.emptyList();
        }
        return mLinks;
    }

    public void setLinks(@NonNull List<Link> links) {
        mLinks = links;
    }

    public boolean isSubscribed() {
        return mIsSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        mIsSubscribed = subscribed;
    }
}
