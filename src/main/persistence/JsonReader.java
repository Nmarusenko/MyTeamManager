package persistence;


import model.Game;
import model.Player;
import model.Team;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Represents a reader that reads team from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads team from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Team read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTeam(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses team from JSON object and returns it
    private Team parseTeam(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Team team = new Team(name);
        addPlayers(team, jsonObject);
        addGames(team, jsonObject);
        return team;
    }

    // MODIFIES: team
    // EFFECTS: parses players from JSON object and passes to helper method, adding to the team
    private void addPlayers(Team team, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("players");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(team, nextPlayer);
        }
    }

    // MODIFIES: team
    // EFFECTS: gets players information and adds them to team
    private void addPlayer(Team team, JSONObject jsonObject) {
        String name = jsonObject.getString("player name");
        int number = jsonObject.getInt("player number");
        Player player = new Player(name, number);
        JSONArray ratingsJsonArray = jsonObject.getJSONArray("player ratings");
        for (int i = 0; i < ratingsJsonArray.length(); i++) {
            player.addRating(ratingsJsonArray.getDouble(i));
        }
        team.addPlayer(player);
    }

    // MODIFIES: team
    // EFFECTS: gets each game from JSON object and passes to helper method, adding to team
    private void addGames(Team team, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("games");
        for (Object json : jsonArray) {
            JSONObject nextGame = (JSONObject) json;
            addGame(team, nextGame);
        }
    }

    // MODIFIES: this
    // EFFECTS: Retrieves information of a game and adds the game to team
    private void addGame(Team team, JSONObject jsonObject) {
        String home = jsonObject.getString("home team");
        String away = jsonObject.getString("away team");
        int oppGoals = jsonObject.getInt("away team goals");
        JSONArray homeScorers = jsonObject.getJSONArray("home goal scorers");
        homeScorers.toList();
        List<Integer> homeGoalScorers = new ArrayList<Integer>();
        for (Object scorer : homeScorers) {
            homeGoalScorers.add((Integer) scorer);
        }
        Game game = new Game(home, away);
        game.setAwayTeamGoals(oppGoals);
        game.setHomeGoals(homeGoalScorers);
        team.addGame(game);
    }
}
