package Persistence;


import model.Game;
import model.Player;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkPlayer(String name, int number, int goals, List<Double> ratings,  Player player) {
        assertEquals(name, player.getName());
        assertEquals(number, player.getJerseyNum());
        assertEquals(goals, player.getGoals());
        assertEquals(ratings, player.getRatings());
    }

    protected void checkGame(String home, String away, int homeGoals, int awayGoals, List<Integer> scorers, Game game) {
        assertEquals(home, game.getHomeTeam());
        assertEquals(away, game.getAwayTeam());
        assertEquals(homeGoals, game.getHomeTeamGoals());
        assertEquals(awayGoals, game.getAwayTeamGoals());
        assertEquals(scorers, game.getHomeGoalScorers());
    }
}
