package ru.gdg.kazan.gdgkazan.screens.images;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.gdg.kazan.gdgkazan.R;
import ru.gdg.kazan.gdgkazan.models.GsonHolder;
import ru.gdg.kazan.gdgkazan.models.Photo;
import ru.gdg.kazan.gdgkazan.utils.ViewUtils;

/**
 * @author Artur Vasilov
 */
public class ImagesPagerActivity extends AppCompatActivity {

    private static final String PHOTOS_KEY = "photos";
    private static final String POSITION_KEY = "photo_position";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.pager)
    ViewPager mViewPager;

    public static void start(@NonNull Activity activity, @NonNull List<Photo> photos, int position) {
        Intent intent = new Intent(activity, ImagesPagerActivity.class);
        intent.putExtra(PHOTOS_KEY, GsonHolder.getGson().toJson(photos));
        intent.putExtra(POSITION_KEY, position);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_images_pager);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        mToolbar.setNavigationOnClickListener(view -> onBackPressed());

        mToolbar.post(() -> {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mToolbar.getLayoutParams();
            params.topMargin = ViewUtils.getStatusBarHeight(this);
            mToolbar.setLayoutParams(params);
        });

        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setPageTransformer(true, new ImagePageTransformer());

        String photosJson = getIntent().getStringExtra(PHOTOS_KEY);
        List<Photo> photos = GsonHolder.getGson().fromJson(photosJson, new TypeToken<List<Photo>>() {
        }.getType());
        mViewPager.setAdapter(new ImagesPagerAdapter(getSupportFragmentManager(), photos));

        int position = getIntent().getIntExtra(POSITION_KEY, 0);
        mViewPager.setCurrentItem(position);
    }

}
