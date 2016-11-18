package ru.gdg.kazan.gdgkazan.screens.events;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.arturvasilov.rxloader.RxUtils;
import ru.arturvasilov.sqlite.rx.RxSQLite;
import ru.gdg.kazan.gdgkazan.R;
import ru.gdg.kazan.gdgkazan.app.Analytics;
import ru.gdg.kazan.gdgkazan.app.PicassoTools;
import ru.gdg.kazan.gdgkazan.models.Event;
import ru.gdg.kazan.gdgkazan.models.EventSubscription;
import ru.gdg.kazan.gdgkazan.models.database.EventSubscriptionsTable;
import ru.gdg.kazan.gdgkazan.repository.RepositoryProvider;

/**
 * @author Artur Vasilov
 */
public class EventsHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.eventPreviewImage)
    ImageView mEventImage;

    @BindView(R.id.eventNameText)
    TextView mEventName;

    @BindView(R.id.eventDescriptionText)
    TextView mDescriptionText;

    @BindView(R.id.moreText)
    View mMoreText;

    @BindView(R.id.notificationsBar)
    View mNotificationsBar;

    @BindView(R.id.notificationsSwitcher)
    SwitchCompat mNotificationsSwitcher;

    private Event mEvent;

    @NonNull
    private final EventsActionListener mListener;

    public EventsHolder(@NonNull View itemView, @NonNull EventsActionListener listener) {
        super(itemView);
        mListener = listener;
        ButterKnife.bind(this, itemView);
    }

    @NonNull
    public static EventsHolder buildForParent(@NonNull ViewGroup parent, @NonNull EventsActionListener listener) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.li_event, parent, false);
        return new EventsHolder(view, listener);
    }

    public void bindView(@NonNull Event event) {
        mEvent = event;

        PicassoTools.downloadOffline(event.getPreviewImage(), R.drawable.image_background, mEventImage);

        mEventName.setText(mEvent.getName());
        mDescriptionText.setText(event.getPreviewDescription());

        itemView.setOnClickListener(view -> {
            Analytics.logEventCardClick(mEvent);
            mListener.onEventClick(mEvent);
        });

        mEventImage.setOnClickListener(view -> {
            Analytics.logEventLogoClick(mEvent);
            mListener.onImageClick(mEvent);
        });

        mMoreText.setOnClickListener(view -> {
            Analytics.logEventMoreButtonClick(mEvent);
            mListener.onEventClick(mEvent);
        });

        mNotificationsBar.setOnClickListener(view -> {
        });

        bindNotificationsSwitcher();
    }

    private void bindNotificationsSwitcher() {
        if (!mEvent.isSubscriptionPossible()) {
            mNotificationsBar.setVisibility(View.GONE);
            return;
        }

        mNotificationsBar.setVisibility(View.VISIBLE);

        RepositoryProvider.provideEventsRepository()
                .subscriptionForEvent(mEvent)
                .subscribe(
                        eventSubscription -> mNotificationsSwitcher.setChecked(eventSubscription.isSubscribed()),
                        throwable -> mNotificationsSwitcher.setChecked(false)
                );

        mNotificationsSwitcher.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                Analytics.logEventNotificationsSwitcherEnabled(mEvent);
            } else {
                Analytics.logEventNotificationsSwitcherDisabled(mEvent);
            }

            EventSubscription subscription = new EventSubscription(mEvent.getId(), isChecked);
            RxSQLite.get().insert(EventSubscriptionsTable.TABLE, subscription)
                    .compose(RxUtils.async())
                    .subscribe(
                            eventSubscription -> {
                            },
                            throwable -> mNotificationsSwitcher.setChecked(!isChecked)
                    );
        });
    }

    public interface EventsActionListener {

        void onEventClick(@NonNull Event event);

        void onImageClick(@NonNull Event event);
    }
}
