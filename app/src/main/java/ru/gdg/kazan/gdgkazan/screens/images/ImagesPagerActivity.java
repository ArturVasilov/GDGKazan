package ru.gdg.kazan.gdgkazan.screens.images;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import ru.gdg.kazan.gdgkazan.models.Photo;

/**
 * @author Artur Vasilov
 */
public class ImagesPagerActivity extends AppCompatActivity {

    private static final String PHOTOS_KEY = "photos";
    private static final String POSITION_KEY = "photo_position";

    public static void start(@NonNull Activity activity, @NonNull List<Photo> photos, int position) {
        Intent intent = new Intent(activity, ImagesPagerActivity.class);
        intent.putExtra(POSITION_KEY, position);
        activity.startActivity(intent);
    }

}
