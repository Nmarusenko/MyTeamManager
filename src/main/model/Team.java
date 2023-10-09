package model;

import java.util.ArrayList;
import java.util.List;

public class Team {

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
        points = 0;
        for (Game game : games) {
            if (game.getHomeTeamGoals() > game.getAwayTeamGoals()) {
                points = points + 3; // WIN
            } else if (game.getHomeTeamGoals() == game.getAwayTeamGoals()) {
                points = points + 1; // TIE
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds the goals to the players who scored them
    public void updateGoalScorers(Game game) {
        List<Integer> scorers = game.getHomeGoalScorers();
        for (int scorer : scorers) {
            for (Player player : players) {
                if (scorer == player.getJerseyNum()) {
                    int goals = player.getGoals() + 1;
                    player.setGoals(goals);
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds given player to list of players,
    //          if successful return true, if player has
    //          same number as another player, don't add player and return false
    public Boolean addPlayer(Player newPlayer) {
        int newNumber = newPlayer.getJerseyNum();
        List<Integer> numbers = new ArrayList<Integer>();
        for (Player player : players) {
            numbers.add(player.getJerseyNum());
        }
        if (numbers.contains(newNumber)) {
            return false;
        } else {
            players.add(newPlayer);
            return true;
        }
    }

    // MODIFIES: this
    // EFFECTS: removes player if name and number match
    public Boolean removePlayer(String name, int number) {
        for (Player player : players) {
            if (player.getJerseyNum() == number && player.getName() == name) {
                players.remove(player);
                return true;
            }
        }
        return false;
    }

    public List<String> displayGames() {
        List<String> display = new ArrayList<String>();
        for (Game game : games) {
            display.add(game.displayGame());
        }
        return display;
    }


    // get methods

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
