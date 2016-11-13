package ru.gdg.kazan.gdgkazan.screens.images;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import ru.gdg.kazan.gdgkazan.R;
import ru.gdg.kazan.gdgkazan.app.StringResourcesHandler;
import ru.gdg.kazan.gdgkazan.models.Photo;

/**
 * @author Artur Vasilov
 */
public class ImagesPagerPresenter {

    private final ImagesPagerView mView;
    private final List<Photo> mPhotos;
    private final int mInitialPosition;
    private final StringResourcesHandler mResourcesHandler;

    public ImagesPagerPresenter(@NonNull ImagesPagerView view, @NonNull List<Photo> photos,
                                int initialPosition, @NonNull StringResourcesHandler resourcesHandler) {
        mView = view;
        mPhotos = photos;
        mInitialPosition = initialPosition;
        mResourcesHandler = resourcesHandler;
    }

    public void init(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            return;
        }
        mView.showPhotos(mPhotos);
        mView.showCurrentPhoto(mInitialPosition);
        showTitle(mInitialPosition);
    }

    public void onPageSelected(int position) {
        showTitle(position);
    }

    private void showTitle(int position) {
        String title = mResourcesHandler.getString(R.string.images_title, position + 1, mPhotos.size());
        mView.setTitle(title);
    }
}
