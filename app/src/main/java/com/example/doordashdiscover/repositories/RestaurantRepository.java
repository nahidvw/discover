package com.example.doordashdiscover.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.doordashdiscover.models.Restaurant;
import com.example.doordashdiscover.requests.RestaurantApiClient;

import java.util.List;

public class RestaurantRepository {
    private static final int PAGE_ITEMS = 10;
    private static RestaurantRepository instance;
    private final RestaurantApiClient mRestaurantApiClient;

    private String lat;
    private String lng;
    private int offset;
    private int limit;

    public static RestaurantRepository getInstance() {
        if(instance == null){
            instance = new RestaurantRepository();
        }
        return instance;
    }

    private RestaurantRepository() {
        mRestaurantApiClient = RestaurantApiClient.getInstance();
    }

    public LiveData<Restaurant> getRestaurant() {
        return mRestaurantApiClient.getRestaurant();
    }

    public LiveData<List<Restaurant>> getRestaurants() {
        return mRestaurantApiClient.getRestaurants();
    }

    public void getRestaurantApi(String id) {
        mRestaurantApiClient.getRestaurantApi(id);
    }

    public void getRestaurantsApi(String lat, String lng, int offset, int limit) {
        this.lat = lat;
        this.lng = lng;
        this.offset = offset;
        this.limit = limit;
        mRestaurantApiClient.getRestaurantsApi(lat, lng, offset, limit);
    }

    public void getNextRestaurantPage() {
        getRestaurantsApi(lat, lng, offset + PAGE_ITEMS, limit);
    }
}
