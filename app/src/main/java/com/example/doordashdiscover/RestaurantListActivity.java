package com.example.doordashdiscover;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.doordashdiscover.adapters.OnRestaurantClickListener;
import com.example.doordashdiscover.adapters.RestaurantRecyclerAdapter;
import com.example.doordashdiscover.models.Restaurant;
import com.example.doordashdiscover.util.Testing;
import com.example.doordashdiscover.util.VerticalSpacingItemDecorator;
import com.example.doordashdiscover.viewmodels.RestaurantListViewModel;

import java.util.List;

public class RestaurantListActivity extends AppCompatActivity implements OnRestaurantClickListener {

    private static final String TAG = "RestaurantListActivity";

    private RestaurantListViewModel mRestaurantListViewModel;
    private RecyclerView mRecyclerView;
    private ImageButton mRetryBtn;
    private RestaurantRecyclerAdapter mRestaurantRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);
        mRecyclerView = findViewById(R.id.restaurant_List);
        mRetryBtn = findViewById(R.id.retry);
        mRetryBtn.setOnClickListener(new View.OnClickListener() {
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
                    Testing.printRestaurants(restaurants, TAG);
                    mRestaurantRecyclerAdapter.setRestaurants(restaurants); //set data to adapter
                }
            }
        });

        mRestaurantListViewModel.isRestaurantsRequestTimeout().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    displayRetryScreen();
                }
            }
        });

        mRestaurantListViewModel.isQueryExhausted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    Log.d(TAG, "onChanged: query is exhausted");
                    mRestaurantRecyclerAdapter.setQueryExhausted();
                }
            }
        });
    }

    private void displayRetryScreen() {
        mRetryBtn.setVisibility(View.VISIBLE);
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

        mRetryBtn.setVisibility(View.GONE);
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

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        super.onDestroy();
    }
}