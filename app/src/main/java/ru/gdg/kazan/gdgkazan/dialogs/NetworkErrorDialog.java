package ru.gdg.kazan.gdgkazan.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import ru.gdg.kazan.gdgkazan.R;

/**
 * @author Daniel Serdyukov
 */
public class NetworkErrorDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity(), R.style.AppTheme_Dialog)
                .setMessage(R.string.error_network)
                .setPositiveButton(R.string.button_ok, null)
                .create();
    }

}
