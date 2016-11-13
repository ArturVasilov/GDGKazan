package ru.gdg.kazan.gdgkazan.screens.images;

import android.support.annotation.NonNull;

import java.util.List;

import ru.gdg.kazan.gdgkazan.models.Photo;

/**
 * @author Artur Vasilov
 */
public interface ImagesPagerView {

    void showPhotos(@NonNull List<Photo> photos);

    void showCurrentPhoto(int position);

    void setTitle(@NonNull String title);

}
