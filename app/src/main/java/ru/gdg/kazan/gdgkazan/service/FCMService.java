package ru.gdg.kazan.gdgkazan.service;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import ru.arturvasilov.rxloader.RxUtils;
import ru.arturvasilov.sqlite.core.Where;
import ru.arturvasilov.sqlite.rx.RxSQLite;
import ru.gdg.kazan.gdgkazan.R;
import ru.gdg.kazan.gdgkazan.app.Analytics;
import ru.gdg.kazan.gdgkazan.models.database.EventSubscriptionsTable;
import ru.gdg.kazan.gdgkazan.repository.EventsRepository;
import ru.gdg.kazan.gdgkazan.repository.RepositoryProvider;
import ru.gdg.kazan.gdgkazan.repository.preferences.KeyValueStorage;
import ru.gdg.kazan.gdgkazan.screens.splash.SplashActivity;
import ru.gdg.kazan.gdgkazan.utils.TextUtils;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class FCMService extends FirebaseMessagingService {

    public static final String EVENT_ID_PUSH_KEY = "event_id_push";
    public static final String SHOW_FROM_PUSH_KEY = "show_from_push";

    public static final String NOTIFICATION_ID_KEY = "notification_id";
    public static final String EVENT_ID_KEY = "event_id";

    private static final String TITLE = "title";
    private static final String BODY = "body";
    private static final String OPEN_EVENT_PAGE = "open_event_page";

    private static final String SPECIAL_ACTION = "special_action";

    private static final String SPECIAL_ACTION_UPDATE_DATA = "update_data";

    private NotificationManagerCompat mNotificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = NotificationManagerCompat.from(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData() == null) {
            return;
        }

        Map<String, String> pushParams = remoteMessage.getData();

        if (pushParams.containsKey(SPECIAL_ACTION)) {
            proceedSpecialAction(pushParams.get(SPECIAL_ACTION));
            return;
        }

        String title = pushParams.get(TITLE);
        String body = pushParams.get(BODY);
        int notificationId;
        try {
            notificationId = Integer.parseInt(pushParams.get(NOTIFICATION_ID_KEY));
        } catch (Exception e) {
            notificationId = 0;
        }

        int eventId;
        try {
            eventId = Integer.parseInt(pushParams.get(EVENT_ID_KEY));
        } catch (Exception e) {
            eventId = -1;
        }

        if (eventId >= 0 && notificationId >= 0) {
            Analytics.logNotificationReceived(eventId, notificationId);
        } else if (notificationId >= 0) {
            Analytics.logNotificationReceived(notificationId);
        }

        if (eventId < 0) {
            showNotification(title, body, notificationId, eventId, false);
            return;
        }

        final int finalNotificationId = notificationId;
        final int finalEventId = eventId;
        final boolean openEventPage = Boolean.parseBoolean(pushParams.get(OPEN_EVENT_PAGE));

        Where where = Where.create().equalTo(EventSubscriptionsTable.EVENT_ID, eventId);
        RxSQLite.get().querySingle(EventSubscriptionsTable.TABLE, where)
                .compose(RxUtils.async())
                .subscribe(
                        eventSubscription -> {
                            if (eventSubscription.isSubscribed()) {
                                showNotification(title, body, finalNotificationId, finalEventId, openEventPage);
                            } else {
                                Analytics.logIgnoreNotification(finalEventId, finalNotificationId);
                            }
                        },
                        throwable -> {
                        });
    }

    private void showNotification(@NonNull String title, @NonNull String body,
                                  int notificationId, int eventId, boolean showEventPage) {
        Analytics.logShowNotification(eventId, notificationId);

        Intent intent = new Intent(this, SplashActivity.class);
        intent.putExtra(SHOW_FROM_PUSH_KEY, true);
        intent.putExtra(NOTIFICATION_ID_KEY, notificationId);
        intent.putExtra(EVENT_ID_KEY, eventId);
        if (showEventPage && eventId >= 0) {
            intent.putExtra(EVENT_ID_PUSH_KEY, eventId);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0, new Intent[]{intent}, PendingIntent.FLAG_ONE_SHOT);

        Intent cancelIntent = new Intent(PushCancelReceiver.ACTION_CANCEL);
        cancelIntent.putExtra(NOTIFICATION_ID_KEY, notificationId);
        cancelIntent.putExtra(EVENT_ID_KEY, eventId);
        PendingIntent deleteIntent = PendingIntent.getBroadcast(this, 0, cancelIntent, 0);

        NotificationCompat.BigTextStyle notificationStyle = new NotificationCompat.BigTextStyle()
                .setBigContentTitle(title)
                .bigText(body);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(notificationStyle)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setDeleteIntent(deleteIntent)
                .setSmallIcon(R.mipmap.ic_launcher);

        mNotificationManager.notify(notificationId, builder.build());
    }

    private void proceedSpecialAction(@NonNull String action) {
        if (!TextUtils.equals(SPECIAL_ACTION_UPDATE_DATA, action)) {
            return;
        }

        //TODO : update events

        final EventsRepository eventsRepository = RepositoryProvider.provideEventsRepository();
        final KeyValueStorage keyValueStorage = RepositoryProvider.provideKeyValueStorage();
        final String beforeUrl = keyValueStorage.getString(KeyValueStorage.EVENTS_URL);

        RepositoryProvider.provideGeneralRepository()
                .config()
                .take(1)
                .flatMap(o -> {
                    Analytics.logConfigSuccess();

                    String newUrl = keyValueStorage.getString(KeyValueStorage.EVENTS_URL);
                    if (TextUtils.equals(newUrl, beforeUrl) && eventsRepository.hasLocalEvents()) {
                        return Observable.just(o);
                    }
                    Analytics.logLoadingEvents();
                    return eventsRepository.fetchEvents();
                })
                .subscribe(
                        o -> {
                        },
                        throwable -> {
                        });

    }

}
