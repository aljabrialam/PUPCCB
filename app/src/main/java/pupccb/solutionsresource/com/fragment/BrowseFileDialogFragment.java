package pupccb.solutionsresource.com.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.helper.communicator.OfflineCommunicator;
import pupccb.solutionsresource.com.model.FileAttachment;
import pupccb.solutionsresource.com.util.RequestCodes;
import retrofit.mime.TypedFile;


/**

 */
@SuppressLint("ValidFragment")
public class BrowseFileDialogFragment extends DialogFragment {

    private OfflineCommunicator offlineCommunicator;
    private Button positiveButton, neutralButton, negativeButton;
    private TextView textViewFileUri;
    private String filePath;
    private TypedFile typedFile;
    private boolean logged_in;

    public BrowseFileDialogFragment() {

    }

    public BrowseFileDialogFragment(boolean logged_in) {
        this.logged_in = logged_in;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_browse, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
//                .setPositiveButton(logged_in ? getResources().getString(R.string.upload) : getString(R.string.attach_file), null)
                .setPositiveButton(getString(R.string.attach_file), null)
                .setNeutralButton(getResources().getString(R.string.browse), null)
                .setNegativeButton(getResources().getString(R.string.cancel), null)
                .setTitle(getResources().getString(R.string.add_attachment));

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        findViewById(view, alertDialog);
        setCancelable(false);
        return alertDialog;
    }

    private void findViewById(View view, AlertDialog alertDialog) {
        offlineCommunicator = (OfflineCommunicator) getActivity();

        positiveButton = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(buttonListener);
        positiveButton.setEnabled(false);
        neutralButton = alertDialog.getButton(Dialog.BUTTON_NEUTRAL);
        neutralButton.setOnClickListener(buttonListener);
        negativeButton = alertDialog.getButton(Dialog.BUTTON_NEGATIVE);
        negativeButton.setOnClickListener(buttonListener);

        textViewFileUri = (TextView) view.findViewById(R.id.textViewFileUri);
    }

    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == positiveButton) {
                if (typedFile.file().exists()) {
                    double bytes = typedFile.file().length();
                    double kilobytes = (bytes / 1024);
                    double megabytes = (kilobytes / 1024);
                    if (megabytes < 5) { // Limit file size to 5MB
                        offlineCommunicator.showBrowseFileDialogResult(
                                new FileAttachment(
                                        null,
                                        typedFile.fileName(),
                                        typedFile.mimeType(),
                                        String.valueOf(typedFile.length()),
                                        filePath,
                                        "false",
                                        typedFile));
                        getDialog().dismiss();
                    } else {
                        Toast.makeText(getActivity(), "The attached file exceeds the allowable size limit. (5MB)", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    filePath = null;
                    typedFile = null;
                    textViewFileUri.setText("");
                    positiveButton.setEnabled(false);

                    Toast.makeText(getActivity(), "File does not exist!", Toast.LENGTH_SHORT).show();
                }
            } else if (view == neutralButton) {
                attachFile();
            } else if (view == negativeButton) {
                getDialog().dismiss();
            }
        }
    };

    private void attachFile() {
        AttachmentDialogFragment dialogFragment = new AttachmentDialogFragment();
        dialogFragment.show(getFragmentManager(), "attachmentDialogFragment");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && (requestCode == RequestCodes.FILE_SELECT)) {
            Uri originalUri = data.getData();
            if (originalUri != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    // ExternalStorageProvider
                    if (isExternalStorageDocument(originalUri)) {
                        final String docId = DocumentsContract.getDocumentId(originalUri);
                        final String[] split = docId.split(":");
                        final String type = split[0];

                        if ("primary".equalsIgnoreCase(type)) {
                            attachFileResult(Environment.getExternalStorageDirectory() + "/" + split[1]);
                        }
                        // TODO handle non-primary volumes
                    }
                    // DownloadsProvider
                    else if (isDownloadsDocument(originalUri)) {
                        final String id = DocumentsContract.getDocumentId(originalUri);
                        final Uri contentUri = ContentUris.withAppendedId(
                                Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                        attachFileResult(getDataColumn(getActivity(), contentUri, null, null));
                    }
                    // MediaProvider
                    else if (isMediaDocument(originalUri)) {
                        final String docId = DocumentsContract.getDocumentId(originalUri);
                        final String[] split = docId.split(":");
                        final String type = split[0];

                        Uri contentUri = null;
                        if ("image".equals(type)) {
                            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        } else if ("video".equals(type)) {
                            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        } else if ("audio".equals(type)) {
                            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        }

                        final String selection = "_id=?";
                        final String[] selectionArgs = new String[]{
                                split[1]
                        };

                        attachFileResult(getDataColumn(getActivity(), contentUri, selection, selectionArgs));
                    } else {
                        attachFileResult(getRealPathFromURI(originalUri));
                    }
                } else {
                    attachFileResult(getRealPathFromURI(originalUri));
                }
            }
        }
    }

    private void attachFileResult(String filePath) {
        File file = new File(filePath);
        this.filePath = filePath;
        this.typedFile = new TypedFile(getMimeType(filePath), file);
        textViewFileUri.setText(file.getPath());
        positiveButton.setEnabled(true);
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param path file path based on uri
     * @return mime type in string format
     */
    public String getMimeType(String path) {
        String type = null;
        String[] splitPath = path.split("\\.");
        String extension = !MimeTypeMap.getFileExtensionFromUrl(path).equals("") ? MimeTypeMap.getFileExtensionFromUrl(path) : splitPath[splitPath.length != 0 ? splitPath.length - 1 : 0];
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        return type;
    }

    /**
     * @param contentURI data URI
     * @return file path
     */
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            result = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
            cursor.close();
        }
        return result;
    }
}
