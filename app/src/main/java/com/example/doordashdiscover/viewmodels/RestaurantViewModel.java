package com.example.doordashdiscover.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.doordashdiscover.models.Restaurant;
import com.example.doordashdiscover.repositories.RestaurantRepository;

public class RestaurantViewModel extends ViewModel {
    private final RestaurantRepository mRestaurantRepository;
    private String mRestaurantId;

    public RestaurantViewModel() {
        this.mRestaurantRepository = RestaurantRepository.getInstance();
    }

    public LiveData<Restaurant> getRestaurant() {
        return mRestaurantRepository.getRestaurant();
    }

    public void getRestaurantApi(String id) {
        mRestaurantId = id;
        mRestaurantRepository.getRestaurantApi(id);
    }

    public String getRestaurantId() {
        return mRestaurantId;
    }
}
