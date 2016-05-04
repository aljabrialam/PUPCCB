package pupccb.solutionsresource.com.model;

/**
 * Created by User on 9/21/2015.
 */
public class Comment {

    String id, comment, user_id, commenter_role, created_at, ticket_id, attachment;

    public Comment(String id, String comment, String user_id, String commenter_role, String created_at, String ticket_id, String attchment) {
        setId(id);
        setComment(comment);
        setUser_id(user_id);
        setCommenter_role(commenter_role);
        setCreated_at(created_at);
        setTicket_id(ticket_id);
    }

    public String getId() {
        return id != null ? id.trim() : "";
    }

    public void setId(String id) {
        this.id = id != null ? id.trim() : "";
    }

    public String getComment() {
        return comment != null ? comment.trim() : "";
    }

    public void setComment(String comment) {
        this.comment = comment != null ? comment.trim() : "";
    }

    public String getUser_id() {
        return user_id != null ? user_id.trim() : "";
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id != null ? user_id.trim() : "";
    }

    public String getCommenter_role() {
        return commenter_role != null ? commenter_role.trim() : "";
    }

    public void setCommenter_role(String commenter_role) {
        this.commenter_role = commenter_role != null ? commenter_role.trim() : "";
    }

    public String getCreated_at() {
        return created_at != null ? created_at.trim() : "";
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at != null ? created_at.trim() : "";
    }

    public String getTicket_id() {
        return ticket_id != null ? ticket_id.trim() : "";
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id != null ? ticket_id.trim() : "";
    }

    public String getAttachment() {
        return attachment != null ? attachment.trim() : "";
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment != null ? attachment.trim() : "";
    }
}
