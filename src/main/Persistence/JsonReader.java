package Persistence;


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

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
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

    // EFFECTS: parses workroom from JSON object and returns it
    private Team parseTeam(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Team team = new Team(name);
        addPlayers(team, jsonObject);
        addGames(team, jsonObject);
        return team;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addPlayers(Team team, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("players");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(team, nextPlayer);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addPlayer(Team team, JSONObject jsonObject) {
        String name = jsonObject.getString("player name");
        int number = jsonObject.getInt("player number");
        Player player = new Player(name, number);
        JSONArray ratingsArray = jsonObject.getJSONArray("player ratings");
        ratingsArray.toList();
        for (Object rating : ratingsArray) {
            player.addRating((Double) rating);
        }
        team.addPlayer(player);
    }

    private void addGames(Team team, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("games");
        for (Object json : jsonArray) {
            JSONObject nextGame = (JSONObject) json;
            addGame(team, nextGame);
        }
    }

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