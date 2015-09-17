package pupccb.solutionsresource.com.model;

/**
 * Created by User on 8/5/2015.
 */
public class TicketInfo {
    public String status;
    public String title;
    public String assignTo;
    public int color;

    public TicketInfo(String status, String title, String assignTo) {
        this.status = status;
        this.title = title;
        this.assignTo =assignTo;
//        this.color = color;
    }

    public String getStatus() {
        return status != null ? status.trim() : "";
    }

    public void setStatus(String status) {
        this.status = status != null ? status.trim() : "";
    }

    public String getTitle() {
        return title!= null ? title.trim() : "";
    }

    public void setTitle(String title) {
        this.title = title != null ? title.trim() : "";
    }

    public String getAssignTo() {
        return assignTo != null ? assignTo.trim() : "";
    }

    public void setAssignTo(String assignTo) {
        this.assignTo = assignTo != null ? assignTo.trim() : "";
    }
}
