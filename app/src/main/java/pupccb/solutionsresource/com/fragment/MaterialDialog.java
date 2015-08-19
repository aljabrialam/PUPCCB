package pupccb.solutionsresource.com.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;

import pupccb.solutionsresource.com.helper.communicator.OfflineCommunicator;
import pupccb.solutionsresource.com.util.Dialog;

/**
 * Created by User on 8/28/2015.
 */
public class MaterialDialog extends DialogFragment {


    public Dialog.Builder materialDialog;
    private OfflineCommunicator offlineCommunicator;
    private String message;
    private int drawable = 0;

    private Button positiveButton, neutralButton, negativeButton;

    public MaterialDialog() {

    }

    public MaterialDialog(String message, int drawable) {
        this.message = message;
        this.drawable = drawable;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        offlineCommunicator = (OfflineCommunicator) getActivity();

        materialDialog = new Dialog.Builder(getActivity());
        materialDialog.setPrimaryHeaderImageResource(drawable)
                .setOnPrimaryButtonClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                    }

                }, true) // false if you don't want to dismiss the Dialog after the onClick is completed.

                .setOnSecondaryButtonClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                    }

                }, true)
                .setTitle(message)
                .create().show();

        Dialog alertDialog = materialDialog.create();
        alertDialog.show();
        setCancelable(false);
        return alertDialog;
    }


}
