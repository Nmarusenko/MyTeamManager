package model;

import java.util.ArrayList;
import java.util.List;

public class Team{

    private String name;
    private List<Player> players;
    private List<Game> games;
    private int points;



    public Team(String name) { //constructor
        this.name = name;
        this.players = new ArrayList<Player>();
        this.games = new ArrayList<Game>();
        this.points = 0;
    }

    // MODIFIES: this
    // EFFECTS: adds the game to games, updates points
    public void addGame(Game game) {
        games.add(game);
        updatePoints();
        updateGoalScorers(game);
    }

    // MODIFIES: this
    // EFFECTS: cycles through all games and adds 3 points for a win
    //          one point for a tie and zero points for a loss
    public void updatePoints() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds the goals to the players who scored them
    public void updateGoalScorers(Game game) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds given player to list of players,
    //          if successful return true, if player has
    //          same number as another player, don't add player and return false
    public Boolean addPlayer(Player player) {
        //Stub
    }

    // MODIFIES: this
    // EFFECTS: removes player if name and number match
    public void removePlayer(String name, int number) {
        //Stub
    }





    // get and methods

    public String getName() {
        return name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Game> getGames() {
        return games;
    }

    public int getPoints() {
        return points;
    }

}
