package pupccb.solutionsresource.com.model;

/**
 * Created by User on 8/5/2015.
 */
public class Ticket {
    public String status;
    public String title;
    public String assignTo;

    public Ticket(String status, String title, String assignTo) {
        this.status = status;
        this.title = title;
        this.assignTo =assignTo;
    }
}
