package model;

import Persistence.Writable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents a team. A team has a name, a list of players that play for that team,
// a list of games that team has played in, and a number of points earned across those games
public class Team implements Writable {

    private String name;
    private List<Player> players;
    private List<Game> games;
    private int points;


    // Constructs a team with given name, no players added, no games added and no points
    public Team(String name) { //constructor
        this.name = name;
        this.players = new ArrayList<Player>();
        this.games = new ArrayList<Game>();
        this.points = 0;
    }

    // MODIFIES: this
    // EFFECTS: adds the game to games, updates points, updates goalscorers
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

    // EFFECTS: constructs a list of strings with players name and number in the form "name, number"
    public List<String> viewAllPlayers() {
        List<String> list = new ArrayList<String>();
        for (Player player : players) {
            list.add(player.getName() + ", " + player.getJerseyNum());
        }
        return list;
    }

    // REQUIRES: There are no duplicate players in the list
    // EFFECTS: Returns the player with given name and number, otherwise null
    public Player findPlayer(String name, int number) {
        for (Player player : players) {
            if ((player.getName()).equals(name) && player.getJerseyNum() == number) {
                return player;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: removes player if name and number match
    public Boolean removePlayer(String name, int number) {
        for (Player player : players) {
            if (player.getJerseyNum() == number && (player.getName()).equals(name)) {
                players.remove(player);
                return true;
            }
        }
        return false;
    }

    // EFFECTS: Constructs a list of all games displayed as a string
    public List<String> displayGames() {
        List<String> display = new ArrayList<String>();
        for (Game game : games) {
            display.add(game.displayGame());
        }
        return display;
    }

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



    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("players", playersToJson());
        json.put("games", gamesToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Player player : players) {
            jsonArray.put(player.toJson());
        }
        return jsonArray;
    }

    private JSONArray gamesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Game game : games) {
            jsonArray.put(game.toJson());
        }
        return jsonArray;
    }



}
