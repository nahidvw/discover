package com.example.doordashdiscover.requests;

import com.example.doordashdiscover.util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static RestaurantApi restaurantApi = retrofit.create(RestaurantApi.class);

    public static RestaurantApi getRestaurantApi() {
        return restaurantApi;
    }
}
