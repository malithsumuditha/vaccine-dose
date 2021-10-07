package tm;

import java.sql.Blob;

/**
 * @author : MalithHP <malithsumuditha11@gmail.com>
 * @since : 10/7/2021
 **/
public class ViewAllDoctorsTM {
    private String id;
    private String name;
    private String contact;
    private String nic;
    private String gender;
    private Blob blob;


    public ViewAllDoctorsTM() {

    }

    public ViewAllDoctorsTM(String id, String name, String contact, String nic, String gender, Blob blob) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.nic = nic;
        this.gender = gender;
        this.blob = blob;
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

    public Blob getBlob() {
        return blob;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
