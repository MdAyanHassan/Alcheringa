package com.example.alcheringa2022.Model;

public class cartModel {
    String name;
    String type;
    String size;
    String price;
    String image;
    String count;
    String Image;

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getSize() {
        return size;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getCount() {
        return count;
    }

    public cartModel(String name, String type, String size, String price, String image, String count, String image1) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.price = price;
        this.image = image;
        this.count = count;
        Image = image1;
    }
}
