package com.alcheringa.alcheringa2022.Model;

public class Sponsor_model {

    String logo;
    String webURL;

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setWebURL(String webURL) {
        this.webURL = webURL;
    }

    public String getLogo() {
        return logo;
    }

    public String getWebURL() {
        return webURL;
    }

    public Sponsor_model(String logo, String WebURL) {
        this.logo = logo;
        this.webURL = WebURL;
    }
}