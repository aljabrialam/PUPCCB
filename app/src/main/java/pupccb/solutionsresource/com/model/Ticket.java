package pupccb.solutionsresource.com.model;

import retrofit.mime.TypedFile;

/**
 * Created by User on 9/8/2015.
 */
public class Ticket {

    private String user_id, incident_date_time, agency, complainee, subject, incident_details;
    private TypedFile file;

    public Ticket(String user_id, String incident_date_time, String agency, String complainee, String subject, String incident_details, TypedFile file) {
        setUser_id(user_id);
        setIncident_date_time(incident_date_time);
        setSubject(subject);
        setAgency(agency);
        setComplainee(complainee);
        setIncident_details(incident_details);
        setFile(file);
    }

    public String getUser_id() {
        return user_id != null ? user_id.trim() : "";
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id != null ? user_id.trim() : "";
    }

    public String getIncident_date_time() {
        return incident_date_time != null ? incident_date_time.trim() : "";
    }

    public void setIncident_date_time(String incident_date_time) {
        this.incident_date_time = incident_date_time != null ? incident_date_time.trim() : "";
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
        return incident_details != null ? incident_details.trim() : "";
    }

    public void setIncident_details(String incident_details) {
        this.incident_details = incident_details != null ? incident_details.trim() : "";
    }

    public TypedFile getFile() {
        return file;
    }

    public void setFile(TypedFile file) {
        this.file = file;
    }
}
