package com.example.doordashdiscover.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class RestaurantDetails implements Parcelable {
    private String id;
    private String name;
    private String description;
    private String cover_img_url;
    private String phone_number;
    private String[] tags;
    private String status;
    private String status_type;
    private double deliveryFee;
    private double average_Rating;
    private int number_of_Ratings;
    private double composite_score;
    private int asap_time;

    public RestaurantDetails() {
    }

    public RestaurantDetails(String id, String name, String description, String cover_img_url, String phone_number, String[] tags, String status, String status_type, double deliveryFee, double average_Rating, int number_of_Ratings, double composite_score, int asap_time) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cover_img_url = cover_img_url;
        this.phone_number = phone_number;
        this.tags = tags;
        this.status = status;
        this.status_type = status_type;
        this.deliveryFee = deliveryFee;
        this.average_Rating = average_Rating;
        this.number_of_Ratings = number_of_Ratings;
        this.composite_score = composite_score;
        this.asap_time = asap_time;
    }

    protected RestaurantDetails(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        cover_img_url = in.readString();
        phone_number = in.readString();
        tags = in.createStringArray();
        status = in.readString();
        status_type = in.readString();
        deliveryFee = in.readDouble();
        average_Rating = in.readDouble();
        number_of_Ratings = in.readInt();
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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public double getAverage_Rating() {
        return average_Rating;
    }

    public void setAverage_Rating(double average_Rating) {
        this.average_Rating = average_Rating;
    }

    public int getNumber_of_Ratings() {
        return number_of_Ratings;
    }

    public void setNumber_of_Ratings(int number_of_Ratings) {
        this.number_of_Ratings = number_of_Ratings;
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

    @Override
    public String toString() {
        return "RestaurantDetails{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cover_img_url='" + cover_img_url + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", status='" + status + '\'' +
                ", status_type='" + status_type + '\'' +
                ", deliveryFee=" + deliveryFee +
                ", average_Rating=" + average_Rating +
                ", number_of_Ratings=" + number_of_Ratings +
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
        dest.writeString(phone_number);
        dest.writeStringArray(tags);
        dest.writeString(status);
        dest.writeString(status_type);
        dest.writeDouble(deliveryFee);
        dest.writeDouble(average_Rating);
        dest.writeInt(number_of_Ratings);
        dest.writeDouble(composite_score);
        dest.writeInt(asap_time);
    }
}
