package tm;

import java.sql.Blob;

/**
 * @author : MalithHP <malithsumuditha11@gmail.com>
 * @since : 10/7/2021
 **/
public class ViewAllPHITM {
    private String id;
    private String name;
    private String address;
    private String contact;
    private String nic;
    private String gender;
    private String city;
    private Blob blob;

    public ViewAllPHITM() {

    }

    public ViewAllPHITM(String id, String name, String address, String contact, String nic, String gender, String city, Blob blob) {

        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.nic = nic;
        this.gender = gender;
        this.city = city;
        this.blob = blob;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Blob getBlob() {
        return blob;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }
}
