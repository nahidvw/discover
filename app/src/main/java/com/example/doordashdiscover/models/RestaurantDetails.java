package com.example.doordashdiscover.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class RestaurantDetails implements Parcelable {
    private String id;
    private String name;
    private String description;
    private String cover_img_url;
    private String[] tags;
    private String status;
    private String status_type;
    private double delivery_Fee;
    private double average_rating;
    private int number_of_ratings;
    private double composite_score;
    private int asap_time;
    private transient String displayRating;

    private static final double RATING = 5.0;

    public RestaurantDetails() {
    }

    public RestaurantDetails(String id, String name, String description, String cover_img_url, String[] tags, String status, String status_type, double delivery_Fee, double average_rating, int number_of_ratings, double composite_score, int asap_time) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cover_img_url = cover_img_url;
        this.tags = tags;
        this.status = status;
        this.status_type = status_type;
        this.delivery_Fee = delivery_Fee;
        this.average_rating = average_rating;
        this.number_of_ratings = number_of_ratings;
        this.composite_score = composite_score;
        this.asap_time = asap_time;
    }

    protected RestaurantDetails(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        cover_img_url = in.readString();
        tags = in.createStringArray();
        status = in.readString();
        status_type = in.readString();
        delivery_Fee = in.readDouble();
        average_rating = in.readDouble();
        number_of_ratings = in.readInt();
        composite_score = in.readDouble();
        asap_time = in.readInt();
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

    public double getDelivery_Fee() {
        return delivery_Fee;
    }

    public void setDelivery_Fee(double delivery_Fee) {
        this.delivery_Fee = delivery_Fee;
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

    public double getComposite_score() {
        return composite_score;
    }

    public void setComposite_score(double composite_score) {
        this.composite_score = composite_score;
    }

    public int getAsap_time() {
        return asap_time;
    }

    public void setAsap_time(int asap_time) {
        this.asap_time = asap_time;
    }

    public String getDisplayRating() {
        displayRating = average_rating + " / " + RATING + " (" + number_of_ratings + " ratings)";   //this can be done in elegant way
        return displayRating;
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
                ", deliveryFee=" + delivery_Fee +
                ", average_Rating=" + average_rating +
                ", number_of_Ratings=" + number_of_ratings +
                ", composite_score=" + composite_score +
                ", asap_time=" + asap_time +
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
        dest.writeDouble(delivery_Fee);
        dest.writeDouble(average_rating);
        dest.writeInt(number_of_ratings);
        dest.writeDouble(composite_score);
        dest.writeInt(asap_time);
    }
}
