package pupccb.solutionsresource.com.helper.communicator;

import android.app.Activity;
import android.content.Context;

import pupccb.solutionsresource.com.helper.Controller;
import pupccb.solutionsresource.com.model.FileAttachment;

/**
 * Created by User on 8/26/2015.
 */
public interface AttachmentCommunicator {
    void postFileAttachment(Controller controller, Activity activity, String ticket_id, String user_id, FileAttachment fileAttachment, Controller.MethodTypes methodTypes);
    void getFileAttachmentList(Controller controller, Activity activity, String ticket_id, String user_id, Controller.MethodTypes methodTypes);
    void deleteFile(Controller controller, Activity activity, FileAttachment fileAttachment, Controller.MethodTypes methodTypes);
}
