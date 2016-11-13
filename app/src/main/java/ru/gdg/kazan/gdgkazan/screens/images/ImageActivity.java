package ru.gdg.kazan.gdgkazan.screens.images;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.gdg.kazan.gdgkazan.R;
import ru.gdg.kazan.gdgkazan.models.Photo;
import ru.gdg.kazan.gdgkazan.utils.ViewUtils;

/**
 * @author Artur Vasilov
 */
public class ImageActivity extends AppCompatActivity {

    private static final String PHOTO_KEY = "photo";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static void start(@NonNull Activity activity, @NonNull Photo photo) {
        Intent intent = new Intent(activity, ImageActivity.class);
        intent.putExtra(PHOTO_KEY, photo);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ac_image);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        mToolbar.setNavigationOnClickListener(view -> onBackPressed());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        mToolbar.post(() -> {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mToolbar.getLayoutParams();
            params.topMargin = ViewUtils.getStatusBarHeight(this);
            mToolbar.setLayoutParams(params);
        });

        if (savedInstanceState == null) {
            Photo photo = (Photo) getIntent().getSerializableExtra(PHOTO_KEY);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ImageFragment.newInstance(photo), ImageFragment.class.getName())
                    .commit();
        }
    }
}
