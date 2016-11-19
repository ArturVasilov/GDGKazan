package ru.gdg.kazan.gdgkazan.app;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import ru.arturvasilov.rxloader.stubs.StubAction0;
import ru.gdg.kazan.gdgkazan.R;
import rx.functions.Action0;

/**
 * @author Artur Vasilov
 */
public final class PicassoTools {

    private static final int CACHE_SIZE_BYTES = 50 * 1024 * 1024; //50 megabytes

    private PicassoTools() {
    }

    public static void initPicasso(@NonNull Context context) {
        Cache cache = new Cache(CacheDir.getCacheDir(), CACHE_SIZE_BYTES);
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .build();
        Downloader downloader = new OkHttp3Downloader(client);
        Picasso picasso = new Picasso.Builder(context).downloader(downloader).build();
        try {
            Picasso.setSingletonInstance(picasso);
        } catch (Exception ignored) {
            //Picasso instance was already set
        }
    }

    public static void downloadOffline(@NonNull String url, @DrawableRes int placeholder, @NonNull ImageView imageView) {
        final Reference<ImageView> imageViewReference = new WeakReference<>(imageView);
        Picasso.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.image_background)
                .error(R.drawable.image_background)
                .noFade()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView, new PicassoCallback(
                        () -> {
                            if (imageViewReference.get() != null) {
                                Picasso.with(imageViewReference.get().getContext())
                                        .load(url)
                                        .placeholder(placeholder)
                                        .error(placeholder)
                                        .noFade()
                                        .into(imageViewReference.get());
                            }
                        })
                );
    }

    public static class PicassoCallback implements Callback {

        private final Action0 mOnSuccess;
        private final Action0 mOnError;

        public PicassoCallback(@NonNull Action0 onError) {
            this(new StubAction0(), onError);
        }

        public PicassoCallback(@NonNull Action0 onSuccess, @NonNull Action0 onError) {
            mOnSuccess = onSuccess;
            mOnError = onError;
        }

        @Override
        public void onSuccess() {
            mOnSuccess.call();
        }

        @Override
        public void onError() {
            mOnError.call();
        }
    }

}
