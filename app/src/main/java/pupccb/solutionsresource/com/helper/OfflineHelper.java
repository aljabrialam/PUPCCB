package pupccb.solutionsresource.com.helper;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pupccb.solutionsresource.com.adapter.FileAttachmentAdapter;
import pupccb.solutionsresource.com.helper.communicator.AttachmentCommunicator;
import pupccb.solutionsresource.com.model.FileAttachment;
import pupccb.solutionsresource.com.util.ToastMessage;

/**
 * Created by User on 8/17/2015.
 */
public class OfflineHelper extends BaseHelper implements AttachmentCommunicator {

    private Controller controller;

    @Override
    public void postFileAttachment(Controller controller, Activity activity, String ticket_id, String user_id, FileAttachment fileAttachment, Controller.MethodTypes methodTypes) {
        this.controller = controller;
        new processTicket(activity, ticket_id, user_id, fileAttachment, methodTypes).execute();
    }

    @Override
    public void getFileAttachmentList(Controller controller, Activity activity, String ticket_id, String user_id, Controller.MethodTypes methodTypes) {
        this.controller = controller;
        new processTicket(activity,ticket_id, user_id, null,  methodTypes).execute();
    }

    @Override
    public void deleteFile(Controller controller, Activity activity, FileAttachment fileAttachment, Controller.MethodTypes methodTypes) {
        this.controller = controller;
        new processTicket(activity, null, null, fileAttachment, methodTypes).execute();
    }

    protected class processTicket extends AsyncTask<String, Void, String> {
        private Activity activity;
        private String ticket_id;
        private String user_id;
        private FileAttachment fileAttachment;
        private Controller.MethodTypes methodTypes;
        private FileAttachmentAdapter fileAttachmentAdapter;
        private long result = 0;

        public processTicket(Activity activity, String ticket_id, String user_id, FileAttachment fileAttachment, Controller.MethodTypes methodTypes) {
            this.activity = activity;
            this.user_id = user_id;
            this.ticket_id = ticket_id;
            this.fileAttachment = fileAttachment;
            this.methodTypes = methodTypes;

        }

        @Override
        protected void onPreExecute() {
            openDatabase(activity);
        }

        @Override
        protected String doInBackground(String... params) {
            switch (methodTypes) {
                case POST_FILE_ATTACHMENT: {
                    try {
                        //result = offlineDatabaseHelper.insertDocument(fileAttachment, ticket_id, user_id);

                    } catch (Exception e) {
                        Log.e("POST_FILE_ATTACHMENT", e.toString());
                    }
                } case GET_FILE_ATTACHMENT_LIST:{
                    try {fileAttachmentAdapter = new FileAttachmentAdapter(activity, (ArrayList<FileAttachment>) offlineDatabaseHelper.getAllDocuments(ticket_id, user_id));
                    } catch (Exception e) {Log.e("performConsultation GET_FILE_ATTACHMENT_LIST Exception", e.toString());}
                    break;
                } case DELETE_FILE_ATTACHMENT:{
                    try {result = offlineDatabaseHelper.deleteDocuments(fileAttachment);}
                    catch (Exception e) {Log.e("performConsultation DELETE_FILE_ATTACHMENT Exception", e.toString());}
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String string) {
            closeDatabase();
            switch (methodTypes) {

                case POST_FILE_ATTACHMENT: {
                    try {
//                        fileAttachmentAdapter = new FileAttachmentAdapter(activity,
//                                (List<FileAttachment>) offlineDatabaseHelper.getAllDocuments(ticket_id, user_id));
                        //fileAttachmentAdapter = new FileAttachmentAdapter(activity, (ArrayList<FileAttachment>) fileAttachment);
                    } catch (Exception e) {
                        Log.e("GET_FILE_ATTACHMENT_LIST", e.toString());
                    }
                    //if (result != -1 && fileAttachmentAdapter != null) {
                        controller.postFileAttachmentResult(false, null);
//                    } else {
//                        new BaseHelper().toastMessage(activity, 2000, ToastMessage.MessageType.DANGER, "Insert Documents failed");
//                        controller.setError(null, methodTypes);
//                    }
                } case GET_FILE_ATTACHMENT_LIST:{
                    controller.getFileAttachmentListResult(false, fileAttachmentAdapter);
                    break;
                } case DELETE_FILE_ATTACHMENT:{
                    if(result != -1){
                        controller.deleteFileResult();
                    } else {
                        new BaseHelper().toastMessage(activity, 2000, ToastMessage.MessageType.DANGER, "Delete Document failed");
                        controller.setError(null, methodTypes);
                    }
                }
            }
        }
    }

}
