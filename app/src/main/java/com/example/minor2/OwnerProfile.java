package com.example.minor2;

public class OwnerProfile {
    private String Oname;
    private String Omobile;
    private String Oemail;
    private String Opassword;
    private String id;
    private String OimageUrl;

    public OwnerProfile() {
    }

    public OwnerProfile(String oname, String omobile, String oemail, String opassword,String id,String OimageUrl) {
        Oname = oname;
        Omobile = omobile;
        Oemail = oemail;
        Opassword = opassword;
        this.id = id;
        this.OimageUrl = OimageUrl;

    }


    public String getOimageUrl() {
        return OimageUrl;
    }

    public void setOimageUrl(String oimageUrl) {
        OimageUrl = oimageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOname() {
        return Oname;
    }

    public void setOname(String oname) {
        Oname = oname;
    }

    public String getOmobile() {

        return Omobile;
    }

    public void setOmobile(String omobile) {
        Omobile = omobile;
    }

    public String getOemail() {
        return Oemail;
    }

    public void setOemail(String oemail) {
        Oemail = oemail;
    }

    public String getOpassword() {
        return Opassword;
    }

    public void setOpassword(String opassword) {
        Opassword = opassword;
    }
}
