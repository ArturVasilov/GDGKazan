package ru.gdg.kazan.gdgkazan.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import ru.gdg.kazan.gdgkazan.app.Analytics;

/**
 * @author Artur Vasilov
 */
public class PushCancelReceiver extends BroadcastReceiver {

    public static final String ACTION_CANCEL = "ru.gdg.kazan.gdgkazan.service.ACTION_CANCEL";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TextUtils.equals(ACTION_CANCEL, intent.getAction())) {
            int notificationId = intent.getIntExtra(FCMService.NOTIFICATION_ID_KEY, -1);
            int eventId = intent.getIntExtra(FCMService.EVENT_ID_KEY, -1);
            if (notificationId >= 0 && eventId >= 0) {
                Analytics.logNotificationDismissed(notificationId, eventId);
            }
        }
    }
}
