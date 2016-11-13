package ru.gdg.kazan.gdgkazan.screens.general;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ProgressBar;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.ButterKnife;
import ru.gdg.kazan.gdgkazan.R;

/**
 * @author Artur Vasilov
 */
public class LoadingDialog extends DialogFragment {

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    @NonNull
    public static LoadingView view(@NonNull FragmentManager fm) {
        return new LoadingDialogView(fm);
    }

    @NonNull
    public static LoadingView view(@NonNull Fragment fragment) {
        return view(fragment.getFragmentManager());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, getTheme());
        setCancelable(false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context themeContext = new ContextThemeWrapper(getActivity(), R.style.AppTheme_LoadingDialog);
        View view = View.inflate(themeContext, R.layout.dialog_loading, null);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ProgressBar progressBar = ButterKnife.findById(view, R.id.progress);
            @ColorInt int color = ContextCompat.getColor(getActivity(), R.color.colorProgressSemiTransparent);
            progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        }

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
    }

    private static class LoadingDialogView implements LoadingView {

        private final FragmentManager mFm;

        private final AtomicBoolean mWaitForHide;

        private LoadingDialogView(@NonNull FragmentManager fm) {
            mFm = fm;
            boolean shown = fm.findFragmentByTag(LoadingDialog.class.getName()) != null;
            mWaitForHide = new AtomicBoolean(shown);
        }

        @Override
        public void showLoading() {
            if (mWaitForHide.compareAndSet(false, true)) {
                if (mFm.findFragmentByTag(LoadingDialog.class.getName()) == null) {
                    DialogFragment dialog = new LoadingDialog();
                    dialog.show(mFm, LoadingDialog.class.getName());
                }
            }
        }

        @Override
        public void hideLoading() {
            if (mWaitForHide.compareAndSet(true, false)) {
                HANDLER.post(new HideTask(mFm));
            }
        }
    }

    private static class HideTask implements Runnable {

        private final Reference<FragmentManager> mFmRef;

        private int mAttempts = 10;

        public HideTask(@NonNull FragmentManager fm) {
            mFmRef = new WeakReference<>(fm);
        }

        @Override
        public void run() {
            HANDLER.removeCallbacks(this);
            final FragmentManager fm = mFmRef.get();
            if (fm != null) {
                final LoadingDialog dialog = (LoadingDialog) fm.findFragmentByTag(LoadingDialog.class.getName());
                if (dialog != null) {
                    dialog.dismissAllowingStateLoss();
                } else if (--mAttempts >= 0) {
                    HANDLER.postDelayed(this, 300);
                }
            }
        }
    }

}
