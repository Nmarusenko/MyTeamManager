package ui;

import persistence.JsonReader;
import persistence.JsonWriter;
import model.Game;
import model.Player;
import model.Team;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Represents the Team Manager App. The Team Manager App will allow the user to
// construct a team for themselves and add as many players and games as they want
// to their team
public class TeamManager {
    private static final String JSON_STORE = "./data/myFile.json";
    private Team myTeam;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: Runs the Team Manager
    public TeamManager() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runTeamManager();
    }

    // EFFECTS: Builds a team with name from user
    //          Opens team menu and gets next user input
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

    // EFFECTS: Displays Team menu options
    private void displayOptions(String name) {
        System.out.println("Chose an option below:");
        System.out.println("\tP -> Manage PLAYERS on " + name);
        System.out.println("\tG -> Manage GAMES for " + name);
        System.out.println("\tS -> SAVE " + name + " to file");
        System.out.println("\tL -> LOAD team from file");
        System.out.println("\tQ -> Quit");
    }

    // EFFECTS: Processes choice from Team menu
    private void processChoice(String choice) {
        if (choice.equals("p")) {
            playerMenu();
        } else if (choice.equals("g")) {
            gameMenu();
        } else if (choice.equals("s")) {
            saveTeam();
        } else if (choice.equals("l")) {
            loadTeam();
        } else {
            System.out.println("Invalid input, please try again");
        }
    }

    // EFFECTS: saves the team to file
    private void saveTeam() {
        try {
            jsonWriter.open();
            jsonWriter.write(myTeam);
            jsonWriter.close();
            System.out.println("Saved " + myTeam.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads team from file
    private void loadTeam() {
        try {
            myTeam = jsonReader.read();
            System.out.println("Loaded " + myTeam.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: Displays the Player menu and processes user input
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
            } else if (playerChoice.equals("s")) {
                checkPlayerGoals();
            } else if (playerChoice.equals("b")) {
                runPlayerMenu = false;
            } else {
                System.out.println("Invalid input, please try again");
            }
        }
    }

    // MODIFIES: Player, Team
    // EFFECTS: Constructs a player and adds them to the team
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

    // EFFECTS: asks for a player and then prints their goals
    private void checkPlayerGoals() {
        System.out.println("What is the player's name?");
        String name = input.next();
        System.out.println("What is " + name + "'s number?");
        int number = Integer.valueOf(input.next());
        Player player = myTeam.findPlayer(name, number);
        int goals = player.getGoals();
        System.out.println(name + " has " + goals + " goals");
    }

    // MODIFIES: Player
    // EFFECTS: Adds a game rating to the specified player
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

    // MODIFIES: Team
    // EFFECTS: Removes given player from the team
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

    // EFFECTS: View a list of all the players on the team
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

    // EFFECTS: Prints out the average rating of a specified player
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

    // EFFECTS: Displays the Player menu
    private void displayPlayerOptions() {
        System.out.println("What would you like to do with players on " + myTeam.getName() + "?");
        System.out.println("\tA -> Add a player to " + myTeam.getName());
        System.out.println("\tG -> Add a Game rating for a player");
        System.out.println("\tR -> Remove a player from " + myTeam.getName());
        System.out.println("\tV -> View all players on " + myTeam.getName());
        System.out.println("\tC -> Check average player ratings");
        System.out.println("\tS -> Check how many times a player has scored");
        System.out.println("\tB -> Go back, return to Team menu");
    }

    // EFFECTS: Displays the game menu and processes user input
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

    // EFFECTS: Displays Team menu
    private void displayGameOptions() {
        System.out.println("What would you like to do with games");
        System.out.println("\tC -> Create a new " + myTeam.getName() + " game");
        System.out.println("\tV -> View log of all " + myTeam.getName() + " games");
        System.out.println("\tB -> Go back, return to Team menu");
    }

    // EFFECTS: Prints out all games associated with the team
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

    // MODIFIES: Game, Team
    // EFFECTS: Creates a game and adds it to the team
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

