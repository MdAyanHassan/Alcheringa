package com.alcheringa.alcheringa2022.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class cartModel implements Parcelable {
    String name;
    String type;
    String size;
    String price;
    String image;
    String count;
    String Image;

    protected cartModel(Parcel in) {
        name = in.readString();
        type = in.readString();
        size = in.readString();
        price = in.readString();
        image = in.readString();
        count = in.readString();
        Image = in.readString();
    }

    public static final Creator<cartModel> CREATOR = new Creator<cartModel>() {
        @Override
        public cartModel createFromParcel(Parcel in) {
            return new cartModel(in);
        }

        @Override
        public cartModel[] newArray(int size) {
            return new cartModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(type);
        parcel.writeString(size);
        parcel.writeString(price);
        parcel.writeString(image);
        parcel.writeString(count);
        parcel.writeString(Image);
    }
}
