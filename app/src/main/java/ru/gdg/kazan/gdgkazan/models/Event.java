package ru.gdg.kazan.gdgkazan.models;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

/**
 * @author Valiev Timur.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

    @JsonProperty("id")
    private long mId;

    @JsonProperty("name")
    private String mName;

    @JsonProperty("description")
    private String mDescription;

    @JsonProperty("date_start")
    private String mDateStart;

    @JsonProperty("date_finish")
    private String mDateFinish;

    @JsonProperty("status")
    private EventStatus mStatus;

    @JsonProperty("preview_img")
    private String mPreviewImage;

    @JsonProperty("header_img")
    private String mHeaderImage;

    @JsonProperty("photos")
    private List<String> mPhotos;

    @JsonProperty("links")
    private List<String> mLinks;

    public long getId() {
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
    public String getHeaderImage() {
        return mHeaderImage;
    }

    @NonNull
    public List<String> getPhotos() {
        if (mPhotos == null) {
            return Collections.emptyList();
        }
        return mPhotos;
    }

    @NonNull
    public List<String> getLinks() {
        if (mLinks == null) {
            return Collections.emptyList();
        }
        return mLinks;
    }
}
