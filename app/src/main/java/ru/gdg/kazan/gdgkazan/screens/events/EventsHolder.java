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
import ru.gdg.kazan.gdgkazan.BuildConfig;
import ru.gdg.kazan.gdgkazan.R;
import ru.gdg.kazan.gdgkazan.models.Event;

/**
 * @author Valiev Timur.
 */
public class EventsHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.imageView)
    ImageView mEventImage;
    @BindView(R.id.tvEventName)
    TextView mEventName;

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

    public static EventsHolder buildForParent(@NonNull ViewGroup parent, @NonNull Context context,
                                              @NonNull EventsActionListener listener) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.li_event, parent, false);
        return new EventsHolder(view, context, listener);
    }

    public void bindView(@NonNull Event event) {
        mEvent = event;
        Picasso.with(mContext)
                .load(BuildConfig.API_BASE_URL + mEvent.getPreviewImage())
                .into(mEventImage);
        mEventName.setText(mEvent.getName());
    }


    public interface EventsActionListener {
        void onEventClick(@NonNull Event event);
    }
}
