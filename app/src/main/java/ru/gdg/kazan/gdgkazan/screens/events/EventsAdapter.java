package ru.gdg.kazan.gdgkazan.screens.events;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import ru.gdg.kazan.gdgkazan.models.Event;

/**
 * @author Valiev Timur.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsHolder> {

    private List<Event> mEvents = Collections.emptyList();
    @NonNull
    private EventsHolder.EventsActionListener mListener;

    @NonNull
    private Context mContext;

    public EventsAdapter(@NonNull EventsHolder.EventsActionListener listener, @NonNull Context context) {
        mListener = listener;
        mContext = context;
    }

    @Override
    public EventsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return EventsHolder.buildForParent(parent, mContext, mListener);
    }

    @Override
    public void onBindViewHolder(EventsHolder holder, int position) {
        holder.bindView(mEvents.get(position));
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public void changeDataSet(List<Event> events) {
        mEvents = Collections.unmodifiableList(events);
        notifyDataSetChanged();
    }
}
