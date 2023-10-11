package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {
    private Team testTeam;
    private Team testTeam2;
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;

    @BeforeEach
    void runBefore() {
        testTeam = new Team("Rangers");
        testTeam2 = new Team("Eagles");
        p1 = new Player("Noah", 10);
        p2 = new Player("Ronaldo", 7);
        p3 = new Player("Kevin", 17);
        p4 = new Player("Messi", 10);
        testTeam2.addPlayer(p1);
        testTeam2.addPlayer(p2);
        testTeam2.addPlayer(p3);
    }

    @Test
    void testConstructor() {
        List<Player> list1 = new ArrayList<Player>();
        List<Game> list2 = new ArrayList<Game>();
        assertEquals("Rangers", testTeam.getName());
        assertEquals(list1, testTeam.getPlayers());
        assertEquals(list2, testTeam.getGames());
        assertEquals(0, testTeam.getPoints());
    }

    @Test
    void testAddGameTie() {
        Game game = new Game("Rangers", "Away");
        testTeam2.addGame(game);
        List<Game> list = new ArrayList<Game>();
        list.add(game);
        assertEquals(list, testTeam2.getGames());
        assertEquals(1, testTeam2.getPoints());
    }

    @Test
    void testAddGameOneWinOneTieOneLoss() {
        Game game1 = new Game("Rangers", "Away1"); // 4-1 Win
        Game game2 = new Game("Rangers", "Away2"); // 1-2 loss
        Game game3 = new Game("Rangers", "Away3"); // 1-1 Tie
        game1.setAwayTeamGoals(1);
        List<Integer> list1 = new ArrayList<Integer>();
        list1.add(10);
        list1.add(9);
        list1.add(10);
        list1.add(17);
        game1.setHomeGoals(list1);
        game2.setAwayTeamGoals(2);
        List<Integer> list2 = new ArrayList<Integer>();
        list2.add(10);
        game2.setHomeGoals(list2);
        game3.setAwayTeamGoals(1);
        List<Integer> list3 = new ArrayList<Integer>();
        list3.add(17);
        game3.setHomeGoals(list3);
        testTeam2.addGame(game1);
        testTeam2.addGame(game2);
        testTeam2.addGame(game3);
        List<Game> list4 = new ArrayList<Game>();
        list4.add(game1);
        list4.add(game2);
        list4.add(game3);
        assertEquals(list4, testTeam2.getGames());
        assertEquals(4, testTeam2.getPoints());
        assertEquals(3, p1.getGoals());
        assertEquals(2, p3.getGoals());
        assertEquals(0, p2.getGoals());
    }

    @Test
    void testAddPlayerSuccess() {
        assertTrue(testTeam.addPlayer(p1));
        List<Player> players = new ArrayList<Player>();
        players.add(p1);
        assertEquals(players, testTeam.getPlayers());
    }

    @Test
    void testAddPlayerSamePlayer() {
        assertTrue(testTeam.addPlayer(p1));
        assertFalse(testTeam.addPlayer(p1));
        List<Player> players = new ArrayList<Player>();
        players.add(p1);
        assertEquals(players, testTeam.getPlayers());
    }

    @Test
    void testAddMultiplePlayerSameNumber() {
        assertTrue(testTeam.addPlayer(p1));
        assertTrue(testTeam.addPlayer(p2));
        assertTrue(testTeam.addPlayer(p3));
        assertFalse(testTeam.addPlayer(p4));
        List<Player> players = new ArrayList<Player>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        assertEquals(players, testTeam.getPlayers());
    }

    @Test
    void testRemoveOnePlayer() {
        assertTrue(testTeam2.removePlayer("Noah", 10));
    }

    @Test
    void testRemoveOnePlayerWrongName() {
        assertFalse(testTeam2.removePlayer("Ronaldo", 10));
    }

    @Test
    void testRemoveOnePlayerWrongNumber() {
        assertFalse(testTeam2.removePlayer("Noah", 11));
    }

    @Test
    void testRemoveMultipleAddMultiple() {
        assertTrue(testTeam2.removePlayer("Noah", 10));
        assertTrue(testTeam2.addPlayer(p4));
        assertFalse(testTeam2.addPlayer(p1));
        assertTrue(testTeam2.removePlayer("Kevin", 17));
        assertTrue(testTeam2.addPlayer(p3));
        assertTrue(testTeam2.removePlayer("Ronaldo", 7));
        List<Player> players = new ArrayList<Player>();
        players.add(p4);
        players.add(p3);
        assertEquals(players, testTeam2.getPlayers());
    }

    @Test
    void testDisplayGamesOneGame() {
        Game game = new Game("Rangers", "Away");
        testTeam2.addGame(game);
        List<String> scores = new ArrayList<String>();
        scores.add("Rangers 0-0 Away");
        assertEquals(scores, testTeam2.displayGames());
    }

    @Test
    void testDisplayGamesMultipleGames() {
        Game game1 = new Game("Rangers", "Away1"); // 4-1 Win
        Game game2 = new Game("Rangers", "Away2"); // 1-2 loss
        Game game3 = new Game("Rangers", "Away3"); // 1-1 Tie
        game1.setAwayTeamGoals(1);
        List<Integer> list1 = new ArrayList<Integer>();
        list1.add(10);
        list1.add(9);
        list1.add(10);
        list1.add(17);
        game1.setHomeGoals(list1);
        game2.setAwayTeamGoals(2);
        List<Integer> list2 = new ArrayList<Integer>();
        list2.add(10);
        game2.setHomeGoals(list2);
        game3.setAwayTeamGoals(1);
        List<Integer> list3 = new ArrayList<Integer>();
        list3.add(17);
        game3.setHomeGoals(list3);
        testTeam2.addGame(game1);
        testTeam2.addGame(game2);
        testTeam2.addGame(game3);
        List<String> scores = new ArrayList<String>();
        scores.add("Rangers 4-1 Away1");
        scores.add("Rangers 1-2 Away2");
        scores.add("Rangers 1-1 Away3");
        assertEquals(scores, testTeam2.displayGames());
    }

    @Test
    void findPlayerOnce() {
        assertEquals(p1, testTeam2.findPlayer("Noah", 10));
    }

}
