package model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    // Represents a player on a team
    private String name;
    private int goals;
    private int jerseyNum;
    private List<Double> ratings;

    public Player(String name, int number) {
        this.name = name;
        this.jerseyNum = number;
        this.goals = 0;
        this.ratings = new ArrayList<Double>();
    }


    // EFFECTS: if ratings is empty return 0, otherwise computer average
    //          of ratings and returns to 1 decimal place.
    public double averageRating() {
        return 1; // STUB
    }

    // REQUIRES: number is in [0, 10]
    // MODIFIES: this
    // EFFECTS: adds number to list of game ratings
    public void addRating(double number) {

    }





    // get methods

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


}
