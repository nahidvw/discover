package com.example.doordashdiscover.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doordashdiscover.models.Restaurant;

import java.util.List;

public class RestaurantListViewModel extends ViewModel {

    private MutableLiveData<List<Restaurant>> mRestaurants = new MutableLiveData<>();

    public RestaurantListViewModel() {
    }

    public LiveData<List<Restaurant>> getRestaurants() {
        return mRestaurants;
    }
}
