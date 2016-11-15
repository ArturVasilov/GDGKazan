package ru.gdg.kazan.gdgkazan.service;

import com.google.firebase.iid.FirebaseInstanceIdService;

import ru.gdg.kazan.gdgkazan.repository.app.Analytics;

/**
 * @author Artur Vasilov
 */
public class FCMInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        Analytics.logTokenRefreshed();
    }

}
