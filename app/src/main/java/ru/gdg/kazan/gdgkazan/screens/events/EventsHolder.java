package ru.gdg.kazan.gdgkazan.screens.events;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.gdg.kazan.gdgkazan.R;
import ru.gdg.kazan.gdgkazan.models.Event;

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

    private Event mEvent;
    private Context mContext;

    @NonNull
    private EventsActionListener mListener;

    public EventsHolder(@NonNull View itemView, @NonNull Context context, @NonNull EventsActionListener listener) {
        super(itemView);
        mContext = context;
        mListener = listener;
        ButterKnife.bind(this, itemView);
    }

    @NonNull
    public static EventsHolder buildForParent(@NonNull ViewGroup parent, @NonNull Context context,
                                              @NonNull EventsActionListener listener) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.li_event, parent, false);
        return new EventsHolder(view, context, listener);
    }

    public void bindView(@NonNull Event event) {
        mEvent = event;
        Picasso.with(mContext)
                .load(event.getPreviewImage())
                .placeholder(R.drawable.image_background)
                .error(R.drawable.image_background)
                .into(mEventImage);
        mEventName.setText(mEvent.getName());
        mDescriptionText.setText(event.getPreviewDescription());

        itemView.setOnClickListener(view -> mListener.onEventClick(mEvent));
        mMoreText.setOnClickListener(view -> mListener.onEventClick(mEvent));
    }

    public interface EventsActionListener {
        void onEventClick(@NonNull Event event);
    }
}
