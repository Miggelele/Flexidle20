package View;

import javax.swing.*;
import java.awt.*;

public class Keyboard extends JPanel {

    private GUI gui;
    private int width;
    private int height;
    private String chosenLanguage;
    JButton[][] keyboardButtons;

    public Keyboard(GUI gui, int width, int height, String chosenLanguage) {
        super(null);
        this.gui = gui;
        this.width = width;
        this.height = height/2;
        this.chosenLanguage = chosenLanguage;
        this.setSize(this.width, this.height);
        this.setLocation(0, this.height);
        this.setBackground(Color.black);
        this.setVisible(true);
        setup();
    }

    private void setup() {
        JLabel title = new JLabel("Keyboard");
        title.setLocation(10,10);
        title.setSize(200,50);
        title.setForeground(Color.white);
        add(title);

        int[] buttonSize = {300,60};
        int startPositionY = height - 110;
//        int spacingY = 150;

        Font buttonFont= new Font("Dialog", Font.BOLD, 24);

        JButton backToGameMenuButton = new JButton("BACK TO GAME MENU");
        backToGameMenuButton.setEnabled(true);
        backToGameMenuButton.setVisible(true);
        backToGameMenuButton.setBackground(Color.DARK_GRAY);
        backToGameMenuButton.setForeground(Color.white);
        backToGameMenuButton.setFont(buttonFont);
        backToGameMenuButton.setSize(buttonSize[0], buttonSize[1]);
        backToGameMenuButton.setLocation( width/3-buttonSize[0]/2-50 , startPositionY );
        backToGameMenuButton.addActionListener(l -> gui.gameBoardButtonPressed(backToGameMenuButton.getText()) );
        add(backToGameMenuButton);

        JButton backToMainMenuButton = new JButton("BACK TO MAIN MENU");
        backToMainMenuButton.setEnabled(true);
        backToMainMenuButton.setVisible(true);
        backToMainMenuButton.setBackground(Color.DARK_GRAY);
        backToMainMenuButton.setForeground(Color.white);
        backToMainMenuButton.setFont(buttonFont);
        backToMainMenuButton.setSize(buttonSize[0], buttonSize[1]);
        backToMainMenuButton.setLocation( width*2/3-buttonSize[0]/2+50 , startPositionY );
        backToMainMenuButton.addActionListener(l -> gui.gameBoardButtonPressed(backToMainMenuButton.getText()) );
        add(backToMainMenuButton);

        JButton backspaceButton = new JButton("<- BACKSPACE");
        backspaceButton.setEnabled(true);
        backspaceButton.setVisible(true);
        backspaceButton.setBackground(Color.DARK_GRAY);
        backspaceButton.setForeground(Color.white);
//        backspaceButton.setFont(buttonFont);
        backspaceButton.setSize(140, 55);
        backspaceButton.setLocation( width-180 , 50 );
        backspaceButton.addActionListener(l -> gui.gameBoardButtonPressed(backspaceButton.getText()) );
        add(backspaceButton);

        JButton enterGuessButton = new JButton("ENTER GUESS");
        enterGuessButton.setEnabled(true);
        enterGuessButton.setVisible(true);
        enterGuessButton.setBackground(Color.DARK_GRAY);
        enterGuessButton.setForeground(Color.white);
//        enterGuessButton.setFont(buttonFont);
        enterGuessButton.setSize(140, 55);
        enterGuessButton.setLocation( width-180 , 50+117 );
        enterGuessButton.addActionListener(l -> gui.gameBoardButtonPressed(enterGuessButton.getText()) );
        add(enterGuessButton);


//        switch(chosenLanguage) {
//            case "SWEDISH":
//
//                break;
//            case "ENGLISH":
//
//                break;
//            case "GERMAN":
//
//                break;
//        }

        int keyboardStartPositionX = 50;
        int keyboardStartPositionY = 50;
        int keyboardSpacingX = 60;
        int keyboardSpacingY = 60;

        Font keyboardFont= new Font("Dialog", Font.BOLD, 24);

        String[][] allSwedishLetters = { {"Q","W","E","R","T","Y","U","I","O","P","Å"},{"A","S","D","F","G","H","J","K","L","Ö","Ä"},{"Z","X","C","V","B","N","M"} };
        keyboardButtons = new JButton [allSwedishLetters.length][];
        for (int i = 0; i < allSwedishLetters.length; i++) {
            keyboardButtons[i] = new JButton[allSwedishLetters[i].length];
        }
        Color keyboardButtonsColor = new Color(117, 117, 117, 255);

        for (int i = 0; i < allSwedishLetters.length; i++) {
            for (int j = 0; j < allSwedishLetters[i].length; j++) {
                keyboardButtons[i][j] = new JButton(allSwedishLetters[i][j]);
                keyboardButtons[i][j].setBackground(keyboardButtonsColor);
                keyboardButtons[i][j].setForeground(Color.white);
                keyboardButtons[i][j].setSize(57, 57);
                keyboardButtons[i][j].setFont(keyboardFont);
                keyboardButtons[i][j].setLocation(keyboardStartPositionX + keyboardSpacingX*j, keyboardStartPositionY + keyboardSpacingY*i);
                String buttonText = keyboardButtons[i][j].getText();
                keyboardButtons[i][j].addActionListener(l -> gui.gameBoardButtonPressed(buttonText));
                add(keyboardButtons[i][j]);
            }
        }

    }

    protected JButton[][] getKeyBoardButtons() {
        return keyboardButtons;
    }

}
