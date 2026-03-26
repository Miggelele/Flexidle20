package View;

import javax.swing.*;
import java.awt.*;

public class Statistics extends JPanel {

    private GUI gui;
    private int width;
    private int height;

    public Statistics(GUI gui, int width, int height) {
        super(null);
        this.gui = gui;
        this.width = width;
        this.height = height;
        this.setSize(width, height);
        this.setBackground(Color.black);
        this.setVisible(true);
        setup();
    }

    private void setup() {
        JLabel title = new JLabel("Statistics");
        title.setLocation(10,10);
        title.setSize(200,50);
        title.setForeground(Color.white);
        add(title);


        int[] buttonSize = {400,100};
        int startPositionY = height-150;
//        int spacingY = 150;

        Font buttonFont= new Font("Dialog", Font.BOLD, 24);

        JButton backToMainMenuButton = new JButton("BACK TO MAIN MENU");
        backToMainMenuButton.setEnabled(true);
        backToMainMenuButton.setBackground(Color.DARK_GRAY);
        backToMainMenuButton.setForeground(Color.white);
        backToMainMenuButton.setFont(buttonFont);
        backToMainMenuButton.setSize(buttonSize[0], buttonSize[1]);
        backToMainMenuButton.setLocation( width/2-buttonSize[0]/2 , startPositionY);
        backToMainMenuButton.addActionListener(l -> gui.statisticsButtonPressed(backToMainMenuButton.getText()) );
        add(backToMainMenuButton);

    }
}
