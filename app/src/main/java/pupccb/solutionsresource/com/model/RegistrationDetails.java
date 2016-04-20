package pupccb.solutionsresource.com.model;

/**
 * Created by User on 8/14/2015.
 */
public class RegistrationDetails {

    private String first_name, last_name, contact_number, email, password, gender;

    public RegistrationDetails(String first_name, String last_name, String contact_number, String email, String password,
                               String gender) {
        setFirst_name(first_name);
        setLast_name(last_name);
        setContact_number(contact_number);
        setEmail(email);
        setPassword(password);
        setGender(gender);
    }

    public String getFirst_name() {
        return first_name != null ? first_name.trim() : "";
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name != null ? first_name.trim() : "";

    }

    public String getLast_name() {
        return last_name != null ? last_name.trim() : "";
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name != null ? last_name.trim() : "";
    }

    public String getContact_number() {
        return contact_number != null ? contact_number.trim() : "";
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number != null ? contact_number.trim() : "";
    }

    public String getEmail() {
        return email != null ? email.trim() : "";
    }

    public void setEmail(String email) {
        this.email = email != null ? email.trim() : "";
    }

    public String getPassword() {
        return password != null ? password.trim() : "";
    }

    public void setPassword(String password) {
        this.password = password != null ? password.trim() : "";
    }
    public void setGender(String gender){
        this.gender = gender != null ? gender.trim() : "";
    }
    public String getGender(){
        return gender != null? gender.trim() : "";
    }
}
