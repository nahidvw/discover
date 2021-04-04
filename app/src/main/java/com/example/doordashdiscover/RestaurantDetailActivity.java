package com.example.doordashdiscover;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.doordashdiscover.databinding.ActivityRestaurantDetailBinding;
import com.example.doordashdiscover.models.Menu;
import com.example.doordashdiscover.models.Restaurant;
import com.example.doordashdiscover.util.Testing;
import com.example.doordashdiscover.viewmodels.RestaurantViewModel;

import java.util.List;

public class RestaurantDetailActivity extends BaseActivity {
    private static final String TAG = "RestaurantDetailActivity";
    public static final String RESTAURANT_DETAIL_EXTRA = "RestaurantDetail";

    private ActivityRestaurantDetailBinding binding;
    private RestaurantViewModel mRestaurantViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRestaurantDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mRestaurantViewModel = new ViewModelProvider(this).get(RestaurantViewModel.class);
        subscribeObservers();
        getIncomingIntent();
    }

    private void subscribeObservers() {
        mRestaurantViewModel.getRestaurant().observe(this, new Observer<Restaurant>() {
            @Override
            public void onChanged(Restaurant restaurant) {
                if (restaurant != null) {
                    Log.d(TAG, "onChanged: -----------------------");
                    Log.d(TAG, "onChanged: " + restaurant.getName());
                    
                    for(String tag : restaurant.getTags()) {
                        Log.d(TAG, "menu: " + tag);
                    }
                }
            }
        });
    }

    private void getIncomingIntent() {
        if(getIntent().hasExtra(RESTAURANT_DETAIL_EXTRA)) {
            Restaurant restaurant = getIntent().getParcelableExtra(RESTAURANT_DETAIL_EXTRA);
            Log.d(TAG, "getIncomingIntent: " + restaurant.getName());
            mRestaurantViewModel.getRestaurantApi(restaurant.getId());
        }
    }
}
