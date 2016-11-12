package ru.gdg.kazan.gdgkazan.screens.event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.gdg.kazan.gdgkazan.R;
import ru.gdg.kazan.gdgkazan.models.Event;
import ru.gdg.kazan.gdgkazan.models.Link;
import ru.gdg.kazan.gdgkazan.models.Photo;

/**
 * @author Artur Vasilov
 */
public class EventActivity extends AppCompatActivity implements EventView {

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

    public static void start(@NonNull Activity activity, @NonNull Event event) {
        Intent intent = new Intent(activity, EventActivity.class);
        intent.putExtra(EVENT_KEY, event);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_event);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back_white);
        mToolbar.setNavigationOnClickListener(view -> onBackPressed());

        mNestedScrollView.setNestedScrollingEnabled(false);
        mLinksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPhotosRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        Event event = (Event) getIntent().getSerializableExtra(EVENT_KEY);
        EventPresenter presenter = new EventPresenter(this);
        presenter.init(event);
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
        LinksAdapter adapter = new LinksAdapter(links);
        mLinksRecyclerView.setAdapter(adapter);
    }

    @Override
    public void hideLinks() {
        mLinksTitle.setVisibility(View.GONE);
        mLinksRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showPhotos(@NonNull List<Photo> photos) {
        PhotosAdapter adapter = new PhotosAdapter(photos);
        mPhotosRecyclerView.setAdapter(adapter);
    }

    @Override
    public void hidePhotos() {
        mPhotosTitle.setVisibility(View.GONE);
        mPhotosRecyclerView.setVisibility(View.GONE);
    }
}
