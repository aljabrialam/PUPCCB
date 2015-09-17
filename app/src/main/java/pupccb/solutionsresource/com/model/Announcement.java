package pupccb.solutionsresource.com.model;

import java.io.Serializable;

/**
 * Created by User on 8/5/2015.
 */
public class Announcement implements Serializable {

    public String id, subject, message, updated_at;

    public Announcement(String id, String subject, String message, String updated_at) {
        setId(id);
        setSubject(subject);
        setMessage(message);
        setUpdated_at(updated_at);
    }

    public String getId() {
        return id != null ? id.trim() : "";
    }

    public void setId(String id) {
        this.id = id != null ? id.trim() : "";
    }

    public String getSubject() {
        return subject != null ? subject.trim() : "";
    }

    public void setSubject(String subject) {
        this.subject = subject != null ? subject.trim() : "";
    }

    public String getMessage() {
        return message != null ? message.trim() : "";
    }

    public void setMessage(String message) {
        this.message = message != null ? message.trim() : "";
    }

    public String getUpdated_at() {
        return updated_at != null ? updated_at.trim() : "";
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at != null ? updated_at.trim() : "";
    }
}
