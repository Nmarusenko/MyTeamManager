package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    private Game testGame;

    @BeforeEach
    void runBefore() {
        testGame = new Game("Home", "Away");
    }


    @Test
    void testConstructor() {
        List<Integer> list = new ArrayList<Integer>();
        assertEquals("Home", testGame.getHomeTeam());
        assertEquals("Away", testGame.getAwayTeam());
        assertEquals(0, testGame.getHomeTeamGoals());
        assertEquals(0, testGame.getAwayTeamGoals());
        assertEquals(list, testGame.getHomeGoalScorers());

    }

    @Test
    void testSetHomeGoalsAndScorers() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(9);
        list.add(7);
        list.add(3);
        testGame.setHomeGoals(list);
        assertEquals(list, testGame.getHomeGoalScorers());
        assertEquals(3, testGame.getHomeTeamGoals());
        testGame.setAwayTeamGoals(5);
        assertEquals(5, testGame.getAwayTeamGoals());
    }

    @Test
    void testDisplayGameNoChanges() {
        assertEquals("Home 0-0 Away", testGame.displayGame());
    }

    @Test
    void testDisplayGameWithChanges() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(10);
        list.add(10);
        testGame.setHomeGoals(list);
        testGame.setAwayTeamGoals(3);
        assertEquals("Home 2-3 Away", testGame.displayGame());
    }
}
