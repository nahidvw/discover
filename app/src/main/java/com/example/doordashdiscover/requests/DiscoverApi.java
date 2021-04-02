package com.example.doordashdiscover.requests;

import com.example.doordashdiscover.responses.RestaurantDetailResponse;
import com.example.doordashdiscover.responses.RestaurantResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DiscoverApi {
    //Get restaurants
    @GET("v1/store_feed")
    Call<RestaurantResponse> getRestaurants(
            @Query("lat") String lat,
            @Query("lng") String lng,
            @Query("offset") String offset,
            @Query("limit") String limit
    );

    //Get restaurant details
    @GET("v2/restaurant/{id}")
    Call<RestaurantDetailResponse> getRestaurantDetail(
            @Path("id") String id
    );
}
