package com.example.doordashdiscover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doordashdiscover.adapters.OnRestaurantClickListener;
import com.example.doordashdiscover.adapters.RestaurantRecyclerAdapter;
import com.example.doordashdiscover.models.Restaurant;
import com.example.doordashdiscover.util.VerticalSpacingItemDecorator;
import com.example.doordashdiscover.viewmodels.RestaurantListViewModel;

import java.util.List;

public class RestaurantListActivity extends AppCompatActivity implements OnRestaurantClickListener {

    private static final String TAG = "RestaurantListActivity";

    private RestaurantListViewModel mRestaurantListViewModel;
    private RecyclerView mRecyclerView;
    private TextView mRetryView;
    private RestaurantRecyclerAdapter mRestaurantRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);
        mRecyclerView = findViewById(R.id.restaurant_List);
        mRetryView = findViewById(R.id.retry);
        mRetryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testGetRestaurantsApi();
            }
        });

        mRestaurantListViewModel = new ViewModelProvider(this).get(RestaurantListViewModel.class);
        initRecyclerView();
        subscribeObservers();
        testGetRestaurantsApi();
    }

    private void subscribeObservers() {
        mRestaurantListViewModel.getRestaurants().observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {
                if (restaurants != null) {
                    hideRetryScreen();
                    mRestaurantRecyclerAdapter.setRestaurants(restaurants); //set data to adapter
                } else {
                    displayRetryScreen();
                }
            }
        });

        mRestaurantListViewModel.isQueryExhausted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    Log.d(TAG, "onChanged: query is exhausted");
                    if(mRestaurantListViewModel.getRestaurants().getValue() == null) {
                        displayRetryScreen();
                    } else {
                        mRestaurantRecyclerAdapter.setQueryExhausted();
                    }
                }
            }
        });
    }

    private void displayRetryScreen() {
        mRetryView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    private void hideRetryScreen() {
        mRetryView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void getRestaurantsApi(String lat, String lng, int offset, int limit) {
        mRestaurantListViewModel.getRestaurantsApi(lat, lng, offset, limit);
    }

    private void initRecyclerView() {
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(18);
        mRecyclerView.addItemDecoration(verticalSpacingItemDecorator);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRestaurantRecyclerAdapter = new RestaurantRecyclerAdapter(this, this);
        mRecyclerView.setAdapter(mRestaurantRecyclerAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!mRecyclerView.canScrollVertically(1)) {
                    //fetch the next 50
                    mRestaurantListViewModel.getNextRestaurantPage();
                }
            }
        });
    }

    private void testGetRestaurantsApi() {
        getRestaurantsApi(
                "37.422740",
                "-122.139956",
                0,
                100);
    }

    @Override
    public void onRestaurantClick(int position) {
        Intent intent = new Intent(this, RestaurantDetailActivity.class);
        intent.putExtra(RestaurantDetailActivity.RESTAURANT_DETAIL_EXTRA, mRestaurantRecyclerAdapter.getSelectedRestaurant(position));
        startActivity(intent);
    }
}