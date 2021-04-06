package com.example.doordashdiscover.network;

import com.example.doordashdiscover.models.RestaurantDetails;
import com.example.doordashdiscover.requests.RestaurantApi;
import com.example.doordashdiscover.responses.RestaurantResponse;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RestaurantAPITest {
    private MockWebServer mServer;
    private RestaurantApi restaurantApi;

    @BeforeEach
    public void setUp() {
        mServer = new MockWebServer();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mServer.url("").toString())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        restaurantApi = retrofit.create(RestaurantApi.class);
    }

    @AfterEach
    public void tearDown() throws IOException {
        mServer.shutdown();
    }

    @Test
    public void fetchRestaurantDetails() throws IOException, InterruptedException {
        MockResponse response = new MockResponse().setBody(getStringFromFile("restaurant_details_1.txt"));
        mServer.enqueue(response);

        RestaurantDetails restaurant = restaurantApi.getRestaurantDetail("1").execute().body();
        assertNotNull(restaurant);
        assertEquals("1", restaurant.getId());

        RecordedRequest request = mServer.takeRequest();
        assertEquals("/v2/restaurant/1", request.getPath());
    }

    @Test
    public void fetchRestaurants() throws IOException, InterruptedException {
        MockResponse response = new MockResponse().setBody(getStringFromFile("restaurant_list.txt"));
        mServer.enqueue(response);

        RestaurantResponse restaurants =
                restaurantApi.getRestaurants("37.544233", "-122.2456898","0", "50").execute().body();
        assertNotNull(restaurants);
        assertEquals(50, restaurants.getRestaurants().size());

        RecordedRequest request = mServer.takeRequest();
        assertEquals("/v1/store_feed?lat=37.544233&lng=-122.2456898&offset=0&limit=50", request.getPath());
    }

    private String getStringFromFile(String fileName) throws IOException {
        InputStream is = Objects.requireNonNull(this.getClass().getClassLoader()).getResourceAsStream(fileName);
        StringBuilder buffer = new StringBuilder();
        InputStreamReader isr = null;

        try {
            isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            while ((line = br.readLine()) != null) {
                buffer.append(line).append("\n");
            }
        } finally {
            if (isr != null) {
                isr.close();
            }

            if (is != null) {
                is.close();
            }
        }
        return buffer.toString();
    }
}