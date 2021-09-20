package tm;

/**
 * @author - Hw_Dulanjana
 * @date - 9/20/2021
 */
public class ViewRegVaccineTM {
    private String vcode;
    private String vname;
    private String mcountry;
    private String mcompany;

    public ViewRegVaccineTM() {
    }

    public ViewRegVaccineTM(String vcode, String vname, String mcountry, String mcompany) {
        this.vcode = vcode;
        this.vname = vname;
        this.mcountry = mcountry;
        this.mcompany = mcompany;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getMcountry() {
        return mcountry;
    }

    public void setMcountry(String mcountry) {
        this.mcountry = mcountry;
    }

    public String getMcompany() {
        return mcompany;
    }

    public void setMcompany(String mcompany) {
        this.mcompany = mcompany;
    }

    @Override
    public String toString() {
        return (vcode +"                    "+vname+ " ");
    }
}
