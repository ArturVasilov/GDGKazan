package ru.gdg.kazan.gdgkazan.models;

/**
 * @author Artur Vasilov
 */
public class EventSubscription {

    private final int mEventId;
    private final boolean mIsSubscribed;

    public EventSubscription(int eventId, boolean isSubscribed) {
        mEventId = eventId;
        mIsSubscribed = isSubscribed;
    }

    public int getEventId() {
        return mEventId;
    }

    public boolean isSubscribed() {
        return mIsSubscribed;
    }
}
