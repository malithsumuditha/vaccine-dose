package tm;

/**
 * @author : MalithHP <malithsumuditha11@gmail.com>
 * @since : 9/18/2021
 **/
public class ViewAllPersonsTM {
    private String id;
    private String name;
    private String address;
    private String contact;
    private String nic;
    private String gender;
    private String registerTime;

    public ViewAllPersonsTM() {

    }

    public ViewAllPersonsTM(String id, String name, String address, String contact, String nic, String gender, String registerTime) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.nic = nic;
        this.gender = gender;
        this.registerTime = registerTime;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }
}
