package ru.gdg.kazan.gdgkazan.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author Artur Vasilov
 */
public class Event implements Serializable, Comparable<Event> {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("preview_img")
    private String mPreviewImage;

    @SerializedName("preview_description")
    private String mPreviewDescription;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("subscription_possible")
    private boolean mIsSubscriptionPossible;

    @SerializedName("is_pinned")
    private boolean mIsPinned;

    @SerializedName("photos")
    private List<Photo> mPhotos;

    @SerializedName("links")
    private List<Link> mLinks;

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
    public String getPreviewImage() {
        return mPreviewImage;
    }

    public void setPreviewImage(@NonNull String previewImage) {
        mPreviewImage = previewImage;
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

    public boolean isSubscriptionPossible() {
        return mIsSubscriptionPossible;
    }

    public void setSubscriptionPossible(boolean subscriptionPossible) {
        mIsSubscriptionPossible = subscriptionPossible;
    }

    public boolean isPinned() {
        return mIsPinned;
    }

    public void setPinned(boolean pinned) {
        mIsPinned = pinned;
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

    @Override
    public int compareTo(@NonNull Event event) {
        if (isPinned() == event.isPinned()) {
            return event.getId() - getId();
        }
        if (isPinned() && !event.isPinned()) {
            return -1;
        }
        return 1;
    }
}
