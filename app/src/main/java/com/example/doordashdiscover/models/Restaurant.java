package com.example.doordashdiscover.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class Restaurant implements Parcelable {
    private String id;
    private String name;
    private String description;
    private String cover_img_url;
    private String header_img_url;
    private String phone_number;
    private Menu[] menus;
    private String[] tags;
    private String status_type;
    private String deliveryFee;
    private String averageRating;
    private String numberOfRatings;

    public Restaurant(String id, String name, String description, String cover_img_url, String header_img_url, String phone_number, Menu[] menus, String[] tags, String status_type, String deliveryFee, String averageRating, String numberOfRatings) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cover_img_url = cover_img_url;
        this.header_img_url = header_img_url;
        this.phone_number = phone_number;
        this.menus = menus;
        this.tags = tags;
        this.status_type = status_type;
        this.deliveryFee = deliveryFee;
        this.averageRating = averageRating;
        this.numberOfRatings = numberOfRatings;
    }

    public Restaurant(){
        //empty constructor
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover_img_url() {
        return cover_img_url;
    }

    public void setCover_img_url(String cover_img_url) {
        this.cover_img_url = cover_img_url;
    }

    public String getHeader_img_url() {
        return header_img_url;
    }

    public void setHeader_img_url(String header_img_url) {
        this.header_img_url = header_img_url;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Menu[] getMenus() {
        return menus;
    }

    public void setMenus(Menu[] menus) {
        this.menus = menus;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getStatus_type() {
        return status_type;
    }

    public void setStatus_type(String status_type) {
        this.status_type = status_type;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public String getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(String numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cover_img_url='" + cover_img_url + '\'' +
                ", header_img_url='" + header_img_url + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", menus=" + Arrays.toString(menus) +
                ", tags=" + Arrays.toString(tags) +
                ", status='" + status_type + '\'' +
                ", deliveryFee=" + deliveryFee +
                ", averageRating=" + averageRating +
                ", numberOfRatings=" + numberOfRatings +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(cover_img_url);
        dest.writeString(header_img_url);
        dest.writeString(phone_number);
        dest.writeTypedArray(menus, flags);
        dest.writeStringArray(tags);
        dest.writeString(status_type);
        dest.writeString(deliveryFee);
        dest.writeString(averageRating);
        dest.writeString(numberOfRatings);
    }

    protected Restaurant(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        cover_img_url = in.readString();
        header_img_url = in.readString();
        phone_number = in.readString();
        menus = in.createTypedArray(Menu.CREATOR);
        tags = in.createStringArray();
        status_type = in.readString();
        deliveryFee = in.readString();
        averageRating = in.readString();
        numberOfRatings = in.readString();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };
}
