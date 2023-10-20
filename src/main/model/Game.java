package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents a game score that has a home team, an away team, goals for either team
// and a list of goalscorers for the home team.
public class Game {

    private String homeTeam;
    private String awayTeam;
    private int homeTeamGoals;
    private int awayTeamGoals;
    private List<Integer> homeGoalScorers;

    // EFFECTS: constructs a game with a home team, an away team and a preset score of 0-0
    public Game(String teamHome, String teamAway) {
        this.homeTeam = teamHome;
        this.awayTeam = teamAway;
        this.homeTeamGoals = 0;
        this.awayTeamGoals = 0;
        this.homeGoalScorers = new ArrayList<Integer>();
    }

    // EFFECTS: Displays the game in the form "HomeTeam #-# AwayTeam"
    //          where # is a number associated with the score
    public String displayGame() {
        int homeGoals = getHomeTeamGoals();
        int awayGoals = getAwayTeamGoals();
        return getHomeTeam() + " " + homeGoals + "-" + awayGoals + " " + getAwayTeam();
    }


    // MODIFIES: this
    // EFFECTS: sets homeTeamGoals and scorers
    public void setHomeGoals(List<Integer> scorers) {
        this.homeGoalScorers = scorers;
        this.homeTeamGoals = scorers.size();
    }

    // REQUIRES: goals is non-negative
    // MODIFIES: this
    // EFFECTS: Sets the away teams goals
    public void setAwayTeamGoals(int goals) {
        this.awayTeamGoals = goals;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public int getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public List<Integer> getHomeGoalScorers() {
        return homeGoalScorers;
    }


    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("home team", homeTeam);
        json.put("away team", awayTeam);
        json.put("away team goals", awayTeamGoals);
        json.put("home goal scorers", homeGoalScorersToJson());
        return json;
    }


    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray homeGoalScorersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (int scorer : homeGoalScorers) {
            jsonArray.put(scorer);
        }

        return jsonArray;
    }
}
