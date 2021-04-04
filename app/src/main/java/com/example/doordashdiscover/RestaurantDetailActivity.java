package com.example.doordashdiscover;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.doordashdiscover.databinding.ActivityRestaurantDetailBinding;
import com.example.doordashdiscover.models.Restaurant;
import com.example.doordashdiscover.viewmodels.RestaurantViewModel;

public class RestaurantDetailActivity extends AppCompatActivity {
    private static final String TAG = "RestaurantDetailActivity";
    public static final String RESTAURANT_DETAIL_EXTRA = "RestaurantDetail";

    private RestaurantViewModel mRestaurantViewModel;
    private ActivityRestaurantDetailBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurantDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mRestaurantViewModel = new ViewModelProvider(this).get(RestaurantViewModel.class);
        binding.detailLoadingSpinner.setVisibility(View.VISIBLE);
        subscribeObservers();
        getIncomingIntent();
    }

    private void subscribeObservers() {
        mRestaurantViewModel.getRestaurant().observe(this, new Observer<Restaurant>() {
            @Override
            public void onChanged(Restaurant restaurant) {
                if (restaurant != null) {
                    if(restaurant.getId().equals(mRestaurantViewModel.getRestaurantId())) {
                        getSupportActionBar().setTitle(restaurant.getName());
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        setRestaurantDetailViewProperties(restaurant);
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

    private void showParent() {
        binding.parentScrollview.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setRestaurantDetailViewProperties(Restaurant restaurant) {
        if(restaurant != null) {
            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(restaurant.getCover_img_url())
                    .into(binding.headerImage);

            binding.restaurantDetailsName.setText(TextUtils.isEmpty(restaurant.getName()) ? "" : restaurant.getName());
            binding.restaurantDetailsDescription.setText(TextUtils.isEmpty(restaurant.getDescription()) ? "" : restaurant.getDescription());
            binding.restaurantDetailsStatus.setText(TextUtils.isEmpty(restaurant.getStatus_type()) ? "" : restaurant.getStatus_type());
            binding.restaurantDetailsRating.setText(TextUtils.isEmpty(restaurant.getAverageRating()) ? "" : restaurant.getAverageRating());
            binding.restaurantDetailsDeliveryFee.setText(TextUtils.isEmpty(restaurant.getDeliveryFee()) ? "" : restaurant.getDeliveryFee());

            binding.tagContainer.removeAllViews();
//            for(String tag : restaurant.getTags()) {
//                TextView textView = new TextView(this);
//                textView.setText(tag);
//                textView.setTextSize(R.dimen.text_size);
//                textView.setLayoutParams(new LinearLayout.LayoutParams(
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT
//                ));
//                mTagContainer.addView(textView);
//            }

            showParent();
            binding.detailLoadingSpinner.setVisibility(View.GONE);
        }
    }
}
