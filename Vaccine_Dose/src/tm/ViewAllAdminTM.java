package tm;

/**
 * @author - Hw_Dulanjana
 * @date - 10/7/2021
 */
public class ViewAllAdminTM {
    private String id ;
    private String name;
    private String contact;
    private String nic;
    private String gender;
    private String email;

    public ViewAllAdminTM() {
    }

    public ViewAllAdminTM(String id, String name, String contact, String nic, String gender, String email) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.nic = nic;
        this.gender = gender;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
