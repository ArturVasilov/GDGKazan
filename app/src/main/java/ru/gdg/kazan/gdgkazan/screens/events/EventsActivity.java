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
import ru.gdg.kazan.gdgkazan.models.Event;
import ru.gdg.kazan.gdgkazan.models.Photo;
import ru.gdg.kazan.gdgkazan.screens.event.EventActivity;
import ru.gdg.kazan.gdgkazan.screens.general.LoadingDialog;
import ru.gdg.kazan.gdgkazan.screens.general.LoadingView;
import ru.gdg.kazan.gdgkazan.screens.images.ImageActivity;

/**
 * @author Artur Vasilov
 */
public class EventsActivity extends AppCompatActivity implements EventsView, EventsHolder.EventsActionListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.eventsRecyclerView)
    RecyclerView mRecyclerView;

    private LoadingView mLoadingView;

    private EventsAdapter mEventsAdapter;

    public static void start(@NonNull Activity activity) {
        activity.startActivity(new Intent(activity, EventsActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_events);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mLoadingView = LoadingDialog.view(getSupportFragmentManager());

        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(this, getSupportLoaderManager());

        setupRecyclerView();

        EventsPresenter presenter = new EventsPresenter(this, lifecycleHandler);
        presenter.init();
    }

    private void setupRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEventsAdapter = new EventsAdapter(this);
        mRecyclerView.setAdapter(mEventsAdapter);
    }

    @Override
    public void showLoading() {
        mLoadingView.showLoading();
    }

    @Override
    public void hideLoading() {
        mLoadingView.hideLoading();
    }

    @Override
    public void showEvents(@NonNull List<Event> events) {
        mEventsAdapter.changeDataSet(events);
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
