package View;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {

    private int width;
    private int height;
    private GUI gui;

    public MainMenu(GUI gui, int width, int height) {
        super(null);
        this.width = width;
        this.height = height;
        this.gui = gui;
        this.setSize(width, height);
        this.setBackground(Color.black);
        this.setVisible(true);
        setup();

    }

    private void setup() {
        JLabel title = new JLabel("MainMenu");
        title.setLocation(10,10);
        title.setSize(200,50);
        title.setForeground(Color.white);
        add(title);

        if (gui.isLoggedIn())  {
            String username = gui.getController().getCurrentUser().getUsername();
            JLabel isUserLoggedInLabel = new JLabel("Logged in as " + username);
            isUserLoggedInLabel.setLocation(650,10);
            isUserLoggedInLabel.setSize(300,50);
            isUserLoggedInLabel.setForeground(Color.white);
            add(isUserLoggedInLabel);
        }
        else {
            JLabel isUserLoggedInLabel = new JLabel("Playing as GUEST (not logged in)");
            isUserLoggedInLabel.setLocation(650,10);
            isUserLoggedInLabel.setSize(300,50);
            isUserLoggedInLabel.setForeground(Color.white);
            add(isUserLoggedInLabel);
        }

        int[] buttonSize = {400,100};
        int startPositionY = 100;
        int spacingY = 150;

        Font buttonFont= new Font("Dialog", Font.BOLD, 24);

        JLabel ruleLabel = new JLabel("Game rules to be shown here");
        ruleLabel.setLocation( width/2-buttonSize[0]/2 , startPositionY );
        ruleLabel.setSize(400,50);
        ruleLabel.setFont(buttonFont);
        ruleLabel.setForeground(Color.white);
        add(ruleLabel);

        JButton startGameButton = new JButton("PLAY");
        startGameButton.setEnabled(true);
        startGameButton.setBackground(Color.DARK_GRAY);
        startGameButton.setForeground(Color.white);
        startGameButton.setFont(buttonFont);
        startGameButton.setSize(buttonSize[0], buttonSize[1]);
        startGameButton.setLocation( width/2-buttonSize[0]/2 , startPositionY + spacingY);
        startGameButton.addActionListener(l -> gui.mainMenuButtonPressed(startGameButton.getText()) );
        add(startGameButton);

        JButton logInButton = new JButton("ACCOUNT");
        logInButton.setEnabled(true);
        logInButton.setBackground(Color.DARK_GRAY);
        logInButton.setForeground(Color.white);
        logInButton.setFont(buttonFont);
        logInButton.setSize(buttonSize[0], buttonSize[1]);
        logInButton.setLocation( width/2-buttonSize[0]/2 , startPositionY + spacingY*2 );
        logInButton.addActionListener(l -> gui.mainMenuButtonPressed(logInButton.getText()) );
        add(logInButton);

        JButton statisticsButton = new JButton("STATISTICS");
        statisticsButton.setEnabled(true);
        statisticsButton.setBackground(Color.DARK_GRAY);
        statisticsButton.setForeground(Color.white);
        statisticsButton.setFont(buttonFont);
        statisticsButton.setSize(buttonSize[0], buttonSize[1]);
        statisticsButton.setLocation( width/2-buttonSize[0]/2 , startPositionY  + spacingY*3 );
        statisticsButton.addActionListener(l -> gui.mainMenuButtonPressed(statisticsButton.getText()) );
        add(statisticsButton);
    }
}
