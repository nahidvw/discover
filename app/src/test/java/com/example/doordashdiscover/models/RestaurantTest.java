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
}
