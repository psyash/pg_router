package com.example.minor2;

public class Upload {
    private String mName;
    private String mUrl;
    public Upload(){

    }
    public Upload(String name, String url){
        if (name.trim().equals("")){
            mName = "No Name";
        }
        this.mName = name;
        this.mUrl = url;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getmName() {
        return mName;
    }

    public String getmUrl() {
        return mUrl;
    }
}
