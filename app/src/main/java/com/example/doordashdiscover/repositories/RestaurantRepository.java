package com.example.doordashdiscover.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.doordashdiscover.models.Restaurant;
import com.example.doordashdiscover.models.RestaurantDetails;
import com.example.doordashdiscover.requests.RestaurantApiClient;

import java.util.List;

import static com.example.doordashdiscover.util.Constants.PAGE_ITEMS;

public class RestaurantRepository {
    private static final String TAG = "RestaurantRepository";
    private static RestaurantRepository instance;
    private final RestaurantApiClient mRestaurantApiClient;
    private final MutableLiveData<Boolean> mIsQueryExhausted = new MutableLiveData<>();

    //use MediatorLiveData to make a change before returning live data
    private final MediatorLiveData<List<Restaurant>> mRestaurants = new MediatorLiveData<>();

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
        initMediator();
    }

    public LiveData<RestaurantDetails> getRestaurant() {
        return mRestaurantApiClient.getRestaurant();
    }

    public LiveData<List<Restaurant>> getRestaurants() {
        return mRestaurants;
    }

    private void initMediator() {
        LiveData<List<Restaurant>> restaurantListApiSource = mRestaurantApiClient.getRestaurants();
        mRestaurants.addSource(restaurantListApiSource, restaurants -> {
            if(restaurants != null) {
                mRestaurants.setValue(restaurants);
                doneQuery(restaurants);
            } else {
                //if we implement the caching part of repository then, search database cache here
                doneQuery(null);
            }
        });
    }

    private void doneQuery(List<Restaurant> list) {
        if(list != null) {
            if(list.size() % PAGE_ITEMS != 0) {
                mIsQueryExhausted.setValue(true);
            }
        } else {
            mIsQueryExhausted.setValue(true);
        }
    }

    public MutableLiveData<Boolean> isQueryExhausted() {
        return mIsQueryExhausted;
    }

    public LiveData<Boolean> isRestaurantRequestTimedOut() {
        return mRestaurantApiClient.isRestaurantRequestTimedOut();
    }

    public void getRestaurantApi(String id) {
        mRestaurantApiClient.getRestaurantApi(id);
    }

    public void getRestaurantsApi(String lat, String lng, int offset, int limit) {
        this.lat = lat;
        this.lng = lng;
        this.offset = offset;
        this.limit = limit;

        mIsQueryExhausted.setValue(false);
        mRestaurantApiClient.getRestaurantsApi(lat, lng, offset, limit);
    }

    public void getNextRestaurantPage() {
        Log.d(TAG, "getNextRestaurantPage: " + "offset: "+ (offset + PAGE_ITEMS));
        getRestaurantsApi(lat, lng, offset + PAGE_ITEMS, limit);
    }
}
