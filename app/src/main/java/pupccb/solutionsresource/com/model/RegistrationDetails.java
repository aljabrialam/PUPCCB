package pupccb.solutionsresource.com.model;

/**
 * Created by User on 8/14/2015.
 */
public class RegistrationDetails {

    private String firstname, middlename, lasrname, contactnumber, emailaddress, password;

    public RegistrationDetails(String firstname, String middlename, String lasrname, String contactnumber, String emailaddress,String password) {
        setFirstname(firstname);
        setMiddlename(middlename);
        setLasrname(lasrname);
        setContactnumber(contactnumber);
        setEmailaddress(emailaddress);
        setPassword(password);
    }

    public String getFirstname() {
        return firstname != null ? firstname.trim() : "";
    }

    public String getMiddlename() {
        return middlename != null ? middlename.trim() : "";
    }

    public String getLasrname() {
        return lasrname != null ? lasrname.trim() : "";
    }

    public String getContactnumber() {
        return contactnumber != null ? contactnumber.trim() : "";
    }

    public String getEmailaddress() {
        return emailaddress != null ? emailaddress.trim() : "";
    }

    public String getPassword() {
        return password != null ? password.trim() : "";
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname != null ? firstname.trim() : "";
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename != null ? middlename.trim() : "";
    }

    public void setLasrname(String lasrname) {
        this.lasrname = lasrname != null ? lasrname.trim() : "";
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber != null ? contactnumber.trim() : "";
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress != null ? emailaddress.trim() : "";
    }

    public void setPassword(String password) {
        this.password = password != null ? password.trim() : "";
    }
}
