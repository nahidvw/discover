package com.example.doordashdiscover;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.doordashdiscover.databinding.ActivityRestaurantDetailBinding;
import com.example.doordashdiscover.models.Restaurant;

public class RestaurantDetailActivity extends BaseActivity {

    public static final String RESTAURANT_DETAIL_EXTRA = "RestaurantDetail";
    private ActivityRestaurantDetailBinding binding;
    private static final String TAG = "RestaurantDetailActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRestaurantDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getIncomingIntent();
    }

    private void getIncomingIntent() {
        if(getIntent().hasExtra(RESTAURANT_DETAIL_EXTRA)) {
            Restaurant restaurant = getIntent().getParcelableExtra(RESTAURANT_DETAIL_EXTRA);
            Log.d(TAG, "getIncomingIntent: " + restaurant.getName());
        }
    }
}
