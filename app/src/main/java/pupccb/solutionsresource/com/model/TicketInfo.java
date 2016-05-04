package pupccb.solutionsresource.com.model;

import java.io.Serializable;

/**
 * Created by User on 8/5/2015.
 */
public class TicketInfo implements Serializable {

    public String id, ticket_id, incident_date_time, agency, complainee, subject, incident_details, status, date, attachment, assignee, updated_at;

    public TicketInfo(String id, String ticket_id, String incident_date_time, String agency, String complainee, String subject, String incident_details, String status, String date, String attachment, String assignee, String updated_at) {
        setId(id);
        setTicket_id(ticket_id);
        setIncident_date_time(incident_date_time);
        setAgency(agency);
        setComplainee(complainee);
        setSubject(subject);
        setIncident_details(incident_details);
        setStatus(status);
        setDate(date);
        setAssignee(assignee);
        setUpdated_at(updated_at);
        setAttachment(attachment);
    }

    public String getId() {
        return id != null ? id.trim() : "";
    }

    public void setId(String id) {
        this.id = id != null ? id.trim() : "";
    }

    public String getTicket_id() {
        return ticket_id != null ? ticket_id.trim() : "";
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id != null ? ticket_id.trim() : "";
    }

    public String getUpdated_at() {
        return updated_at != null ? updated_at.trim() : "";
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at != null ? updated_at.trim() : "";
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

    public String getStatus() {
        return status != null ? status.trim() : "";
    }

    public void setStatus(String status) {
        this.status = status != null ? status.trim() : "";
    }

    public String getDate() {
        return date != null ? date.trim() : "";
    }

    public void setDate(String date) {
        this.date = date != null ? date.trim() : "";
    }

    public String getAttachment() {
        return attachment != null ? attachment.trim() : "" ;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment != null ? attachment.trim() : "";
    }

    public String getAssignee() {
        return assignee != null ? assignee.trim() : "";
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee != null ? assignee.trim() : "";
    }
}
