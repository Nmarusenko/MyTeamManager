package Persistence;


import model.Game;
import model.Player;
import model.Team;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Team team = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTeam.json");
        try {
            Team team = reader.read();
            assertEquals("Rangers", team.getName());
            assertEquals(0, (team.getPlayers()).size());
            assertEquals(0, (team.getGames()).size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTeam.json");
        try {
            Team team = reader.read();
            assertEquals("Rangers", team.getName());
            List<Game> games = team.getGames();
            List<Player> players = team.getPlayers();
            List<Double> noahRatings = new ArrayList<Double>();
            List<Double> emptyRatings = new ArrayList<Double>();
            List<Double> owenRatings = new ArrayList<Double>();
            noahRatings.add(7.2);
            noahRatings.add(7.8);
            owenRatings.add(5.0);
            assertEquals(2, games.size());
            assertEquals(4, players.size());
            checkPlayer("Noah", 8, 2, noahRatings, players.get(0));
            checkPlayer("Kohen", 10, 1, emptyRatings, players.get(1));
            checkPlayer("Liam", 7, 2, emptyRatings, players.get(2));
            checkPlayer("Owen", 17, 0, owenRatings, players.get(3));
            List<Integer> goalScorers = new ArrayList<>();
            goalScorers.add(8);
            goalScorers.add(8);
            List<Integer> scorers = new ArrayList<Integer>();
            scorers.add(7);
            scorers.add(7);
            scorers.add(10);
            checkGame("Rangers", "Beans", 2, 3, goalScorers, games.get(0));
            checkGame("Rangers", "Potato", 3, 3, scorers, games.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}