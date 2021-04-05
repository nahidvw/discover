package com.example.doordashdiscover;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doordashdiscover.adapters.OnRestaurantClickListener;
import com.example.doordashdiscover.adapters.RestaurantRecyclerAdapter;
import com.example.doordashdiscover.util.VerticalSpacingItemDecorator;
import com.example.doordashdiscover.viewmodels.RestaurantListViewModel;

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
        mRetryView.setOnClickListener(v -> getRestaurantsApi());

        mRestaurantListViewModel = new ViewModelProvider(this).get(RestaurantListViewModel.class);
        initRecyclerView();
        subscribeObservers();
        getRestaurantsApi();
    }

    private void subscribeObservers() {
        mRestaurantListViewModel.getRestaurants().observe(this, restaurants -> {
            if (restaurants != null) {
                hideRetryScreen();
                mRestaurantRecyclerAdapter.setRestaurants(restaurants); //set data to adapter
            } else {
                displayRetryScreen();
            }
        });

        mRestaurantListViewModel.isQueryExhausted().observe(this, aBoolean -> {
            if(aBoolean) {
                Log.d(TAG, "onChanged: query is exhausted");
                if(mRestaurantListViewModel.getRestaurants().getValue() == null) {
                    displayRetryScreen();
                } else {
                    mRestaurantRecyclerAdapter.setQueryExhausted();
                }
            }
        });
    }

    private void getRestaurantsApi() {
        mRestaurantListViewModel.getRestaurantsApi();
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

    @Override
    public void onRestaurantClick(int position) {
        Intent intent = new Intent(this, RestaurantDetailActivity.class);
        intent.putExtra(RestaurantDetailActivity.RESTAURANT_DETAIL_EXTRA, mRestaurantRecyclerAdapter.getSelectedRestaurant(position));
        startActivity(intent);
    }

    private void displayRetryScreen() {
        mRetryView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    private void hideRetryScreen() {
        mRetryView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}