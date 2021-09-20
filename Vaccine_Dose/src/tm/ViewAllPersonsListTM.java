package tm;

/**
 * @author : MalithHP <malithsumuditha11@gmail.com>
 * @since : 9/19/2021
 **/
public class ViewAllPersonsListTM {
    private String id;
    private String name;
    private String gender;


    public ViewAllPersonsListTM() {
    }

    public ViewAllPersonsListTM(String id, String name,String gender) {
        this.id = id;
        this.name = name;
        this.gender=gender;
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
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.id = gender;
    }

    @Override
    public String toString() {
        return "   "+ id + "    " +name ;
    }
}
