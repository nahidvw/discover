package com.example.doordashdiscover.responses;

import com.example.doordashdiscover.models.Menu;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantDetailResponse {
    @SerializedName("id")
    @Expose()
    private String id;

    @SerializedName("phone_number")
    @Expose()
    private String phone_number;

    @SerializedName("menus")
    @Expose()
    private List<Menu> menus;

    public String getId() {
        return id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    @Override
    public String toString() {
        return "RestaurantDetailResponse{" +
                "id='" + id + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", menus=" + menus +
                '}';
    }
}
