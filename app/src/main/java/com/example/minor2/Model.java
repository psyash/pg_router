package com.example.minor2;

public class Model {
    private String pgname;
    private String norooms;
    private String sizeroom;
    private String internet;
    private String laundary;
    private String maid;
    private String otherfacilities;
    private String rent;
    private String solar;
    private String backup;
    private String boys;
    private String girls;
    private String Latitude;
    private String Longitude;
    private String P1imageURL;
    private String P2imageURL;
    private String P3imageURL;
    String type = "None";

    public Model() {
    }

    public Model(String pgname, String norooms, String sizeroom,
                 String internet, String laundary, String maid,
                 String otherfacilities, String rent, String solar,
                 String backup, String boys, String girls, String latitude,
                 String longitude, String p1imageURL, String p2imageURL, String p3imageURL) {
        this.pgname = pgname;
        this.norooms = norooms;
        this.sizeroom = sizeroom;
        this.internet = internet;
        this.laundary = laundary;
        this.maid = maid;
        this.otherfacilities = otherfacilities;
        this.rent = rent;
        this.solar = solar;
        this.backup = backup;
        this.boys = boys;
        this.girls = girls;
        Latitude = latitude;
        Longitude = longitude;
        P1imageURL = p1imageURL;
        P2imageURL = p2imageURL;
        P3imageURL = p3imageURL;

    }

    public String getPgname() {
        return pgname;
    }

    public void setPgname(String pgname) {
        this.pgname = pgname;
    }

    public String getNorooms() {
        return norooms;
    }

    public void setNorooms(String norooms) {
        this.norooms = norooms;
    }

    public String getSizeroom() {
        return sizeroom;
    }

    public void setSizeroom(String sizeroom) {
        this.sizeroom = sizeroom;
    }

    public String getInternet() {
        return internet;
    }

    public void setInternet(String internet) {
        this.internet = internet;
    }

    public String getLaundary() {
        return laundary;
    }

    public void setLaundary(String laundary) {
        this.laundary = laundary;
    }

    public String getMaid() {
        return maid;
    }

    public void setMaid(String maid) {
        this.maid = maid;
    }

    public String getOtherfacilities() {
        return otherfacilities;
    }

    public void setOtherfacilities(String otherfacilities) {
        this.otherfacilities = otherfacilities;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getSolar() {
        return solar;
    }

    public void setSolar(String solar) {
        this.solar = solar;
    }

    public String getBackup() {
        return backup;
    }

    public void setBackup(String backup) {
        this.backup = backup;
    }

    public String getBoys() {
        return boys;
    }

    public void setBoys(String boys) {
        this.boys = boys;
    }

    public String getGirls() {
        return girls;
    }

    public void setGirls(String girls) {
        this.girls = girls;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getP1imageURL() {
        return P1imageURL;
    }

    public void setP1imageURL(String p1imageURL) {
        P1imageURL = p1imageURL;
    }

    public String getP2imageURL() {
        return P2imageURL;
    }

    public void setP2imageURL(String p2imageURL) {
        P2imageURL = p2imageURL;
    }

    public String getP3imageURL() {
        return P3imageURL;
    }

    public void setP3imageURL(String p3imageURL) {
        P3imageURL = p3imageURL;
    }

    public String getType(){
        if(!girls.equals("None")){
            type = "Girls";
        }
        else{
            type = "Boys";
        }
        return type;
    }


}
