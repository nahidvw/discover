package com.example.doordashdiscover;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.doordashdiscover.databinding.ActivityRestaurantDetailBinding;
import com.example.doordashdiscover.models.Restaurant;
import com.example.doordashdiscover.models.RestaurantDetails;
import com.example.doordashdiscover.viewmodels.RestaurantViewModel;

import java.util.Objects;

public class RestaurantDetailActivity extends AppCompatActivity {
    private static final String TAG = "RestaurantDetailActivity";
    public static final String RESTAURANT_DETAIL_EXTRA = "RestaurantDetail";

    private RestaurantViewModel mRestaurantViewModel;
    private ActivityRestaurantDetailBinding binding;    //using view binding

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurantDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mRestaurantViewModel = new ViewModelProvider(this).get(RestaurantViewModel.class);

        binding.detailLoadingSpinner.setVisibility(View.VISIBLE);
        binding.errorView.setVisibility(View.GONE);

        subscribeObservers();
        getIncomingIntent();
    }

    private void subscribeObservers() {
        mRestaurantViewModel.getRestaurant().observe(this, restaurant -> {
            if (restaurant != null) {
                if(restaurant.getId().equals(mRestaurantViewModel.getRestaurantId())) {
                    mRestaurantViewModel.setRetrieveRestaurant(true);

                    Objects.requireNonNull(getSupportActionBar()).setTitle(restaurant.getName());
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    setRestaurantDetailViewProperties(restaurant);
                }
            }
        });

        mRestaurantViewModel.isRestaurantRequestTimeout().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean && !mRestaurantViewModel.didRetrieveRestaurant()) {
                    Log.d(TAG, "onChanged: timed out...");
                    displayErrorScreen();
                }
            }
        });
    }

    private void displayErrorScreen() {
        binding.detailLoadingSpinner.setVisibility(View.GONE);
        binding.errorView.setVisibility(View.VISIBLE);
    }

    private void getIncomingIntent() {
        if(getIntent().hasExtra(RESTAURANT_DETAIL_EXTRA)) {
            Restaurant restaurant = getIntent().getParcelableExtra(RESTAURANT_DETAIL_EXTRA);
            Log.d(TAG, "getIncomingIntent: " + restaurant.getName());
            mRestaurantViewModel.getRestaurantApi(restaurant.getId());
        }
    }

    private void showParent() {
        binding.parentScrollview.setVisibility(View.VISIBLE);
        binding.detailLoadingSpinner.setVisibility(View.GONE);
        binding.errorView.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setRestaurantDetailViewProperties(RestaurantDetails restaurant) {
        if(restaurant != null) {
            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(restaurant.getCover_img_url())
                    .into(binding.headerImage);

            binding.restaurantDetailsName.setText(TextUtils.isEmpty(restaurant.getName()) ? "" : restaurant.getName());
            binding.restaurantDetailsDescription.setText(TextUtils.isEmpty(restaurant.getDescription()) ? "" : restaurant.getDescription());
            binding.restaurantStatus.setText(TextUtils.isEmpty(restaurant.getStatus()) ? "" : restaurant.getStatus());
            binding.restaurantStatusType.setText(TextUtils.isEmpty(restaurant.getStatus_type()) ? "" : restaurant.getStatus_type());
            binding.restaurantDetailsRating.setText(TextUtils.isEmpty(String.valueOf(restaurant.getDisplayRating())) ? "" : String.valueOf(restaurant.getDisplayRating()));
            binding.restaurantDetailsDeliveryFee.setText(TextUtils.isEmpty(String.valueOf(restaurant.getDelivery_Fee())) ? "" : String.valueOf(restaurant.getDelivery_Fee()));

            //adding view programmatically in a layout
            binding.tagContainer.removeAllViews();
            for(String tag : restaurant.getTags()) {
                TextView textView = new TextView(this);
                textView.setText(tag);
                textView.setTextSize(16);
                textView.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                binding.tagContainer.addView(textView);
            }

            showParent();
        }
    }
}
