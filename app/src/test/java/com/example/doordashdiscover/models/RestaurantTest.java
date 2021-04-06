package com.example.doordashdiscover.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantTest {
    @Test
    void isRestaurantsEqual_identicalProperties_returnTrue() {
        //Arrange
        Restaurant rest1 = new Restaurant();
        rest1.setId("1");
        rest1.setName("McDonald");

        Restaurant rest2 = new Restaurant();
        rest2.setId("1");
        rest2.setName("McDonald");

        //Assert
        assertEquals(rest1, rest2);
    }

    @Test
    void isRestaurantsEqual_differentIDs_returnFalse() {
        //Arrange
        Restaurant rest1 = new Restaurant();
        rest1.setId("1");
        rest1.setName("McDonald");

        Restaurant rest2 = new Restaurant();
        rest2.setId("2");
        rest2.setName("McDonald");

        //Assert
        assertNotEquals(rest1, rest2);
    }

    @Test
    void isRestaurantsEqual_differentNames_returnFalse() {
        //Arrange
        Restaurant rest1 = new Restaurant();
        rest1.setId("1");
        rest1.setName("McDonald");

        Restaurant rest2 = new Restaurant();
        rest2.setId("1");
        rest2.setName("McDonalds");

        //Assert
        assertNotEquals(rest1, rest2);
    }

    @Test
    void isStatusEqual_returnTrue() {
        //Arrange
        Restaurant restaurant = new Restaurant();
        restaurant.setStatus(new Status(new int[] {45,45}, true));

        //Assert
        assertEquals(restaurant.getDisplayStatus(), "45");

        restaurant.setStatus(new Status(new int[] {45,45}, false));
        assertEquals(restaurant.getDisplayStatus(), "");

        restaurant.setStatus(new Status(new int[] {39}, true));
        assertEquals(restaurant.getDisplayStatus(), "39");
    }

    @Test
    void isStatusNotEqual_returnTrue() {
        //Arrange
        Restaurant restaurant = new Restaurant();
        restaurant.setStatus(new Status(new int[] {45,45}, true));

        //Assert
        assertNotEquals(restaurant.getDisplayStatus(), "45 Mins");

        restaurant.setStatus(new Status(new int[] {45,45}, false));
        assertNotEquals(restaurant.getDisplayStatus(), "45");

        restaurant.setStatus(new Status(new int[] {39}, true));
        assertNotEquals(restaurant.getDisplayStatus(), "");
    }
}
