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

public class TeamManagerGUI extends JFrame {
    private static final String JSON_STORE = "./data/myFile.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String FILE_DESCRIPTOR = "...file";
    private static final String SCREEN_DESCRIPTOR = "...screen";
    private Team team;
    private JDesktopPane desktop;
    private JInternalFrame controlPanel;
    private NameDisplayUI nameDisplayUI;




    public TeamManagerGUI() {
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
        addTeamNameDisplayPanel();

        controlPanel.pack();
        controlPanel.setVisible(true);
        desktop.add(controlPanel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();
        setVisible(true);
    }


    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6,2));
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
        buttonPanel.add(new JButton(new SaveToFileAction()));
        buttonPanel.add(new JButton(new LoadFromFileAction()));

        controlPanel.add(buttonPanel, BorderLayout.WEST);
    }




    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            TeamManagerGUI.this.requestFocusInWindow();
        }
    }

    private class AddPlayerAction extends AbstractAction {

        AddPlayerAction() {
            super("Add Player");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            List<String> nameAndNumber = useDoublePanel();
            String name = nameAndNumber.get(0);
            Integer num = Integer.valueOf(nameAndNumber.get(1));
            Player player = new Player(name, num);
            Boolean bool = team.addPlayer(player);
            if (!bool) {
                JOptionPane.showMessageDialog(null,
                        "Player number currently in use", "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class AddGameRatingAction extends AbstractAction {

        AddGameRatingAction() {
            super("Add Game Rating");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String name = "Default"; // edit for user input
            int number = 0; // edit for use input
            Double rating = 1.3;
            Player player = team.findPlayer(name, number);
            if (player == null) {
                JOptionPane.showMessageDialog(null, "Player does not exist", "System Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                Boolean bool = player.addRating(rating);
                if (!bool) {
                    JOptionPane.showMessageDialog(null,
                            "Rating not in range [0, 10]", "System Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class RemovePlayerAction extends AbstractAction {

        RemovePlayerAction() {
            super("Remove Player");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String name = "Default"; // edit for user input
            int number = 0; // edit for use input
            Boolean bool = team.removePlayer(name, number);
            if (!bool) {
                JOptionPane.showMessageDialog(null,
                        "Unable to find player with name and number", "System Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class ViewPlayersAction extends AbstractAction {

        ViewPlayersAction() {
            super("View all Players");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            List<String> players = team.viewAllPlayers();
            // display the players
        }
    }

    private class AverageRatingAction extends AbstractAction {

        AverageRatingAction() {
            super("Player Average Rating");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String name = "Default"; // edit for user input
            int number = 0; // edit for use input
            Player player = team.findPlayer(name, number);
            if (player == null) {
                JOptionPane.showMessageDialog(null,
                        "Unable to find player with name and number", "System Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                Double num = player.averageRating();
                // somehow show num to user
            }
        }
    }

    private class GoalsScoredAction extends AbstractAction {

        GoalsScoredAction() {
            super("Player Goals Scored");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String name = "Default"; // edit for user input
            int number = 0; // edit for use input
            Player player = team.findPlayer(name, number);
            if (player == null) {
                JOptionPane.showMessageDialog(null,
                        "Unable to find player with name and number", "System Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                int num = player.getGoals();
                // display num to user
            }
        }
    }

    private class CreateGameAction extends AbstractAction {

        CreateGameAction() {
            super("Create Game");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String awayName = "away";
            int awayGoals = 0;
            List<Integer> homeScorers = new ArrayList<>();

            Game game = new Game(team.getName(), awayName);
            game.setHomeGoals(homeScorers);
            game.setAwayTeamGoals(awayGoals);

            team.addGame(game);
        }
    }

    private class ViewGamesAction extends AbstractAction {

        ViewGamesAction() {
            super("View Games");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            List<String> display = team.displayGames();
            // Show display to the user
        }
    }

    private class SetTeamNameAction extends AbstractAction {

        SetTeamNameAction() {
            super("Set Team Name");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            String name = JOptionPane.showInputDialog("What is your new team name?");
            team.setName(name);
            nameDisplayUI.update(name);
        }
    }

    private class CalculateTeamPointsAction extends AbstractAction {

        CalculateTeamPointsAction() {
            super("Calculate Team Points");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            int points = team.getPoints();
            // display points
        }
    }

    private class SaveToFileAction extends AbstractAction {

        SaveToFileAction() {
            super("Save to File");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            saveTeam();
        }
    }

    private class LoadFromFileAction extends AbstractAction {

        LoadFromFileAction() {
            super("Load From File");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            loadTeam();
        }
    }

    // EFFECTS: saves the team to file
    private void saveTeam() {
        try {
            jsonWriter.open();
            jsonWriter.write(team);
            jsonWriter.close();
            // System.out.println("Saved " + team.getName() + " to " + JSON_STORE);
            // Show that everything went well?
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to write to file: " + JSON_STORE, "System Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads team from file
    private void loadTeam() {
        try {
            team = jsonReader.read();
            // System.out.println("Loaded " + team.getName() + " from " + JSON_STORE);
            // maybe display success?
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to read from file: " + JSON_STORE, "System Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addTeamNameDisplayPanel() {
        nameDisplayUI = new NameDisplayUI(team.getName());
        controlPanel.add(nameDisplayUI, BorderLayout.NORTH);
    }

    // Code inspired from online to make a double input panel
    public List<String> useDoublePanel() {
        JTextField fieldX = new JTextField(5);
        JTextField fieldY = new JTextField(5);
        JPanel doublePanel = new JPanel();
        doublePanel.add(new JLabel("Player Name: "));
        doublePanel.add(fieldX);
        doublePanel.add(Box.createHorizontalStrut(15));
        doublePanel.add(new JLabel("PlayerNumber: "));
        doublePanel.add(fieldY);
        List<String> ret = new ArrayList<String>();
        JOptionPane.showConfirmDialog(null, doublePanel,
                "Please Enter Player Name and Number", JOptionPane.OK_CANCEL_OPTION);
        ret.add(fieldX.getText());
        ret.add(fieldY.getText());
        return ret;
    }

}
