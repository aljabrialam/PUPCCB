package pupccb.solutionsresource.com.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.OnItemClickListener;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.helper.BaseHelper;

/**
 *
 */
public class CreateMaterialDialog {

    private Activity activity;
    private Holder holder;
    private Communicator communicator;
    private int requestCode;

    public CreateMaterialDialog(Activity activity, String title, String cancel, String confirm,
                                Holder holder, int gravity, boolean expanded, boolean cancelable,
                                int requestCode, String filePath) {

        this.activity = activity;
        this.holder = holder;
        this.requestCode = requestCode;
        this.communicator = (Communicator) activity;
        TextView textViewTitle, textViewClose, headerClose, textViewConfirm;
        final DialogPlus dialog = DialogPlus.newDialog(activity)
                .setContentHolder(holder)
                .setHeader(R.layout.header)
                .setFooter(R.layout.footer)
                .setCancelable(cancelable)
                .setGravity(gravity)
                .setExpanded(expanded)
                .setContentWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOnClickListener(newCommentDialogListener)
                .setOnDismissListener(dismissListener)
                .setOnCancelListener(cancelListener)
                .create();
        textViewTitle = (TextView) this.holder.getHeader().findViewById(R.id.title);
        headerClose = (TextView) this.holder.getHeader().findViewById(R.id.close);
        textViewClose = (TextView) this.holder.getFooter().findViewById(R.id.footer_close_button);
        textViewConfirm = (TextView) this.holder.getFooter().findViewById(R.id.footer_confirm_button);
        textViewTitle.setText(title);
        textViewClose.setText(cancel);
        textViewConfirm.setText(confirm);
        dialog.show();

        if(RequestCodes.PREVIEW_ATTACHMENT == requestCode || RequestCodes.ANNOUNCEMENT_DETAIL == requestCode
                || RequestCodes.TICKET_DETAILS == requestCode){
            headerClose.setVisibility(View.VISIBLE);
            textViewConfirm.setVisibility(View.GONE);
            textViewClose.setVisibility(View.GONE);
            communicator.MaterialDialogResponse(requestCode, holder, filePath);
        } else {
            communicator.MaterialDialogResponse(RequestCodes.TEXT_COUNTER, holder, filePath);
        }
    }

    OnClickListener newCommentDialogListener = new OnClickListener() {
        @Override
        public void onClick(DialogPlus dialog, View view) {
            switch (view.getId()) {
                case R.id.footer_confirm_button:
                    communicator.MaterialDialogResponse(requestCode, holder, "");
                    break;
                case R.id.footer_close_button:
                    dialog.dismiss();
                    break;
                case R.id.textViewAddAttachment:
                    communicator.MaterialDialogResponse(RequestCodes.ADD_ATTACHMENT, holder, "");
                    break;
                case R.id.close:
                    dialog.dismiss();
                    break;
            }
        }
    };


    OnDismissListener dismissListener = new OnDismissListener() {
        @Override
        public void onDismiss(DialogPlus dialog) {
            dialog.dismiss();
        }
    };

    OnCancelListener cancelListener = new OnCancelListener() {
        @Override
        public void onCancel(DialogPlus dialog) {
            dialog.dismiss();
        }
    };

    public interface Communicator{
        void MaterialDialogResponse(int requestCode, Holder holder, String filePath);
    }
}
