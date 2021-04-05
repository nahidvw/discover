package com.example.doordashdiscover.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.doordashdiscover.models.Restaurant;
import com.example.doordashdiscover.models.RestaurantDetails;
import com.example.doordashdiscover.requests.RestaurantApiClient;

import java.util.List;

public class RestaurantRepository {
    private static final int PAGE_ITEMS = 100;
    private static RestaurantRepository instance;
    private final RestaurantApiClient mRestaurantApiClient;
    private MutableLiveData<Boolean> mIsQueryExhausted = new MutableLiveData<>();

    //use MediatorLiveData to make a change before returning live data
    private MediatorLiveData<List<Restaurant>> mRestaurants = new MediatorLiveData<>();

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
        mRestaurants.addSource(restaurantListApiSource, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {
                if(restaurants != null) {
                    mRestaurants.setValue(restaurants);
                    doneQuery(restaurants);
                } else {
                    //search database cache
                    doneQuery(null);
                }
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

    public LiveData<Boolean> isRestaurantsRequestTimedOut() {
        return mRestaurantApiClient.isRestaurantsRequestTimedOut();
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
        getRestaurantsApi(lat, lng, offset + PAGE_ITEMS, limit);
    }
}
