package tm;

/**
 * @author : MalithHP <malithsumuditha11@gmail.com>
 * @since : 9/19/2021
 **/
public class ViewAllVaccinatedPersonsTM {
    private String id;
    private String name;
    private String age;
    private String location;
    private String regDateDose1;
    private String regDateDose2;
    private String person_id;

    public ViewAllVaccinatedPersonsTM() {
    }

    public ViewAllVaccinatedPersonsTM(String id, String name, String age, String location, String regDateDose1, String regDateDose2, String person_id) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.location = location;
        this.regDateDose1 = regDateDose1;
        this.regDateDose2 = regDateDose2;
        this.person_id = person_id;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRegDateDose1() {
        return regDateDose1;
    }

    public void setRegDateDose1(String regDateDose1) {
        this.regDateDose1 = regDateDose1;
    }

    public String getRegDateDose2() {
        return regDateDose2;
    }

    public void setRegDateDose2(String regDateDose2) {
        this.regDateDose2 = regDateDose2;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }
}
