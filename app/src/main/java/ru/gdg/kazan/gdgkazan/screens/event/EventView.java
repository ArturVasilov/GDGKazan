package ru.gdg.kazan.gdgkazan.screens.event;

import android.support.annotation.NonNull;

import java.util.List;

import ru.gdg.kazan.gdgkazan.models.Link;
import ru.gdg.kazan.gdgkazan.models.Photo;

/**
 * @author Artur Vasilov
 */
public interface EventView {

    void showTitle(@NonNull String title);

    void showDescription(@NonNull String description);

    void showLinks(@NonNull List<Link> links);

    void hideLinks();

    void showPhotos(@NonNull List<Photo> photos);

    void hidePhotos();

}
