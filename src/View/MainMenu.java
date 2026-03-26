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

//        infoPanel = new InfoPanel(width-(height*7/8)-100 , height*7/8, mainFrame);
//        add(infoPanel);
//
//        gameBoardPanel = new GameBoardPanel(height*7/8 , height*7/8, mainFrame);
//        add(gameBoardPanel);
//
//        bottomPanel = new BottomPanel(width, height/8, mainFrame);
//        add(bottomPanel);
//
//        mainMenuPanel = new MainMenuPanel(width, height, mainFrame);
//        add(mainMenuPanel);
    }

    private void setup() {
        JLabel title = new JLabel("MainMenu");
        title.setLocation(10,10);
        title.setSize(200,50);
        title.setForeground(Color.white);
        add(title);

        /*
        turnMarkerPlayerTwo = new JLabel("->");
        turnMarkerPlayerTwo.setLocation(5,200);
        turnMarkerPlayerTwo.setSize(40,50);
        turnMarkerPlayerTwo.setFont(markerFont);
        turnMarkerPlayerTwo.setForeground(Color.blue);
        add(turnMarkerPlayerTwo);
         */
        int[] buttonSize = {400,100};
        int startPositionY = 100;
        int spacingY = 150;

        Font buttonFont= new Font("Dialog", Font.BOLD, 24);

        JButton startGameButton = new JButton("PLAY");
        startGameButton.setEnabled(true);
        startGameButton.setBackground(Color.DARK_GRAY);
        startGameButton.setForeground(Color.white);
        startGameButton.setFont(buttonFont);
        startGameButton.setSize(buttonSize[0], buttonSize[1]);
        startGameButton.setLocation( width/2-buttonSize[0]/2 , startPositionY);
        startGameButton.addActionListener(l -> gui.mainMenuButtonPressed(startGameButton.getText()) );
        add(startGameButton);

        JButton logInButton = new JButton("LOG IN");
        logInButton.setEnabled(true);
        logInButton.setBackground(Color.DARK_GRAY);
        logInButton.setForeground(Color.white);
        logInButton.setFont(buttonFont);
        logInButton.setSize(buttonSize[0], buttonSize[1]);
        logInButton.setLocation( width/2-buttonSize[0]/2 , startPositionY + spacingY );
        logInButton.addActionListener(l -> gui.mainMenuButtonPressed(logInButton.getText()) );
        add(logInButton);

        JButton statisticsButton = new JButton("STATISTICS");
        statisticsButton.setEnabled(true);
        statisticsButton.setBackground(Color.DARK_GRAY);
        statisticsButton.setForeground(Color.white);
        statisticsButton.setFont(buttonFont);
        statisticsButton.setSize(buttonSize[0], buttonSize[1]);
        statisticsButton.setLocation( width/2-buttonSize[0]/2 , startPositionY  + spacingY*2 );
        statisticsButton.addActionListener(l -> gui.mainMenuButtonPressed(statisticsButton.getText()) );
        add(statisticsButton);

        /*
        reglerButton = new JButton("REGLER");
        reglerButton.setEnabled(true);
        reglerButton.setSize(buttonSize[0], buttonSize[1]);
        reglerButton.setLocation((width/(numberOfButtons+1))-buttonSize[0]/2, height / 2 - buttonSize[1]/2);
        reglerButton.addActionListener(l -> mainFrame.buttonPressed(BottomPanelButtonType.Regler));
        reglerButton.setFont(buttonFont);
        this.add(reglerButton);
         */
    }
}
