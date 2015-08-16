package pupccb.solutionsresource.com.model;

/**
 * Created by User on 8/5/2015.
 */
public class Ticket {
    public String status;
    public String title;
    public String assignTo;
    public int color;

    public Ticket(String status, String title, String assignTo, int color) {
        this.status = status;
        this.title = title;
        this.assignTo =assignTo;
        this.color = color;
    }
}
