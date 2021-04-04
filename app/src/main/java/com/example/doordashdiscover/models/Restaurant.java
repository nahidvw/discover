package com.example.doordashdiscover.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.doordashdiscover.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restaurant implements Parcelable {
    private String id;
    private String name;
    private String description;
    private String cover_img_url;
    private String display_delivery_fee;
    private Status status;
    private transient String displayStatus;

    public Restaurant(){
    }

    public Restaurant(String id, String name, String description, String cover_img_url, String display_delivery_fee, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cover_img_url = cover_img_url;
        this.display_delivery_fee = display_delivery_fee;
        this.status = status;
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

    public String getDisplay_delivery_fee() {
        return display_delivery_fee;
    }

    public void setDisplay_delivery_fee(String display_delivery_fee) {
        this.display_delivery_fee = display_delivery_fee;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDisplayStatus() {
        displayStatus = "";
        if(status.isAsap_available() && status.getAsap_minutes_range().length != 0) {
            displayStatus = String.valueOf(status.getAsap_minutes_range()[0]);
        }
        return displayStatus;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cover_img_url='" + cover_img_url + '\'' +
                ", display_delivery_fee='" + display_delivery_fee + '\'' +
                ", status=" + status +
                '}';
    }

    protected Restaurant(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        cover_img_url = in.readString();
        display_delivery_fee = in.readString();
        status = in.readParcelable(Status.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(cover_img_url);
        dest.writeString(display_delivery_fee);
        dest.writeParcelable(status, flags);
    }

    @Override
    public int describeContents() {
        return 0;
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
