package ui;

import javax.swing.*;
import java.awt.*;

public class NameDisplayUI extends JPanel {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 30;
    private static final int TEXT_INDENT = 30;
    private String displayString;
    private Color fillColor;

    /**
     * Constructor creates interface to display status of alarm
     */
    public NameDisplayUI(String name) {
        displayString = name;
        fillColor = Color.gray;
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(fillColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.drawString("Team Name: " + displayString, 20, 20);
    }

    public void update(String name) {
        displayString = name;
        repaint();
    }

}

