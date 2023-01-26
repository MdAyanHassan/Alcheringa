package com.alcheringa.alcheringa2022.Model;

public class YourOrders_model {

    String status;
    String merch_name;
    String merch_type;
    String merch_quantity;
    String merch_size;
    String price;
    String decimalprice;
    String delivery_date;
    String Image;

    public void setMerch_type(String merch_type) {
        this.merch_type = merch_type;
    }

    public void setMerch_quantity(String merch_quantity) {
        this.merch_quantity = merch_quantity;
    }

    public void setMerch_size(String merch_size) {
        this.merch_size = merch_size;
    }

    public void setStatus(String  status) {
        this.status = status;
    }

    public void setMerchName(String merch_name) {
        this.merch_name = merch_name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getMerch_quantity() {
        return merch_quantity;
    }

    public String getMerch_size() {
        return merch_size;
    }

    public String getMerch_type() {
        return merch_type;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public String getStatus() {
        return status;
    }

    public String getMerch_name() {
        return merch_name;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return Image;
    }

    public YourOrders_model(String status, String merch_name, String merch_type, String merch_quantity, String merch_size, String price,String delivery_date, String Image) {
        this.status= status;
        this.merch_name = merch_name;
        this.price = price;
        this.delivery_date = delivery_date;
        this.merch_quantity = merch_quantity;
        this.merch_size = merch_size;
        this.merch_type = merch_type;
        this.Image = Image;
    }
}
