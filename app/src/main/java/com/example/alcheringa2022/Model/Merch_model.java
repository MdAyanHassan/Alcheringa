package com.example.alcheringa2022.Model;

public class Merch_model {
    String Name_hoddie;
    String Material;
    String Price;
    String Description;
    String Image_url;
    Boolean Is_available;
    Boolean Small;
    Boolean Medium;
    Boolean Large;

    public void setName_hoddie(String name_hoddie) {
        Name_hoddie = name_hoddie;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setImage_url(String image_url) {
        Image_url = image_url;
    }

    public void setIs_available(Boolean is_available) {
        Is_available = is_available;
    }

    public void setSmall(Boolean small) {
        Small = small;
    }

    public void setMedium(Boolean medium) {
        Medium = medium;
    }

    public void setLarge(Boolean large) {
        Large = large;
    }

    public String getName_hoddie() {
        return Name_hoddie;
    }

    public Merch_model(String name_hoddie, String material, String price, String description, String image_url, Boolean is_available, Boolean small, Boolean medium, Boolean large) {
        Name_hoddie = name_hoddie;
        Material = material;
        Price = price;
        Description = description;
        Image_url = image_url;
        Is_available = is_available;
        Small = small;
        Medium = medium;
        Large = large;
    }

    public String getMaterial() {
        return Material;
    }

    public String getPrice() {
        return Price;
    }

    public String getDescription() {
        return Description;
    }

    public String getImage_url() {
        return Image_url;
    }

    public Boolean getIs_available() {
        return Is_available;
    }

    public Boolean getSmall() {
        return Small;
    }

    public Boolean getMedium() {
        return Medium;
    }

    public Boolean getLarge() {
        return Large;
    }
}
