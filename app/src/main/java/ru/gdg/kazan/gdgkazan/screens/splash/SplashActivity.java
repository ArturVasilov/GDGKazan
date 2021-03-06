package ru.gdg.kazan.gdgkazan.screens.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;
import ru.gdg.kazan.gdgkazan.R;
import ru.gdg.kazan.gdgkazan.app.Analytics;
import ru.gdg.kazan.gdgkazan.screens.events.EventsActivity;
import ru.gdg.kazan.gdgkazan.service.FCMService;
import ru.gdg.kazan.gdgkazan.utils.ThemeUtils;

/**
 * @author Artur Vasilov
 */
public class SplashActivity extends AppCompatActivity implements SplashView {

    @BindView(R.id.loadingImage)
    View mLoadingImage;

    @BindView(R.id.refreshLayout)
    View mRefreshLayout;

    private SplashPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ThemeUtils.obtainThemeFromConfig());
        setContentView(R.layout.ac_splash);

        if (savedInstanceState == null) {
            Analytics.logSplashScreenStarted();
            boolean showFromPush = getIntent().getBooleanExtra(FCMService.SHOW_FROM_PUSH_KEY, false);
            if (showFromPush) {
                int eventId = getIntent().getIntExtra(FCMService.EVENT_ID_PUSH_KEY, 0);
                int notificationId = getIntent().getIntExtra(FCMService.NOTIFICATION_ID_KEY, 0);
                if (eventId >= 0 && notificationId >= 0) {
                    Analytics.logNotificationClick(eventId, notificationId);
                }
            }
        }
        ButterKnife.bind(this);

        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(this, getSupportLoaderManager());
        mPresenter = new SplashPresenter(this, lifecycleHandler);
        mPresenter.init();
    }

    @Override
    public void showLoading() {
        mLoadingImage.setVisibility(View.VISIBLE);
        mRefreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        mLoadingImage.setVisibility(View.GONE);
        mRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEvents() {
        if (getIntent() != null && getIntent().hasExtra(FCMService.EVENT_ID_PUSH_KEY)) {
            EventsActivity.startEventPush(this, getIntent().getIntExtra(FCMService.EVENT_ID_PUSH_KEY, -1));
        } else {
            EventsActivity.start(this);
        }
        supportFinishAfterTransition();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.refreshImage)
    public void onRefreshClick() {
        mPresenter.reload();
    }
}
