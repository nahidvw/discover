package com.example.doordashdiscover.requests;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.doordashdiscover.AppExecutors;
import com.example.doordashdiscover.models.Restaurant;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.example.doordashdiscover.util.Constants.NETWORK_TIMEOUT;

public class RestaurantApiClient {
    private static RestaurantApiClient instance;
    private MutableLiveData<List<Restaurant>> mRestaurants;

    public static RestaurantApiClient getInstance() {
        if(instance == null) {
            instance = new RestaurantApiClient();
        }
        return instance;
    }

    private RestaurantApiClient() {
        mRestaurants = new MutableLiveData<>();
    }

    public LiveData<List<Restaurant>> getRestaurants() {
        return mRestaurants;
    }

    public void getRestaurantsApi() {
        final Future handler = AppExecutors.getInstance().getNetworkIO().submit(new Runnable() {
            @Override
            public void run() {
                //retrieve data from REST api
            }
        });

        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //let the user know it's timed out
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }
}
