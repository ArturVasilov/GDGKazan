package ru.gdg.kazan.gdgkazan.screens.images;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
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
public class ImagesPagerActivity extends AppCompatActivity implements ImagesPagerView, ViewPager.OnPageChangeListener {

    private static final String PHOTOS_KEY = "photos";
    private static final String POSITION_KEY = "photo_position";

    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.pager)
    ViewPager mViewPager;

    private ImagesPagerPresenter mPresenter;

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

        mAppBar.post(() -> {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mAppBar.getLayoutParams();
            params.topMargin = ViewUtils.getStatusBarHeight(this);
            mAppBar.setLayoutParams(params);
        });

        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setPageTransformer(true, new ImagePageTransformer());

        String photosJson = getIntent().getStringExtra(PHOTOS_KEY);
        List<Photo> photos = GsonHolder.getGson().fromJson(photosJson, new TypeToken<List<Photo>>() {
        }.getType());
        int position = getIntent().getIntExtra(POSITION_KEY, 0);

        mPresenter = new ImagesPagerPresenter(this, photos, position, this::getString);
        mPresenter.init(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void onPause() {
        mViewPager.removeOnPageChangeListener(this);
        super.onPause();
    }

    @Override
    public void showPhotos(@NonNull List<Photo> photos) {
        mViewPager.setAdapter(new ImagesPagerAdapter(getSupportFragmentManager(), photos));
    }

    @Override
    public void showCurrentPhoto(int position) {
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void setTitle(@NonNull String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // Do nothing
    }

    @Override
    public void onPageSelected(int position) {
        mPresenter.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // Do nothing
    }
}
