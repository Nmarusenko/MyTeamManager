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
    private Team team;
    private JDesktopPane desktop;
    private JInternalFrame controlPanel;
    private NameDisplayUI nameDisplayUI;
    private JPanel buttonPanel;
    private JLabel logoLabel;




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
        addTeamNameAndLogo();

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
        buttonPanel.setLayout(new GridLayout(4,3));
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
        }
    }

    private class AddGameRatingAction extends AbstractAction {

        AddGameRatingAction() {
            super("Add Game Rating");
        }

        @Override
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
        }
    }

    private class RemovePlayerAction extends AbstractAction {

        RemovePlayerAction() {
            super("Remove Player");
        }

        @Override
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
        }
    }

    private class ViewPlayersAction extends AbstractAction {

        ViewPlayersAction() {
            super("View all Players");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            List<String> players = team.viewAllPlayers();
            String display = display(players);
            JOptionPane.showInternalMessageDialog(null, display,
                    "Showing all Players", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class AverageRatingAction extends AbstractAction {

        AverageRatingAction() {
            super("Player Average Rating");
        }

        @Override
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

    private class GoalsScoredAction extends AbstractAction {

        GoalsScoredAction() {
            super("Player Goals Scored");
        }

        @Override
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

    private class CreateGameAction extends AbstractAction {

        CreateGameAction() {
            super("Create Game");
        }

        @Override
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
        }
    }

    private class ViewGamesAction extends AbstractAction {

        ViewGamesAction() {
            super("View Games");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            List<String> games = team.displayGames();
            String display = display(games);
            JOptionPane.showInternalMessageDialog(null, display,
                    "Showing all Games", JOptionPane.INFORMATION_MESSAGE);
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
            JOptionPane.showInternalMessageDialog(null,team.getName() + " has "
                            + points + " points from their games",
                    "+3 points for win, +1 for tie, +0 for loss", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class SaveToFileAction extends AbstractAction {

        SaveToFileAction() {
            super("Save to File");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            int num = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to Save To File?", "Save to File", JOptionPane.YES_NO_OPTION);
            System.out.println(num);
            if (num == 0) {
                saveTeam();
            }
        }
    }

    private class LoadFromFileAction extends AbstractAction {

        LoadFromFileAction() {
            super("Load From File");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            int num = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to Load From File?", "Save to File", JOptionPane.YES_NO_OPTION);
            if (num == 0) {
                loadTeam();
                nameDisplayUI.update(team.getName());
            }
        }
    }


    private class FilterByRatingAction extends AbstractAction {

        FilterByRatingAction() {
            super("Filter by min Average Rating");
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            Double minRating = Double.valueOf(
                    JOptionPane.showInputDialog("What is the minimum rating you would like to filter by?"));
            List<String> names = team.filterByMinRating(minRating);
            String display = display(names);
            JOptionPane.showInternalMessageDialog(null, display,
                    "All players with min rating of " + minRating, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class FilterByGoalsAction extends AbstractAction {

        FilterByGoalsAction() {
            super("Filter by min goals scored");
        }

        @Override
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

    private void addTeamNameAndLogo() {
        nameDisplayUI = new NameDisplayUI(team.getName());
        controlPanel.add(nameDisplayUI, BorderLayout.NORTH);
        //ImageIcon logo = new ImageIcon(new ImageIcon("data/logo.png").getImage().getScaledInstance(270, 200, Image.SCALE_DEFAULT));
        ImageIcon logo = new ImageIcon("data/newlogo.png");
        logoLabel = new JLabel(logo);
        logoLabel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        controlPanel.add(logoLabel, BorderLayout.SOUTH);
    }

    // Code inspired from online to make a double input panel
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


    public String display(List<String> names) {
        String display = "";
        for (String s : names) {
            display = display + s + "\n";
        }
        return display;
    }




}
