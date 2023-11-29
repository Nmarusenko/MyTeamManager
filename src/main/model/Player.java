package model;

import persistence.Writable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents a player of a team. A player has a name, jersey number, amount of goals scored
// and a list of player ratings.
public class Player implements Writable {

    private String name;
    private int goals;
    private int jerseyNum;
    private List<Double> ratings;

    // Constructs a player with a given name, given jersey number, no goals and no match ratings
    public Player(String name, int number) {
        this.name = name;
        this.jerseyNum = number;
        this.goals = 0;
        this.ratings = new ArrayList<Double>();
    }

    // REQUIRES: ratings must not be empty
    // EFFECTS: computes average of ratings and returns to 1 decimal place rounding down
    public double averageRating() {
        double sum = 0;
        for (double number : ratings) {
            sum = sum + number;
        }
        int average = (int) (10 * sum / ratings.size());
        double roundedAverage = (double) average / 10;
        return roundedAverage;

    }


    // MODIFIES: this
    // EFFECTS: adds number to list of game ratings
    //          returns true if added successfully, false otherwise.
    //          successful if number is in [0, 10]
    public Boolean addRating(double number) {
        if (0 <= number && number <= 10) {
            ratings.add(number);
            EventLog.getInstance().logEvent(new Event("Rating of " + number + " added for " + name));
            return true;
        } else {
            return false;
        }
    }


    // REQUIRES: goals is non-negative
    // MODIFIES: this
    // EFFECTS: Sets the goals for a player
    public void setGoals(int goals) {
        this.goals = goals;
    }

    public void setRatings(List<Double> ratings) {
        this.ratings = ratings;
    }

    public String getName() {
        return name;
    }

    public int getGoals() {
        return goals;
    }

    public int getJerseyNum() {
        return jerseyNum;
    }

    public List<Double> getRatings() {
        return ratings;
    }


    // EFFECTS: returns a player as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("player name", name);
        json.put("player number", jerseyNum);
        json.put("player ratings", ratingsToJson());
        return json;
    }


    // EFFECTS: returns ratings from a player into a JSON array
    private JSONArray ratingsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Double rating : ratings) {
            jsonArray.put(rating);
        }

        return jsonArray;
    }
}
