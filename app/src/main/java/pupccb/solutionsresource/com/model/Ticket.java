package pupccb.solutionsresource.com.model;

/**
 * Created by User on 9/8/2015.
 */
public class Ticket {

    public String user_id, date_time, agency, complainee, subject, incident_details;

    public Ticket(String user_id, String date_time, String agency, String complainee, String subject, String incident_details) {
        setDate_time(date_time);
        setSubject(subject);
        setAgency(agency);
        setIncident_details(incident_details);
        setComplainee(complainee);
        setUser_id(user_id);
    }

    public String getUser_id() {
        return user_id != null ? user_id.trim() : "";
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id != null ? user_id.trim() : "";
    }

    public String getDate_time() {
        return date_time != null ? date_time.trim() : "";
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time != null ? date_time.trim() : "";
    }

    public String getAgency() {
        return agency != null ? agency.trim() : "";
    }

    public void setAgency(String agency) {
        this.agency = agency != null ? agency.trim() : "";
    }

    public String getComplainee() {
        return complainee != null ? complainee.trim() : "";
    }

    public void setComplainee(String complainee) {
        this.complainee = complainee != null ? complainee.trim() : "";
    }

    public String getSubject() {
        return subject != null ? subject.trim() : "";
    }

    public void setSubject(String subject) {
        this.subject = subject != null ? subject.trim() : "";
    }

    public String getIncident_details() {
        return incident_details;
    }

    public void setIncident_details(String incident_details) {
        this.incident_details = incident_details;
    }
}
