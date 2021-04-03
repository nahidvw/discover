package com.example.doordashdiscover;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ReceiverCallNotAllowedException;
import android.os.Bundle;
import android.util.Log;

import com.example.doordashdiscover.adapters.OnRestaurantClickListener;
import com.example.doordashdiscover.adapters.RestaurantRecyclerAdapter;
import com.example.doordashdiscover.models.Menu;
import com.example.doordashdiscover.models.Restaurant;
import com.example.doordashdiscover.requests.RestaurantApi;
import com.example.doordashdiscover.requests.ServiceGenerator;
import com.example.doordashdiscover.responses.RestaurantDetailResponse;
import com.example.doordashdiscover.util.Testing;
import com.example.doordashdiscover.viewmodels.RestaurantListViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantListActivity extends BaseActivity implements OnRestaurantClickListener {

    private static final String TAG = "RestaurantListActivity";

    private RestaurantListViewModel mRestaurantListViewModel;
    private RecyclerView mRecyclerView;
    private RestaurantRecyclerAdapter mRestaurantRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);
        mRecyclerView = findViewById(R.id.restaurant_List);

        mRestaurantListViewModel = new ViewModelProvider(this).get(RestaurantListViewModel.class);
        initRecyclerView();

        subscribeObservers();

        testGetRestaurantsApi();
        //testRetrofitRequest();
        //testRestaurantDetailRequest();
    }

    private void subscribeObservers() {
        mRestaurantListViewModel.getRestaurants().observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {
                if (restaurants != null) {
                    Testing.printRestaurants(restaurants, TAG);
                    mRestaurantRecyclerAdapter.setRestaurants(restaurants); //set data to adapter
                }
            }
        });
    }

    private void getRestaurantsApi(String lat, String lng, int offset, int limit) {
        mRestaurantListViewModel.getRestaurantsApi(lat, lng, offset, limit);
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRestaurantRecyclerAdapter = new RestaurantRecyclerAdapter(this);
        mRecyclerView.setAdapter(mRestaurantRecyclerAdapter);
    }

    private void testRestaurantDetailRequest() {
        RestaurantApi restaurantApi = ServiceGenerator.getRestaurantApi();
        Call<RestaurantDetailResponse> restaurantDetailResponseCall = restaurantApi.
                getRestaurantDetail("1");

        restaurantDetailResponseCall.enqueue(new Callback<RestaurantDetailResponse>() {
            @Override
            public void onResponse(Call<RestaurantDetailResponse> call, Response<RestaurantDetailResponse> response) {
                Log.d(TAG, "onResponse: server responses: " + response.body().toString());
                if(response.code() == 200) {
                    Log.d(TAG, "onResponse: "+ response.body().toString());
                    List<Menu> menus = new ArrayList<>(response.body().getMenus());
                    for(Menu m : menus) {
                        Log.d(TAG, "onResponse: "+ m.getName());
                    }
                } else {
                    Log.d(TAG, "onResponse: "+ response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<RestaurantDetailResponse> call, Throwable t) {

            }
        });
    }

    private void testGetRestaurantsApi() {
        getRestaurantsApi(
                "37.422740",
                "-122.139956",
                0,
                50);
    }

    @Override
    public void onRestaurantClick(int position) {

    }
}