package com.example.doordashdiscover.models;

import android.os.Parcel;
import android.os.Parcelable;

/*
1. Create all fields
2. Use command + n to create following:
    - Constructor
    - Getter and Setter
    - toString()
3. Finally do a parcelable implementation
*/

public class Restaurant implements Parcelable {
    private String id;
    private String name;
    private String description;
    private String cover_img_url;

    public Restaurant(String id, String name, String description, String cover_img_url) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cover_img_url = cover_img_url;
    }

    public Restaurant(){
        //empty constructor
    }

    protected Restaurant(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        cover_img_url = in.readString();
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

    @Override
    public String toString() {
        return "Restaurant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cover_img_url='" + cover_img_url + '\'' +
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
    }
}
