package ru.gdg.kazan.gdgkazan.screens.images;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ru.gdg.kazan.gdgkazan.models.Photo;

/**
 * @author Artur Vasilov
 */
public class ImagesPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Photo> mPhotos;

    public ImagesPagerAdapter(@NonNull FragmentManager fm, @NonNull List<Photo> photos) {
        super(fm);
        mPhotos = photos;
    }

    @Override
    public Fragment getItem(int position) {
        return ImageFragment.newInstance(mPhotos.get(position));
    }

    @Override
    public int getCount() {
        return mPhotos.size();
    }
}
