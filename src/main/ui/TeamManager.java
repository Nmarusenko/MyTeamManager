package ui;

import model.Game;
import model.Player;
import model.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TeamManager {
    private Team myTeam;
    private Scanner input;


    // EFFECTS: Runs the Team Manager
    public TeamManager() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    public void runTeamManager() {
        Boolean runTeamMenu = true;
        String teamName = null;
        System.out.println("What is your teams name?");
        teamName = input.next();
        myTeam = new Team(teamName);
        System.out.println("congratulations! " + teamName + " is now a team!");

        while (runTeamMenu) {
            displayOptions(teamName);
            String choice = null;
            choice = input.next();
            choice = choice.toLowerCase();

            if (choice.equals("q")) {
                runTeamMenu = false;
            } else {
                processChoice(choice);
            }

        }
        System.out.println("Thank you for using Team Manager. Have a nice day!");
    }

    // EFFECTS: Show menu options
    private void displayOptions(String name) {
        System.out.println("Chose an option below:");
        System.out.println("\tP -> manage PLAYERS on " + name);
        System.out.println("\tG -> Manage GAMES for " + name);
        System.out.println("\tQ -> Quit");
    }

    // EFFECTS: processes choice from team menu
    private void processChoice(String choice) {
        if (choice.equals("p")) {
            playerMenu();
        } else if (choice.equals("g")) {
            gameMenu();
        } else {
            System.out.println("Invalid input, please try again");
        }
    }

    // EFFECTS: all functionality for adding players
    private void playerMenu() {
        Boolean runPlayerMenu = true;
        while (runPlayerMenu) {
            displayPlayerOptions();
            String playerChoice = input.next();
            playerChoice = playerChoice.toLowerCase();
            if (playerChoice.equals("a")) {
                addPlayer();
            } else if (playerChoice.equals("g")) {
                addGameRating();
            } else if (playerChoice.equals("r")) {
                removePlayer();
            } else if (playerChoice.equals("v")) {
                viewPlayers();
            } else if (playerChoice.equals("c")) {
                checkAveragePlayerRating();
            } else if (playerChoice.equals("b")) {
                runPlayerMenu = false;
            } else {
                System.out.println("Invalid input, please try again");
            }
        }
    }

    private void addPlayer() {
        System.out.println("What is the player's name?");
        String name = input.next();
        System.out.println("What is " + name + "'s number?");
        int number = Integer.valueOf(input.next());
        Player player = new Player(name, number);
        Boolean success = myTeam.addPlayer(player);
        if (success) {
            System.out.println(name + ", " + number + " - added to " + myTeam.getName() + ", returning to Player menu");
        } else {
            System.out.println("Unable to add player, shares same number as another player on " + myTeam.getName());
        }
    }

    private void addGameRating() {
        System.out.println("What is the player's name?");
        String name = input.next();
        System.out.println("What is " + name + "'s number?");
        int number = Integer.valueOf(input.next());
        Player player = myTeam.findPlayer(name, number);
        if (player == null) {
            System.out.println("Unable to add rating for player, check name and number");
        } else {
            System.out.println("Give player rating between 0-10");
            double rating = Double.valueOf(input.next());
            Boolean success = player.addRating(rating);
            if (success) {
                System.out.println("Successfully added rating");
            } else {
                System.out.println("Unable to successfully add rating");
            }
        }
    }


    private void removePlayer() {
        System.out.println("What is the player's name?");
        String name = input.next();
        System.out.println("What is " + name + "'s number?");
        int number = Integer.valueOf(input.next());
        Boolean success = myTeam.removePlayer(name, number);
        if (success) {
            System.out.println("Successfully removed " + name + ", returning to Player menu");
        } else {
            System.out.println("Could not remove player, check name and number is correct");
        }
    }

    private void viewPlayers() {
        List<String> list = myTeam.viewAllPlayers();
        if (list.size() == 0) {
            System.out.println("There are no players on this team");
        } else {
            System.out.println("Here are the players on " + myTeam.getName() + ":");
            for (String player : list) {
                System.out.println(player);
            }
        }
        System.out.println("Returning to Player menu");
    }

    private void checkAveragePlayerRating() {
        System.out.println("What is the player's name?");
        String name = input.next();
        System.out.println("What is " + name + "'s number?");
        int number = Integer.valueOf(input.next());
        Player player = myTeam.findPlayer(name, number);
        if (player == null) {
            System.out.println("Unable to find player, returning to Player menu");
        } else {
            double rating = player.averageRating();
            System.out.println("The average rating for " + name + " is " + rating);
            System.out.println("Returning to Player menu");
        }
    }

    private void displayPlayerOptions() {
        System.out.println("What would you like to do with players on " + myTeam.getName());
        System.out.println("\tA -> Add a player to " + myTeam.getName());
        System.out.println("\tG -> Add a Game rating for a player");
        System.out.println("\tR -> Remove a player from " + myTeam.getName());
        System.out.println("\tV -> View all players on " + myTeam.getName());
        System.out.println("\tC -> Check average player ratings");
        System.out.println("\tB -> Go back, return to Team menu");
    }

    // EFFECTS: all functionality for games
    private void gameMenu() {
        Boolean runGameMenu = true;

        while (runGameMenu) {
            displayGameOptions();
            String gameChoice = null;
            gameChoice = input.next();
            gameChoice = gameChoice.toLowerCase();
            if (gameChoice.equals("c")) {
                createGame();
            } else if (gameChoice.equals("v")) {
                viewGames();
            } else if (gameChoice.equals("b")) {
                runGameMenu = false;
                System.out.println("Returning to Team menu!");
            } else {
                System.out.println("invalid input, please try again");
            }
        }
    }

    private void displayGameOptions() {
        System.out.println("What would you like to do with games");
        System.out.println("\tC -> Create a new " + myTeam.getName() + " game");
        System.out.println("\tV -> View log of all " + myTeam.getName() + " games");
        System.out.println("\tB -> Go back, return to Team menu");
    }

    private void viewGames() {
        List<Game> list = myTeam.getGames();
        int size = list.size();
        if (size == 0) {
            System.out.println("There are no games associated with " + myTeam.getName());
        } else {
            System.out.println("Here are all the games for " + myTeam.getName() + ":");
            for (Game game : myTeam.getGames()) {
                System.out.println("\t" + game.displayGame());
            }
        }
    }

    private void createGame() {
        int awayGoals = 0;
        int homeGoals = 0;
        List<Integer> homeGoalScorers = new ArrayList<Integer>();
        System.out.println("What is the opponent team's name?");
        String awayName = input.next();
        Game game = new Game(myTeam.getName(), awayName);
        System.out.println("How many goals did " + awayName + " score?");
        awayGoals = Integer.valueOf(input.next());
        System.out.println("How many goals did " + myTeam.getName() + " score?");
        homeGoals = Integer.valueOf(input.next());
        for (int i = 1; i <= homeGoals; i++) {
            int scorer = 0;
            System.out.println("Who (jersey number) scored goal #" + i + " for the " + myTeam.getName() + "?");
            scorer = Integer.valueOf(input.next());
            homeGoalScorers.add(scorer);
        }
        game.setHomeGoals(homeGoalScorers);
        game.setAwayTeamGoals(awayGoals);
        myTeam.addGame(game);
        System.out.println("The following has been added to " + myTeam.getName() + " games");
        System.out.println("\t" + game.displayGame() + " with " + myTeam.getName()
                + " goalscorers: " + homeGoalScorers);
        System.out.println("Returning to Game menu");
    }
}

