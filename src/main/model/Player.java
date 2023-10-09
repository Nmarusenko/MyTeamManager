package model;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

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

    // REQUIRES: ratings must not be empty
    // EFFECTS: computes average of ratings and returns to 1 decimal place rounding down
    public double averageRating() {
        double sum = 0;
        for (double number : ratings) {
            sum = sum + number;
        }
        int average = (int) (10* sum / ratings.size());
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
            return true;
        }
        else {
            return false;
        }
    }





    // get methods

    public void setGoals(int goals) {
        this.goals = goals;
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


}
