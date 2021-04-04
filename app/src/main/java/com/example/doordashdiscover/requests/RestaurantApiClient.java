package com.example.doordashdiscover.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.doordashdiscover.AppExecutors;
import com.example.doordashdiscover.models.Restaurant;
import com.example.doordashdiscover.responses.RestaurantResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.example.doordashdiscover.util.Constants.NETWORK_TIMEOUT;

public class RestaurantApiClient {
    private static final String TAG = "RestaurantApiClient";
    private static RestaurantApiClient instance;

    private final MutableLiveData<Restaurant> mRestaurant;
    private final MutableLiveData<List<Restaurant>> mRestaurants;

    private RetrieveRestaurantRunnable mRetrieveRestaurantRunnable;
    private RetrieveRestaurantsRunnable mRetrieveRestaurantsRunnable;

    public static RestaurantApiClient getInstance() {
        if(instance == null) {
            instance = new RestaurantApiClient();
        }
        return instance;
    }

    private RestaurantApiClient() {
        mRestaurant = new MutableLiveData<>();
        mRestaurants = new MutableLiveData<>();
    }

    public LiveData<Restaurant> getRestaurant() {
        return mRestaurant;
    }

    public LiveData<List<Restaurant>> getRestaurants() {
        return mRestaurants;
    }

    public void getRestaurantApi(String id) {
        if(mRetrieveRestaurantRunnable != null) {
            mRetrieveRestaurantRunnable = null;    //if a query has already executed in past, set it null
        }
        mRetrieveRestaurantRunnable = new RetrieveRestaurantRunnable(id);

        final Future handler = AppExecutors.getInstance().getNetworkIO().submit(mRetrieveRestaurantRunnable);

        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //let the user know if it's timed out
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public void getRestaurantsApi(String lat, String lng, int offset, int limit) {
        if(mRetrieveRestaurantsRunnable != null) {
            mRetrieveRestaurantsRunnable = null;    //if a query has already executed in past, set it null
        }
        mRetrieveRestaurantsRunnable = new RetrieveRestaurantsRunnable(lat, lng, offset, limit);

        final Future handler = AppExecutors.getInstance().getNetworkIO().submit(mRetrieveRestaurantsRunnable);

        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //let the user know if it's timed out
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveRestaurantRunnable implements Runnable {

        private final String id;
        private boolean cancelRequest;

        public RetrieveRestaurantRunnable(String id) {
            this.id = id;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response<Restaurant> response = getRestaurant(id).execute();  //this line will be executed in background thread
                if(cancelRequest) {
                    return;
                }

                if(response.code() == 200) {
                    if(response.body() != null) {
                        Restaurant restaurant = response.body();
                        mRestaurant.postValue(restaurant);
                    }
                } else {
                    if(response.errorBody() != null) {
                        String error = response.errorBody().string();
                        Log.e(TAG, "run: " + error);
                    }
                    mRestaurant.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mRestaurant.postValue(null);
            }
        }

        private Call<Restaurant> getRestaurant(String id) {
            return ServiceGenerator.getRestaurantApi().getRestaurantDetail(id);
        }

        private void cancelRequest() {
            Log.d(TAG, "cancelRequest: canceling the restaurant detail request");
            cancelRequest = true;
        }
    }

    private class RetrieveRestaurantsRunnable implements Runnable {

        private final String lat;
        private final String lng;
        private final int offset;
        private final int limit;
        private boolean cancelRequest;

        public RetrieveRestaurantsRunnable(String lat, String lng, int offset, int limit) {
            this.lat = lat;
            this.lng = lng;
            this.offset = offset;
            this.limit = limit;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response<RestaurantResponse> response = getRestaurants(lat, lng, offset, limit).execute();  //this line will be executed in background thread
                if(cancelRequest) {
                    return;
                }

                if(response.code() == 200) {
                    List<Restaurant> list = new ArrayList<>(((RestaurantResponse)response.body()).getRestaurants());

                    //if we are not beginning of list, then keep adding restaurants in current list
                    if (offset == 0) {
                        mRestaurants.postValue(list);
                    } else {
                        List<Restaurant> currentRestaurants = mRestaurants.getValue();
                        if(currentRestaurants != null) {
                            currentRestaurants.addAll(list);
                        }
                        mRestaurants.postValue(currentRestaurants);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    mRestaurants.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mRestaurants.postValue(null);
            }
        }

        private Call<RestaurantResponse> getRestaurants(String lat, String lng, int offset, int limit) {
            return ServiceGenerator.getRestaurantApi().getRestaurants(
                    lat,
                    lng,
                    String.valueOf(offset),
                    String.valueOf(limit)
            );
        }

        private void cancelRequest() {
            Log.d(TAG, "cancelRequest: canceling the search request");
            cancelRequest = true;
        }
    }

    public void cancelRequest() {
        if(mRetrieveRestaurantsRunnable != null) {
            mRetrieveRestaurantsRunnable.cancelRequest();
        }

        if(mRetrieveRestaurantRunnable != null) {
            mRetrieveRestaurantRunnable.cancelRequest();
        }
    }
}
