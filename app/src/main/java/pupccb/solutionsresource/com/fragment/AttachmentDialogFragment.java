package pupccb.solutionsresource.com.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.adapter.AttachmentAdapter;
import pupccb.solutionsresource.com.model.Attachment;
import pupccb.solutionsresource.com.util.RequestCodes;

/**
 * Created by
 */
public class AttachmentDialogFragment extends DialogFragment {

    private Activity activity;
    private Communicator communicator;
    private Uri uriSavedImage;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        activity = getActivity();
        communicator = (Communicator) activity;
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_attachment, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view)
                .setNegativeButton(getString(R.string.action_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDialog().dismiss();
                    }
                })
                .setTitle(getString(R.string.attachment_title));

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        findViewById(view);
        return alertDialog;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == RequestCodes.FILE_SELECT) {
            Intent intent = new Intent();
            intent.setData(uriSavedImage);
            communicator.onActivityResult(requestCode, resultCode, data != null ? data : intent);
            getDialog().dismiss();
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void findViewById(View view) {
        PackageManager packageManager = activity.getPackageManager();
        List<Attachment> yourIntentsList = new ArrayList<Attachment>();

        Intent allIntent;
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        allIntent = new Intent(Intent.ACTION_GET_CONTENT); //ACTION_OPEN_DOCUMENT
        allIntent = setAllIntentType(allIntent, true);
        allIntent.addCategory(Intent.CATEGORY_OPENABLE);
        //} else {
        //    allIntent = new Intent(Intent.ACTION_GET_CONTENT);
        //    allIntent = setAllIntentType(allIntent, false);
        //}
        List<ResolveInfo> listGall = packageManager.queryIntentActivities(allIntent, 0);
        for (ResolveInfo res : listGall) {
            //if(!res.activityInfo.packageName.equals("com.android.contacts")) { //temporary fix to remove contacts from selection list
            final Intent finalIntent = new Intent(allIntent);
            finalIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            yourIntentsList.add(new Attachment(res, finalIntent));
            //}
        }

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent = setCameraDirectory(cameraIntent);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(cameraIntent, 0);
        for (ResolveInfo res : listCam) {
            final Intent finalIntent = new Intent(cameraIntent);
            finalIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            yourIntentsList.add(new Attachment(res, finalIntent));
        }

        AttachmentAdapter attachmentAdapter = new AttachmentAdapter(activity, R.layout.attachment_selection, (ArrayList<Attachment>) yourIntentsList);
        ListView listView = (ListView) view.findViewById(R.id.container);
        listView.setAdapter(attachmentAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Attachment attachment = (Attachment) parent.getAdapter().getItem(position);
                startActivityForResult(attachment.getIntent(), RequestCodes.FILE_SELECT);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private Intent setAllIntentType(Intent allIntent, boolean isKitKat) {
        allIntent.setType("image/*");
        String[] mimetypes = {"image/*"};
        allIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        return allIntent;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private Intent setCameraDirectory(Intent cameraIntent) {
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), getString(R.string.app_name));
        imagesFolder.mkdirs();
        File[] listFiles = imagesFolder.listFiles();
        int counter = 0;
        for (File file : listFiles) {
            if (file.getName().endsWith(".jpg")) {
                counter++;
            }
        }
        File image = new File(imagesFolder, "image_" + counter + ".jpg");
        uriSavedImage = Uri.fromFile(image);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        return cameraIntent;
    }

    public interface Communicator {
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }
}
