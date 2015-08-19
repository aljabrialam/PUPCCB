package pupccb.solutionsresource.com.helper;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.helper.communicator.AttachmentCommunicator;
import pupccb.solutionsresource.com.helper.communicator.OfflineCommunicator;
import pupccb.solutionsresource.com.model.FileAttachment;

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


    protected class processTicket extends AsyncTask<String, Void,String>{
        private Activity activity;
        private String ticket_id;
        private String user_id;
        private FileAttachment fileAttachment;
        private Controller.MethodTypes methodTypes;

        public processTicket(Activity activity, String ticket_id, String user_id, FileAttachment fileAttachment, Controller.MethodTypes methodTypes){
            this.activity = activity;
            this.ticket_id = ticket_id;
            this.fileAttachment = fileAttachment;
            this.methodTypes = methodTypes;
        }

        @Override
        protected void onPreExecute() {
            openDatabase(activity);
        }

        private long result = 0;

        @Override
        protected String doInBackground(String... params) {
            switch (methodTypes){
                case POST_FILE_ATTACHMENT:{
//                    try {result = offlineDatabaseHelper.insertDocument(fileAttachment, consultation_id, patient_id);}
//                    catch (Exception e) {
//                        Log.e("performConsultation POST_FILE_ATTACHMENT Exception", e.toString());}
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(String string) {
            closeDatabase();
            switch (methodTypes){

                case POST_FILE_ATTACHMENT:{
//                    try {fileAttachmentAdapter = new FileAttachmentAdapter(activity, R.layout.list_file_attachment,
//                            (ArrayList<FileAttachment>) offlineDatabaseHelper.getAllDocuments(consultation_id, patient_id));}
//                    catch (Exception e) {Log.e("performConsultation GET_FILE_ATTACHMENT_LIST Exception", e.toString());}
//                    if(result != -1 && fileAttachmentAdapter != null) {
                        controller.postFileAttachmentResult(false, null);
//                    } else {
//                        Toast.makeText(context, "Insert Documents failed", Toast.LENGTH_SHORT).show();
//                        consultationController.setError(null, methodTypes);
//                    }
                }
            }
        }
    }

}
