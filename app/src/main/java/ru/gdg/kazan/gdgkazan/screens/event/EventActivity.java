package ru.gdg.kazan.gdgkazan.screens.event;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.appinvite.AppInviteInvitation;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.gdg.kazan.gdgkazan.R;
import ru.gdg.kazan.gdgkazan.app.Analytics;
import ru.gdg.kazan.gdgkazan.app.Env;
import ru.gdg.kazan.gdgkazan.models.Event;
import ru.gdg.kazan.gdgkazan.models.Link;
import ru.gdg.kazan.gdgkazan.models.Photo;
import ru.gdg.kazan.gdgkazan.screens.images.ImagesPagerActivity;
import ru.gdg.kazan.gdgkazan.utils.ThemeUtils;

/**
 * @author Artur Vasilov
 */
public class EventActivity extends AppCompatActivity implements EventView,
        PhotosAdapter.OnPhotoActionListener, LinksAdapter.OnLinkActionListener {

    private static final String EVENT_KEY = "event";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.scrollView)
    NestedScrollView mNestedScrollView;

    @BindView(R.id.eventDescriptionText)
    TextView mDescriptionText;

    @BindView(R.id.linksTitle)
    TextView mLinksTitle;

    @BindView(R.id.linksRecyclerView)
    RecyclerView mLinksRecyclerView;

    @BindView(R.id.photosTitle)
    TextView mPhotosTitle;

    @BindView(R.id.photosRecyclerView)
    RecyclerView mPhotosRecyclerView;

    private EventPresenter mPresenter;

    public static void start(@NonNull Activity activity, @NonNull Event event) {
        Intent intent = new Intent(activity, EventActivity.class);
        intent.putExtra(EVENT_KEY, event);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ThemeUtils.obtainThemeFromConfig());
        setContentView(R.layout.ac_event);
        if (savedInstanceState == null) {
            Analytics.logEventScreenStarted();
        }

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        mToolbar.setNavigationOnClickListener(view -> onBackPressed());

        mNestedScrollView.setNestedScrollingEnabled(false);
        mLinksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPhotosRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        Event event = (Event) getIntent().getSerializableExtra(EVENT_KEY);
        mPresenter = new EventPresenter(this, event);
        mPresenter.init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            mPresenter.onShareClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showTitle(@NonNull String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void showDescription(@NonNull String description) {
        mDescriptionText.setText(description);
    }

    @Override
    public void showLinks(@NonNull List<Link> links) {
        LinksAdapter adapter = new LinksAdapter(links, this);
        mLinksRecyclerView.setAdapter(adapter);
    }

    @Override
    public void hideLinks() {
        mLinksTitle.setVisibility(View.GONE);
        mLinksRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showPhotos(@NonNull List<Photo> photos) {
        PhotosAdapter adapter = new PhotosAdapter(photos, this);
        mPhotosRecyclerView.setAdapter(adapter);
    }

    @Override
    public void hidePhotos() {
        mPhotosTitle.setVisibility(View.GONE);
        mPhotosRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showPhotosPager(@NonNull List<Photo> photos, int selectedPosition) {
        ImagesPagerActivity.start(this, photos, selectedPosition);
    }

    @Override
    public void sendInvite(@NonNull String eventName, @NonNull String imageLink, @NonNull String deepLink) {
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message_format, eventName))
                .setDeepLink(Uri.parse(deepLink))
                .setCustomImage(Uri.parse(imageLink))
                .build();
        startActivityForResult(intent, 0);
    }

    @Override
    public void onPhotoClick(int selectedPosition) {
        mPresenter.onPhotoClick(selectedPosition);
    }

    @Override
    public void onLinkClick(@NonNull Link link) {
        mPresenter.onLinkClick(link);
    }

    @Override
    public void openLink(@NonNull String url) {
        Env.browseUrl(this, url);
    }
}
