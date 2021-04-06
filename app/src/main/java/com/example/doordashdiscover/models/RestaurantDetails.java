package com.example.doordashdiscover.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.doordashdiscover.util.Constants;

import java.util.Arrays;

public class RestaurantDetails implements Parcelable {
    private String id;
    private String name;
    private String description;
    private String cover_img_url;
    private String[] tags;
    private String status;
    private String status_type;
    private double average_rating;
    private int number_of_ratings;

    public RestaurantDetails() {
    }

    public RestaurantDetails(String id, String name, String description, String cover_img_url, String[] tags, String status, String status_type, double average_rating, int number_of_ratings, int asap_time) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cover_img_url = cover_img_url;
        this.tags = tags;
        this.status = status;
        this.status_type = status_type;
        this.average_rating = average_rating;
        this.number_of_ratings = number_of_ratings;
    }

    protected RestaurantDetails(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        cover_img_url = in.readString();
        tags = in.createStringArray();
        status = in.readString();
        status_type = in.readString();
        average_rating = in.readDouble();
        number_of_ratings = in.readInt();
    }

    public static final Creator<RestaurantDetails> CREATOR = new Creator<RestaurantDetails>() {
        @Override
        public RestaurantDetails createFromParcel(Parcel in) {
            return new RestaurantDetails(in);
        }

        @Override
        public RestaurantDetails[] newArray(int size) {
            return new RestaurantDetails[size];
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

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_type() {
        return status_type;
    }

    public void setStatus_type(String status_type) {
        this.status_type = status_type;
    }

    public double getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(double average_rating) {
        this.average_rating = average_rating;
    }

    public int getNumber_of_ratings() {
        return number_of_ratings;
    }

    public void setNumber_of_ratings(int number_of_ratings) {
        this.number_of_ratings = number_of_ratings;
    }

    public String getDisplayRating() {
        return average_rating + " / " + Constants.RATING + " (" + number_of_ratings + " ratings)";
    }

    @Override
    public String toString() {
        return "RestaurantDetails{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cover_img_url='" + cover_img_url + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", status='" + status + '\'' +
                ", status_type='" + status_type + '\'' +
                ", average_Rating=" + average_rating +
                ", number_of_Ratings=" + number_of_ratings +
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
        dest.writeStringArray(tags);
        dest.writeString(status);
        dest.writeString(status_type);
        dest.writeDouble(average_rating);
        dest.writeInt(number_of_ratings);
    }
}
