package com.example.minor2;

public class UserProfile {
    private String Sname;
    private String Smobile;
    private String Fname;
    private String Fmobile;
    private String Semail;
    private String Spassword;
    private String Scollege;
    private String SimageURL;

    public UserProfile() {
    }

    public UserProfile(String sname, String smobile, String fname, String fmobile, String semail, String spassword, String scollege, String imageURL) {
        Sname = sname;
        Smobile = smobile;
        Fname = fname;
        Fmobile = fmobile;
        Semail = semail;
        Spassword = spassword;
        Scollege = scollege;
        SimageURL  = imageURL;
    }

    public String getSimageURL() {
        return SimageURL;
    }

    public void setSimageURL(String simageURL) {
        SimageURL = simageURL;
    }

    public String getSname() {
        return Sname;
    }

    public void setSname(String sname) {
        Sname = sname;
    }

    public String getSmobile() {
        return Smobile;
    }

    public void setSmobile(String smobile) {
        Smobile = smobile;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getFmobile() {
        return Fmobile;
    }

    public void setFmobile(String fmobile) {
        Fmobile = fmobile;
    }

    public String getSemail() {
        return Semail;
    }

    public void setSemail(String semail) {
        Semail = semail;
    }

    public String getSpassword() {
        return Spassword;
    }

    public void setSpassword(String spassword) {
        Spassword = spassword;
    }

    public String getScollege() {
        return Scollege;
    }

    public void setScollege(String scollege) {
        Scollege = scollege;
    }
}
