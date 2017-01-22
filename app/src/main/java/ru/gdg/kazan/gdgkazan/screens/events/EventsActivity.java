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

import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;
import ru.gdg.kazan.gdgkazan.BuildConfig;
import ru.gdg.kazan.gdgkazan.R;
import ru.gdg.kazan.gdgkazan.app.Analytics;
import ru.gdg.kazan.gdgkazan.models.Event;
import ru.gdg.kazan.gdgkazan.models.Photo;
import ru.gdg.kazan.gdgkazan.screens.event.EventActivity;
import ru.gdg.kazan.gdgkazan.screens.images.ImageActivity;
import ru.gdg.kazan.gdgkazan.service.FCMService;
import ru.gdg.kazan.gdgkazan.utils.ThemeUtils;

/**
 * @author Artur Vasilov
 */
public class EventsActivity extends AppCompatActivity implements EventsView,
        EventsHolder.EventsActionListener, GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.eventsRecyclerView)
    RecyclerView mRecyclerView;

    private EventsAdapter mEventsAdapter;

    private EventsPresenter mPresenter;

    private GoogleApiClient mGoogleApiClient;

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
        setTheme(ThemeUtils.obtainThemeFromConfig());
        setContentView(R.layout.ac_events);
        if (savedInstanceState == null) {
            Analytics.logEventsScreenStarted();
        }
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(AppInvite.API)
                .enableAutoManage(this, this)
                .build();

        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(this, getSupportLoaderManager());

        setupRecyclerView();

        mPresenter = new EventsPresenter(this, lifecycleHandler);
        int eventId = -1;
        if (getIntent() != null && getIntent().hasExtra(FCMService.EVENT_ID_PUSH_KEY)) {
            eventId = getIntent().getIntExtra(FCMService.EVENT_ID_PUSH_KEY, -1);
        }
        mPresenter.init(eventId);

        if (eventId < 0) {
            handleAppInvite();
        }
    }

    private void setupRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEventsAdapter = new EventsAdapter(this);
        mRecyclerView.setAdapter(mEventsAdapter);
    }

    private void handleAppInvite() {
        AppInvite.AppInviteApi.getInvitation(mGoogleApiClient, this, false)
                .setResultCallback(
                        result -> {
                            if (result.getStatus().isSuccess()) {
                                Intent intent = result.getInvitationIntent();
                                String deepLink = AppInviteReferral.getDeepLink(intent);
                                try {
                                    int eventId = Integer.parseInt(deepLink.substring(BuildConfig.INVITE_ENDPOINT.length()));
                                    mPresenter.onAppInviteEvent(eventId);
                                } catch (Exception ignored) {
                                }
                            }
                        });

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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //ignore error
    }
}
