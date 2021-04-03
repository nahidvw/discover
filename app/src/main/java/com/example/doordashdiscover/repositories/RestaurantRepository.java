package com.example.doordashdiscover.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.doordashdiscover.models.Restaurant;

import java.util.List;

public class RestaurantRepository {
    private static RestaurantRepository instance;
    private MutableLiveData<List<Restaurant>> mRestaurants;

    public static RestaurantRepository getInstance() {
        if(instance == null){
            instance = new RestaurantRepository();
        }
        return instance;
    }

    private RestaurantRepository() {
        mRestaurants = new MutableLiveData<>();
    }

    public LiveData<List<Restaurant>> getRestaurants() {
        return mRestaurants;
    }
}
