package com.example.doordashdiscover.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.doordashdiscover.models.Restaurant;
import com.example.doordashdiscover.repositories.RestaurantRepository;

import java.util.List;

public class RestaurantListViewModel extends ViewModel {

    private RestaurantRepository mRestaurantRepository;

    public RestaurantListViewModel() {
        mRestaurantRepository = RestaurantRepository.getInstance();
    }

    public LiveData<List<Restaurant>> getRestaurants() {
        return mRestaurantRepository.getRestaurants();
    }

    public void getRestaurantsApi(String lat, String lng, int offset, int limit) {
        mRestaurantRepository.getRestaurantsApi(lat, lng, offset, limit);
    }
}
