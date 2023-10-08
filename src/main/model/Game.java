package model;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private String homeTeam;
    private String awayTeam;
    private int homeTeamGoals;
    private int awayTeamGoals;
    private List<Integer> homeGoalScorers;

    public Game(String teamHome, String teamAway){
        this.homeTeam = teamHome;
        this.awayTeam = teamAway;
        this.homeTeamGoals = 0;
        this.awayTeamGoals = 0;
        this.homeGoalScorers = new ArrayList<Integer>();
    }


    public String displayGame() {
        int homeGoals = getHomeTeamGoals();
        int awayGoals = getAwayTeamGoals();
        return getHomeTeam() + " " + homeGoals + "-" + awayGoals + " " + getAwayTeam();
    }


    // MODIFIES: this
    // EFFECTS: sets homeTeamGoals and scorers
    public void setHomeGoals(List<Integer> scorers) {
        // STUB
    }

    public void setAwayTeamGoals(int goals) {
        this.awayTeamGoals = goals;
    }


    // get methods

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




}
