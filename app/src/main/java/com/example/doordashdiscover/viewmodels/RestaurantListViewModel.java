package com.example.doordashdiscover.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.doordashdiscover.models.Restaurant;
import com.example.doordashdiscover.repositories.RestaurantRepository;

import java.util.List;

import static com.example.doordashdiscover.util.Constants.PAGE_ITEMS;
import static com.example.doordashdiscover.util.Constants.QUERY_LATITUDE;
import static com.example.doordashdiscover.util.Constants.QUERY_LONGITUDE;

public class RestaurantListViewModel extends ViewModel {

    private final RestaurantRepository mRestaurantRepository;

    public RestaurantListViewModel() {
        mRestaurantRepository = RestaurantRepository.getInstance();
    }

    public LiveData<List<Restaurant>> getRestaurants() {
        return mRestaurantRepository.getRestaurants();
    }

    public LiveData<Boolean> isQueryExhausted() {
        return mRestaurantRepository.isQueryExhausted();
    }

    public void getRestaurantsApi() {
        mRestaurantRepository.getRestaurantsApi(QUERY_LATITUDE, QUERY_LONGITUDE, 0, PAGE_ITEMS);
    }

    public void getNextRestaurantPage() {
        if(!isQueryExhausted().getValue()) {
            mRestaurantRepository.getNextRestaurantPage();
        }
    }
}
