package com.example.doordashdiscover.util;

import android.util.Log;

import com.example.doordashdiscover.models.Restaurant;

import java.util.List;

public class Testing {
    public static void printRestaurants(List<Restaurant> list, String tag) {
        for(Restaurant r : list) {
            Log.d(tag, "printRestaurants: " + r.getName());
        }
    }
}
