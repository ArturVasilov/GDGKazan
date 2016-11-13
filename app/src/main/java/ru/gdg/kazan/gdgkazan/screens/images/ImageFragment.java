package ru.gdg.kazan.gdgkazan.screens.images;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.gdg.kazan.gdgkazan.R;
import ru.gdg.kazan.gdgkazan.app.PicassoTools;
import ru.gdg.kazan.gdgkazan.models.Photo;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * @author Artur Vasilov
 */
public class ImageFragment extends Fragment {

    private static final String PHOTO_KEY = "photo";

    @BindView(R.id.image)
    PhotoView mPhotoView;

    private PhotoViewAttacher mAttacher;

    @NonNull
    public static ImageFragment newInstance(@NonNull Photo photo) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putSerializable(PHOTO_KEY, photo);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_image, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mAttacher = new PhotoViewAttacher(mPhotoView);

        Photo photo = (Photo) getArguments().getSerializable(PHOTO_KEY);
        if (photo == null) {
            return;
        }

        Picasso.with(getActivity())
                .load(photo.getUrl())
                .noFade()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(mPhotoView, new PicassoTools.PicassoCallback(mAttacher::update,
                        () -> Picasso.with(getActivity())
                                .load(photo.getUrl())
                                .noFade()
                                .into(mPhotoView, new PicassoTools.PicassoCallback(mAttacher::update, mAttacher::update)))
                );
    }
}
