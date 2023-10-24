package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player testPlayer;

    @BeforeEach
    void runBefore() {
        testPlayer = new Player("Noah", 8);
    }

    @Test
    void testConstructor() {
        List<Double> empty = new ArrayList<Double>();
        assertEquals("Noah", testPlayer.getName());
        assertEquals(8, testPlayer.getJerseyNum());
        assertEquals(0, testPlayer.getGoals());
        assertEquals(empty, testPlayer.getRatings());
    }

    @Test
    void testAddRatingOnce() {
        List<Double> list = new ArrayList<Double>();
        list.add(7.2);
        assertTrue(testPlayer.addRating(7.2));
        assertEquals(list, testPlayer.getRatings());
    }

    @Test
    void testAddMultipleRatingTwoFail() {
        List<Double> list = new ArrayList<Double>();
        list.add(10.0);
        list.add(10.0);
        list.add(9.9);
        list.add(0.0);
        list.add(0.1);
        assertTrue(testPlayer.addRating(10.0));
        assertTrue(testPlayer.addRating(10.0));
        assertFalse(testPlayer.addRating(10.1));
        assertTrue(testPlayer.addRating(9.9));
        assertTrue(testPlayer.addRating(0.0));
        assertTrue(testPlayer.addRating(0.1));
        assertFalse(testPlayer.addRating(-0.1));
        assertEquals(list, testPlayer.getRatings());
    }

    @Test
    void testAverageRatingOneRatingRoundDown() {
        testPlayer.addRating(7.19999);
        assertEquals(7.1, testPlayer.averageRating());
    }

    @Test
    void testAverageRatingMultipleRatings() {
        testPlayer.addRating(10.0);
        testPlayer.addRating(3.14);
        testPlayer.addRating(5.5);
        assertEquals(6.2, testPlayer.averageRating());
    }

    @Test
    void testSetRatings() {
        List<Double> ratings = new ArrayList<Double>();
        ratings.add(2.3);
        ratings.add(7.6);
        testPlayer.setRatings(ratings);
        assertEquals(2, (testPlayer.getRatings()).size());
        assertEquals(2.3, (testPlayer.getRatings().get(0)));
        assertEquals(7.6, (testPlayer.getRatings().get(1)));
    }


}