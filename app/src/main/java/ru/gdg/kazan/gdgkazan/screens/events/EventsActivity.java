package ru.gdg.kazan.gdgkazan.screens.events;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;
import ru.gdg.kazan.gdgkazan.R;
import ru.gdg.kazan.gdgkazan.app.Analytics;
import ru.gdg.kazan.gdgkazan.models.Event;
import ru.gdg.kazan.gdgkazan.models.Photo;
import ru.gdg.kazan.gdgkazan.screens.event.EventActivity;
import ru.gdg.kazan.gdgkazan.screens.images.ImageActivity;
import ru.gdg.kazan.gdgkazan.service.FCMService;

/**
 * @author Artur Vasilov
 */
public class EventsActivity extends AppCompatActivity implements EventsView, EventsHolder.EventsActionListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.eventsRecyclerView)
    RecyclerView mRecyclerView;

    private EventsAdapter mEventsAdapter;

    public static void start(@NonNull Activity activity) {
        activity.startActivity(new Intent(activity, EventsActivity.class));
    }

    public static void startEventPush(@NonNull Activity activity, int eventId) {
        Intent intent = new Intent(activity, EventsActivity.class);
        intent.putExtra(FCMService.EVENT_ID_PUSH_KEY, eventId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_events);
        if (savedInstanceState == null) {
            Analytics.logEventsScreenStarted();
        }
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(this, getSupportLoaderManager());

        setupRecyclerView();

        EventsPresenter presenter = new EventsPresenter(this, lifecycleHandler);
        int eventId = -1;
        if (getIntent() != null && getIntent().hasExtra(FCMService.EVENT_ID_PUSH_KEY)) {
            eventId = getIntent().getIntExtra(FCMService.EVENT_ID_PUSH_KEY, -1);
        }
        presenter.init(eventId);
    }

    private void setupRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEventsAdapter = new EventsAdapter(this);
        mRecyclerView.setAdapter(mEventsAdapter);
    }

    @Override
    public void showEvents(@NonNull List<Event> events) {
        mEventsAdapter.changeDataSet(events);
    }

    @Override
    public void showEventScreen(@NonNull Event event) {
        EventActivity.start(this, event);
    }

    @Override
    public void onEventClick(@NonNull Event event) {
        EventActivity.start(this, event);
    }

    @Override
    public void onImageClick(@NonNull Event event) {
        ImageActivity.start(this, new Photo("", event.getPreviewImage()));
    }
}
