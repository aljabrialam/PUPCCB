package pupccb.solutionsresource.com.model;

import retrofit.mime.TypedFile;

/**
 * Created by User on 8/19/2015.
 */
public class FileAttachment {

    private String id, file_name, file_type, file_size, file_attachment_path, sync;
    private TypedFile file;

    public FileAttachment(String id, String file_name, String file_type, String file_size, String file_attachment_path, String sync, TypedFile file) {
        setId(id);
        setFile_name(file_name);
        setFile_type(file_type);
        setFile_size(file_size);
        setFile_attachment_path(file_attachment_path);
        setSync(sync);
        setFile(file);
    }

    public String getId() {
        return id != null ? id.trim() : "";
    }

    public String getFile_name() {
        return file_name != null ? file_name.trim() : "";
    }

    public String getFile_type() {
        return file_type != null ? file_type.trim() : "";
    }

    public String getFile_size() {
        return file_size != null ? file_size.trim() : "0";
    }

    public String getFile_attachment_path() {
        return file_attachment_path != null ? file_attachment_path.trim() : "";
    }

    public String getSync() {
        return sync != null ? sync.trim() : "";
    }

    public TypedFile getFile() {
        return file;
    }

    public void setId(String id) {
        this.id = id != null ? id.trim() : "";
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name != null ? file_name.trim() : "";
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type != null ? file_type.trim() : "";
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size != null ? file_size.trim() : "0";
    }

    public void setFile_attachment_path(String file_attachment_path) {
        this.file_attachment_path = file_attachment_path;
    }

    public void setSync(String sync) {
        this.sync = sync != null ? sync.trim() : "";
    }

    public void setFile(TypedFile file) {
        this.file = file;
    }
}
