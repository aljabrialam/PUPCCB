package pupccb.solutionsresource.com.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Richard on 21/02/2015.
 */
public class CreateYesNoDialog {
    private Activity activity;
    private Dialog dialog;
    private Boolean dismissOnCancel;
    private int requestCode;
    private Communicator communicator;
    public CreateYesNoDialog(Activity activity, Dialog dialog, String title, String message, Boolean cancelable, Boolean dismissOnCancel, String positive, String negative, int requestCode){
        this.activity = activity;
        this.dialog = dialog;
        this.dismissOnCancel = dismissOnCancel;
        this.requestCode = requestCode;
        this.communicator = (Communicator) activity;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(cancelable)
                .setPositiveButton(positive, dialogClickListener)
                .setNegativeButton(negative, dialogClickListener);
        builder.show();
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    if(dialog != null){
                        dialog.dismiss();
                    }
                    communicator.yesNoDialogPositiveResponse(requestCode);
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    dialogInterface.dismiss();
                    if(dismissOnCancel){
                        activity.finish();
                    }
                    break;
            }
        }
    };

    public interface Communicator {
        void yesNoDialogPositiveResponse(int requestCode);
    }
}
