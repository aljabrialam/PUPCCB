package pupccb.solutionsresource.com.helper.communicator;

import pupccb.solutionsresource.com.model.FileAttachment;

/**
 * Created by User on 8/17/2015.
 */
public interface OfflineCommunicator {
    void showBrowseFileDialog();
    void showBrowseFileDialogResult(FileAttachment fileAttachment);
}
