package ui;

import model.Game;
import model.Player;
import model.Team;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// Represents the Team Manager App. The Team Manager App will allow the user to
// construct a team for themselves and add as many players and games as they want
// to their team. This is the GUI for the app
public class TeamManagerGUI extends JFrame {
    private static final String JSON_STORE = "./data/myFile.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private Team team;
    private JDesktopPane desktop;
    private JInternalFrame controlPanel;
    private NameDisplayUI nameDisplayUI;
    private JPanel buttonPanel;
    private JLabel logoLabel;
    private JList currDisplay;
    private DefaultListModel<String> stringList;



    //EFFECTS: runs the team manager
    public TeamManagerGUI() {
        buttonPanel = new JPanel();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        team = new Team("MyTeamName");
        desktop = new JDesktopPane();
        desktop.addMouseListener(new DesktopFocusAction());
        controlPanel = new JInternalFrame("Control Panel",
                false, false, false, false);
        controlPanel.setLayout(new BorderLayout());

        setContentPane(desktop);
        setTitle("My Team Manager");
        setSize(WIDTH, HEIGHT);

        addButtonPanel();
        createPlayersAndGamesPanel();
        addTeamNameAndLogo();

        controlPanel.pack();
        controlPanel.setVisible(true);
        desktop.add(controlPanel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();
        setVisible(true);
    }

    // EFFECTS: Display the current state of the team in an updating panel
    private void createPlayersAndGamesPanel() {
        stringList = new DefaultListModel<>();
        stringList.addElement("Players: ");
        currDisplay = new JList(stringList);
        JScrollPane scroll = new JScrollPane(currDisplay);
        scroll.setVisible(true);
        controlPanel.add(scroll);
        System.out.println(stringList.getSize());
    }

    // EFFECTS: Updates the players panel when an action happens to a player
    private void updatePlayersPanel() {
        stringList.clear();
        stringList.addElement("Players: ");
        for (Player p : team.getPlayers()) {
            stringList.addElement(p.getName() + " (" + p.getJerseyNum() + ")" + " - Rating: " + p.averageRating()
                    + " - Goals: " + p.getGoals());
        }
    }


    // EFFECTS: Centers the panel on the screen
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    // EFFECTS: Adds all the buttons to the panel
    private void addButtonPanel() {
        buttonPanel.setLayout(new GridLayout(7, 2));
        buttonPanel.add(new JButton(new AddPlayerAction()));
        buttonPanel.add(new JButton(new AddGameRatingAction()));
        buttonPanel.add(new JButton(new RemovePlayerAction()));
        buttonPanel.add(new JButton(new ViewPlayersAction()));
        buttonPanel.add(new JButton(new AverageRatingAction()));
        buttonPanel.add(new JButton(new GoalsScoredAction()));
        buttonPanel.add(new JButton(new CreateGameAction()));
        buttonPanel.add(new JButton(new ViewGamesAction()));
        buttonPanel.add(new JButton(new SetTeamNameAction()));
        buttonPanel.add(new JButton(new CalculateTeamPointsAction()));
        buttonPanel.add(new JButton(new FilterByRatingAction()));
        buttonPanel.add(new JButton(new FilterByGoalsAction()));
        buttonPanel.add(new JButton(new SaveToFileAction()));
        buttonPanel.add(new JButton(new LoadFromFileAction()));
        controlPanel.add(buttonPanel, BorderLayout.WEST);
    }


    // Sets up the mouse
    private class DesktopFocusAction extends MouseAdapter {
        @Override
        //EFFECTS: makes the mouse click work
        public void mouseClicked(MouseEvent e) {
            TeamManagerGUI.this.requestFocusInWindow();
        }
    }

    // Sets up the action for adding a player
    private class AddPlayerAction extends AbstractAction {

        AddPlayerAction() {
            super("Add Player");
        }

        @Override
        // EFFECTS: Action performed when adding a player
        public void actionPerformed(ActionEvent evt) {
            List<String> nameAndNumber = useDoublePanelPlayers("Enter Player Name and Number");
            String name = nameAndNumber.get(0);
            Integer num = Integer.valueOf(nameAndNumber.get(1));
            Player player = new Player(name, num);
            Boolean bool = team.addPlayer(player);
            if (!bool) {
                JOptionPane.showMessageDialog(null,
                        "Player number currently in use", "System Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        player.getName() + ", " + player.getJerseyNum() + " - added successfully to "
                                + team.getName(), "Success",
                        JOptionPane.PLAIN_MESSAGE);
            }
            updatePlayersPanel();
        }
    }

    // Sets up the action for adding a game rating
    private class AddGameRatingAction extends AbstractAction {

        AddGameRatingAction() {
            super("Add Game Rating");
        }

        @Override
        // EFFECTS: Action performed when adding a game rating
        public void actionPerformed(ActionEvent evt) {
            List<String> nameAndNumber = useDoublePanelPlayers("Find player by Name and Number");
            String name = nameAndNumber.get(0);
            Integer number = Integer.valueOf(nameAndNumber.get(1));
            Player player = team.findPlayer(name, number);
            if (player == null) {
                JOptionPane.showMessageDialog(null, "Player does not exist", "System Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                Double rating = Double.valueOf(JOptionPane.showInputDialog("Give " + player.getName()
                        + " a rating between [1, 10]"));
                Boolean bool = player.addRating(rating);
                if (!bool) {
                    JOptionPane.showMessageDialog(null,
                            "Rating not in range [0, 10]", "System Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            updatePlayersPanel();
        }
    }

    // Sets up the action for removing a player
    private class RemovePlayerAction extends AbstractAction {

        RemovePlayerAction() {
            super("Remove Player");
        }

        @Override
        // EFFECTS: Action performed when removing a player
        public void actionPerformed(ActionEvent evt) {
            List<String> nameAndNumber = useDoublePanelPlayers("Find player by Name and Number");
            String name = nameAndNumber.get(0);
            Integer number = Integer.valueOf(nameAndNumber.get(1));
            Boolean bool = team.removePlayer(name, number);
            if (!bool) {
                JOptionPane.showMessageDialog(null,
                        "Unable to find player with name and number", "System Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Successfully removed " + name + " from " + team.getName(), "Success",
                        JOptionPane.PLAIN_MESSAGE);
            }
            updatePlayersPanel();
        }
    }

    // Sets up the action for viewing players
    private class ViewPlayersAction extends AbstractAction {

        ViewPlayersAction() {
            super("View all Players");
        }

        @Override
        // EFFECTS: Action performed when viewing all players
        public void actionPerformed(ActionEvent evt) {
            List<String> players = team.viewAllPlayers();
            String display = display(players);
            JOptionPane.showInternalMessageDialog(null, display,
                    "Showing all Players", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Sets up the action for finding the average rating of a player
    private class AverageRatingAction extends AbstractAction {

        AverageRatingAction() {
            super("Player Average Rating");
        }

        @Override
        // EFFECTS: Action performed when finding average rating of a player
        public void actionPerformed(ActionEvent evt) {
            List<String> nameAndNumber = useDoublePanelPlayers("Find player by Name and Number");
            String name = nameAndNumber.get(0);
            Integer number = Integer.valueOf(nameAndNumber.get(1));
            Player player = team.findPlayer(name, number);
            if (player == null) {
                JOptionPane.showMessageDialog(null,
                        "Unable to find player with name and number", "System Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                Double num = player.averageRating();
                JOptionPane.showInternalMessageDialog(null, name + " has an average game rating of " + num,
                        "Average Rating", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // Sets up the action for finding the goals scored of a player
    private class GoalsScoredAction extends AbstractAction {

        GoalsScoredAction() {
            super("Player Goals Scored");
        }

        @Override
        // EFFECTS: Action performed when finding goals scored of a player
        public void actionPerformed(ActionEvent evt) {
            List<String> nameAndNumber = useDoublePanelPlayers("Find player by Name and Number");
            String name = nameAndNumber.get(0);
            Integer number = Integer.valueOf(nameAndNumber.get(1));
            Player player = team.findPlayer(name, number);
            if (player == null) {
                JOptionPane.showMessageDialog(null,
                        "Unable to find player with name and number", "System Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                int num = player.getGoals();
                JOptionPane.showInternalMessageDialog(null, name + " has " + num + " goals",
                        "Total Goals", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // Sets up the action for creating a game
    private class CreateGameAction extends AbstractAction {

        CreateGameAction() {
            super("Create Game");
        }

        @Override
        // EFFECTS: Action performed to create a game
        public void actionPerformed(ActionEvent evt) {
            List gameInfo = useDoublePanelGames();
            String awayName = (String) gameInfo.get(0);
            Integer awayGoals = Integer.valueOf((String) gameInfo.get(1));
            List<Integer> homeScorers = (List<Integer>) gameInfo.get(2);

            Game game = new Game(team.getName(), awayName);
            game.setHomeGoals(homeScorers);
            game.setAwayTeamGoals(awayGoals);
            team.addGame(game);
            JOptionPane.showMessageDialog(null,
                    "Successfully added the game: " + game.displayGame(), "Success",
                    JOptionPane.PLAIN_MESSAGE);
            updatePlayersPanel();
        }
    }

    // Sets up the action for viewing all games
    private class ViewGamesAction extends AbstractAction {

        ViewGamesAction() {
            super("View Games");
        }

        @Override
        // EFFECTS: Action performed when viewing all games
        public void actionPerformed(ActionEvent evt) {
            List<String> games = team.displayGames();
            String display = display(games);
            JOptionPane.showInternalMessageDialog(null, display,
                    "Showing all Games", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    // Sets up the action for changing the team name
    private class SetTeamNameAction extends AbstractAction {

        SetTeamNameAction() {
            super("Set Team Name");
        }

        @Override
        // EFFECTS: Action performed when setting a new team name
        public void actionPerformed(ActionEvent evt) {
            String name = JOptionPane.showInputDialog("What is your new team name?");
            team.setName(name);
            nameDisplayUI.update(name);
        }
    }

    // Sets up the action for calculating the teams points
    private class CalculateTeamPointsAction extends AbstractAction {

        CalculateTeamPointsAction() {
            super("Calculate Team Points");
        }

        @Override
        // EFFECTS: Action performed when calculating a teams points
        public void actionPerformed(ActionEvent evt) {
            int points = team.getPoints();
            JOptionPane.showInternalMessageDialog(null, team.getName() + " has "
                            + points + " points from their games",
                    "+3 points for win, +1 for tie, +0 for loss", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Sets up the action for saving to file
    private class SaveToFileAction extends AbstractAction {

        SaveToFileAction() {
            super("Save to File");
        }

        @Override
        // EFFECTS: Action performed when saving state to file
        public void actionPerformed(ActionEvent evt) {
            int num = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to Save To File?", "Save to File", JOptionPane.YES_NO_OPTION);
            System.out.println(num);
            if (num == 0) {
                saveTeam();
            }
            updatePlayersPanel();
        }
    }

    // Sets up the action for loading from file
    private class LoadFromFileAction extends AbstractAction {

        LoadFromFileAction() {
            super("Load From File");
        }

        @Override
        // EFFECTS: Action performed when loading a new state from file
        public void actionPerformed(ActionEvent evt) {
            int num = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to Load From File?", "Save to File", JOptionPane.YES_NO_OPTION);
            if (num == 0) {
                loadTeam();
                nameDisplayUI.update(team.getName());
            }
            updatePlayersPanel();
        }
    }


    // Sets up the action for filtering all players by a certain rating
    private class FilterByRatingAction extends AbstractAction {

        FilterByRatingAction() {
            super("Filter by min Average Rating");
        }

        @Override
        // EFFECTS: Action performed when filtering players by a min rating
        public void actionPerformed(ActionEvent evt) {
            Double minRating = Double.valueOf(
                    JOptionPane.showInputDialog("What is the minimum rating you would like to filter by?"));
            List<String> names = team.filterByMinRating(minRating);
            String display = display(names);
            JOptionPane.showInternalMessageDialog(null, display,
                    "All players with min rating of " + minRating, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Sets up the action for filtering a list of players by number of goals scored
    private class FilterByGoalsAction extends AbstractAction {

        FilterByGoalsAction() {
            super("Filter by min goals scored");
        }

        @Override
        // EFFECTS: Action performed when filtering players by a min number of goals
        public void actionPerformed(ActionEvent evt) {
            Integer minGoals = Integer.valueOf(
                    JOptionPane.showInputDialog("What is the minimum number of goals you would like to filter by?"));
            List<String> names = team.filterByMinGoals(minGoals);
            String display = display(names);
            JOptionPane.showInternalMessageDialog(null, display,
                    "All players with min " + minGoals + " goals", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // EFFECTS: saves the team to file
    private void saveTeam() {
        try {
            jsonWriter.open();
            jsonWriter.write(team);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to write to file: " + JSON_STORE, "System Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        updatePlayersPanel();
    }

    // MODIFIES: this
    // EFFECTS: loads team from file
    private void loadTeam() {
        try {
            team = jsonReader.read();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to read from file: " + JSON_STORE, "System Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: Adds the team name at the top of the screen and the logo at the bottom
    private void addTeamNameAndLogo() {
        nameDisplayUI = new NameDisplayUI(team.getName());
        controlPanel.add(nameDisplayUI, BorderLayout.NORTH);
        ImageIcon logo = new ImageIcon("data/finallogo.PNG");
        logoLabel = new JLabel(logo);
        logoLabel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        controlPanel.add(logoLabel, BorderLayout.SOUTH);
    }

    // NOTE: Code inspired from online to make a double input panel
    // EFFECTS: gets required name and number from user
    public List<String> useDoublePanelPlayers(String message) {
        JTextField fieldX = new JTextField(5);
        JTextField fieldY = new JTextField(5);
        JPanel doublePanel = new JPanel();
        doublePanel.add(new JLabel("Player Name: "));
        doublePanel.add(fieldX);
        doublePanel.add(Box.createHorizontalStrut(15));
        doublePanel.add(new JLabel("Player Number: "));
        doublePanel.add(fieldY);
        List<String> ret = new ArrayList<String>();
        JOptionPane.showConfirmDialog(null, doublePanel,
                message, JOptionPane.OK_CANCEL_OPTION);
        ret.add(fieldX.getText());
        ret.add(fieldY.getText());
        return ret;
    }

    // EFFECTS: gets information to create a game from the user
    public List useDoublePanelGames() {
        String message = "Enter away team name and goals scored";
        JTextField fieldX = new JTextField(5);
        JTextField fieldY = new JTextField(5);
        JPanel doublePanel = new JPanel();
        doublePanel.add(new JLabel("Away Team Name: "));
        doublePanel.add(fieldX);
        doublePanel.add(Box.createHorizontalStrut(15));
        doublePanel.add(new JLabel("Away Team Goals: "));
        doublePanel.add(fieldY);
        List ret = new ArrayList();
        JOptionPane.showConfirmDialog(null, doublePanel,
                message, JOptionPane.OK_CANCEL_OPTION);
        ret.add(fieldX.getText());
        ret.add(fieldY.getText());
        List<Integer> scorers = getHomeScorers();
        ret.add(scorers);
        return ret;
    }

    // EFFECTS: Gets the jersey numbers of the goalscorers from user
    public List<Integer> getHomeScorers() {
        List<Integer> scorers = new ArrayList<Integer>();
        Integer goals = Integer.valueOf(
                JOptionPane.showInputDialog("How many goals did " + team.getName() + " score?"));
        for (int i = 0; i < goals; i++) {
            Integer scorer = Integer.valueOf(
                    JOptionPane.showInputDialog("Who (jersey number) scored goal #" + (i + 1) + "?"));
            scorers.add(scorer);
        }
        return scorers;
    }


    // EFFECTS: displays the names in a single string
    public String display(List<String> names) {
        String display = "";
        for (String s : names) {
            display = display + s + "\n";
        }
        return display;
    }


}
