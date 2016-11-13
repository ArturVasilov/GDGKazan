package ru.gdg.kazan.gdgkazan.screens.events;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.gdg.kazan.gdgkazan.models.Event;

/**
 * @author Artur Vasilov
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsHolder> {

    private final List<Event> mEvents = new ArrayList<>();

    @NonNull
    private final EventsHolder.EventsActionListener mListener;

    public EventsAdapter(@NonNull EventsHolder.EventsActionListener listener) {
        mListener = listener;
    }

    @Override
    public EventsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return EventsHolder.buildForParent(parent, mListener);
    }

    @Override
    public void onBindViewHolder(EventsHolder holder, int position) {
        holder.bindView(mEvents.get(position));
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    public void changeDataSet(@NonNull List<Event> events) {
        mEvents.clear();
        mEvents.addAll(events);
        notifyDataSetChanged();
    }
}
