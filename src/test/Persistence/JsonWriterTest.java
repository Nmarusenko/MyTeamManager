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

class JsonWriterTest extends JsonTest {


    @Test
    void testWriterInvalidFile() {
        try {
            Team team = new Team("My Team");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Team team = new Team("My Team");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(team);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            team = reader.read();
            assertEquals("My Team", team.getName());
            assertEquals(0, (team.getPlayers()).size());
            assertEquals(0, (team.getGames()).size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Team team = new Team("Rangers");
            team.addPlayer(new Player("Noah", 8));
            Player kohen = new Player("Kohen", 10);
            kohen.addRating(7.5);
            kohen.addRating(6.5);
            team.addPlayer(kohen);
            List<Integer> scorers1 = new ArrayList<Integer>();
            scorers1.add(8);
            scorers1.add(10);
            scorers1.add(10);
            Game game1 = new Game("Rangers", "Madrid");
            game1.setAwayTeamGoals(2);
            game1.setHomeGoals(scorers1);
            team.addGame(game1);
            List<Integer> scorers2 = new ArrayList<Integer>();
            scorers2.add(8);
            scorers2.add(8);
            scorers2.add(8);
            Game game2 = new Game("Rangers", "Barca");
            game2.setAwayTeamGoals(1);
            game2.setHomeGoals(scorers2);
            team.addGame(game2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(team);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            team = reader.read();
            assertEquals("Rangers", team.getName());
            List<Game> games = team.getGames();
            List<Player> players = team.getPlayers();
            assertEquals(2, players.size());
            assertEquals(2, games.size());
            List<Double> kohenRatings = new ArrayList<Double>();
            kohenRatings.add(7.5);
            kohenRatings.add(6.5);
            checkPlayer("Noah", 8, 4, new ArrayList<Double>(), players.get(0));
            checkPlayer("Kohen", 10, 2, kohenRatings, players.get(1));
            checkGame("Rangers", "Madrid", 3, 2, scorers1, games.get(0));
            checkGame("Rangers", "Barca", 3, 1, scorers2, games.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}