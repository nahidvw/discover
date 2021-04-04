package com.example.doordashdiscover;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.NavUtils;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.doordashdiscover.models.Restaurant;
import com.example.doordashdiscover.viewmodels.RestaurantViewModel;

public class RestaurantDetailActivity extends AppCompatActivity {
    private static final String TAG = "RestaurantDetailActivity";
    public static final String RESTAURANT_DETAIL_EXTRA = "RestaurantDetail";

    private RestaurantViewModel mRestaurantViewModel;

    //UI components
    private AppCompatImageView mHeaderImage;
    private TextView mRestaurantDetailsName;
    private TextView mRestaurantDetailsDescription;
    private TextView mRestaurantDetailsStatus;
    private TextView mRestaurantDetailsRating;
    private TextView mRestaurantDetailsDeliveryFee;
    private ScrollView mParent;
    private LinearLayout mTagContainer;
    private ProgressBar mDetailLoadingSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        mHeaderImage = findViewById(R.id.header_image);
        mRestaurantDetailsName = findViewById(R.id.restaurant_details_name);
        mRestaurantDetailsDescription = findViewById(R.id.restaurant_details_description);
        mRestaurantDetailsStatus = findViewById(R.id.restaurant_details_status);
        mRestaurantDetailsRating = findViewById(R.id.restaurant_details_rating);
        mRestaurantDetailsDeliveryFee = findViewById(R.id.restaurant_details_delivery_fee);
        mParent = findViewById(R.id.parent_scrollview);
        mTagContainer = findViewById(R.id.tag_container);
        mDetailLoadingSpinner = findViewById(R.id.detail_loading_spinner);

        mRestaurantViewModel = new ViewModelProvider(this).get(RestaurantViewModel.class);
        mDetailLoadingSpinner.setVisibility(View.VISIBLE);
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
        mParent.setVisibility(View.VISIBLE);
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
                    .into(mHeaderImage);

            mRestaurantDetailsName.setText(restaurant.getName());
            mRestaurantDetailsDescription.setText(restaurant.getDescription());
            mRestaurantDetailsStatus.setText(restaurant.getStatus_type());
            mRestaurantDetailsRating.setText(String.valueOf(restaurant.getAverageRating()));
            mRestaurantDetailsDeliveryFee.setText(String.valueOf(restaurant.getDeliveryFee()));

            mTagContainer.removeAllViews();
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
            mDetailLoadingSpinner.setVisibility(View.GONE);
        }
    }
}
