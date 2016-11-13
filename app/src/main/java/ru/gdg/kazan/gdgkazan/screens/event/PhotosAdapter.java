package ru.gdg.kazan.gdgkazan.screens.event;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import ru.gdg.kazan.gdgkazan.R;
import ru.gdg.kazan.gdgkazan.models.Photo;

/**
 * @author Artur Vasilov
 */
public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoHolder> {

    private final List<Photo> mPhotos;

    private final OnPhotoActionListener mActionListener;

    private final View.OnClickListener mInternalListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();
            mActionListener.onPhotoClick(mPhotos, position);
        }
    };

    public PhotosAdapter(@NonNull List<Photo> photos, @NonNull OnPhotoActionListener actionListener) {
        mPhotos = Collections.unmodifiableList(photos);
        mActionListener = actionListener;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.li_photo, parent, false);
        return new PhotoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
        holder.bind(mPhotos.get(position));
        holder.itemView.setOnClickListener(mInternalListener);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    public static class PhotoHolder extends RecyclerView.ViewHolder {

        private ImageView mPhoto;

        public PhotoHolder(View itemView) {
            super(itemView);
            mPhoto = (ImageView) itemView;
        }

        public void bind(@NonNull Photo photo) {
            Picasso.with(itemView.getContext())
                    .load(photo.getUrl())
                    .noFade()
                    .placeholder(R.drawable.image_background_2)
                    .error(R.drawable.image_background_2)
                    .into(mPhoto);
            mPhoto.setContentDescription(photo.getTitle());
        }
    }

    public interface OnPhotoActionListener {
        void onPhotoClick(@NonNull List<Photo> photos, int selectedPosition);
    }

}
