package ru.gdg.kazan.gdgkazan.screens.event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.gdg.kazan.gdgkazan.R;
import ru.gdg.kazan.gdgkazan.models.Event;

/**
 * @author Artur Vasilov
 */
public class EventActivity extends AppCompatActivity {

    private static final String EVENT_KEY = "event";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static void start(@NonNull Activity activity, @NonNull Event event) {
        Intent intent = new Intent(activity, EventActivity.class);
        intent.putExtra(EVENT_KEY, event);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_event);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        Event event = (Event) getIntent().getSerializableExtra(EVENT_KEY);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(event.getName());
        }
    }
}
