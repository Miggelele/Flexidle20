package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;

public class GameBoard extends JPanel {


    /**
     * The letterBoxes are implemented as JButtons, because that was the most aesthetical option that was functional.
     * These JButtons are not used as buttons, but only used to display text on them (they show which letters have
     * been chosen).
     */
    private JButton[][] letterBoxes;
    private Keyboard keyboard;
    private int chosenMaxGuesses;
    private int chosenWordlength;
    private String chosenLanguage;
    private GUI gui;
    private int width;
    private int height;


    public GameBoard(GUI gui, int width, int height, int chosenMaxGuesses, int chosenWordlength, String chosenLanguage) {
        super(null);
        this.gui = gui;
        this.width = width;
        this.height = height;
        this.chosenMaxGuesses = chosenMaxGuesses;
        this.chosenWordlength = chosenWordlength;
        this.chosenLanguage = chosenLanguage;
        this.setSize(width, height);
        this.setBackground(Color.black);
        this.setVisible(true);
        this.keyboard = new Keyboard(gui, width, height, chosenLanguage);
        add(keyboard);

        letterBoxes = new JButton[chosenMaxGuesses][chosenWordlength];
        setup();
    }

    private void setup() {
        int labelSpacingY = 60;

        JLabel title = new JLabel("GameBoard");
        title.setLocation(10,10);
        title.setSize(200,50);
        title.setForeground(Color.white);
        add(title);

        Font labelFont = new Font("Dialog", Font.BOLD, 14);

        JLabel chosenGuessesLabel = new JLabel("Maximum Guesses: " +  chosenMaxGuesses);
        chosenGuessesLabel.setLocation(10,labelSpacingY);
        chosenGuessesLabel.setSize(200,50);
        chosenGuessesLabel.setFont(labelFont);
        chosenGuessesLabel.setForeground(Color.white);
        add(chosenGuessesLabel);

        JLabel chosenWordLengthLabel = new JLabel("Wordlength: " +  chosenWordlength);
        chosenWordLengthLabel.setLocation(10,labelSpacingY*2);
        chosenWordLengthLabel.setSize(200,50);
        chosenWordLengthLabel.setFont(labelFont);
        chosenWordLengthLabel.setForeground(Color.white);
        add(chosenWordLengthLabel);

        JLabel chosenLanguageLabel = new JLabel("Language: " +  chosenLanguage);
        chosenLanguageLabel.setLocation(10,labelSpacingY*3);
        chosenLanguageLabel.setSize(200,50);
        chosenLanguageLabel.setFont(labelFont);
        chosenLanguageLabel.setForeground(Color.white);
        add(chosenLanguageLabel);

        JLabel correctWordLabel = new JLabel("Correct word (debug): " +  gui.getCorrectWord());
        correctWordLabel.setLocation(10,labelSpacingY*4);
        correctWordLabel.setSize(200,50);
        correctWordLabel.setForeground(Color.white);
        add(correctWordLabel);

        int startPositionX = 250;
        int startPositionY = 10;
        int spacingX = 58;
        int spacingY = 53;

        Font letterBoxFont = new Font("Dialog", Font.BOLD, 22);

        for (int i = 0; i < letterBoxes.length; i++) {
            for (int j = 0; j < letterBoxes[i].length; j++) {
                letterBoxes[i][j] = new JButton();
                letterBoxes[i][j].setLocation( startPositionX+spacingX*j, startPositionY+spacingY*i);
                letterBoxes[i][j].setSize(53, 45);
                letterBoxes[i][j].setFont(letterBoxFont);
                letterBoxes[i][j].setBorder(BorderFactory.createLineBorder(Color.lightGray));
                letterBoxes[i][j].setBackground(Color.black);
                letterBoxes[i][j].setForeground( Color.white);
                add(letterBoxes[i][j]);
            }
        }

    }

    protected JButton[][] getKeyBoardButtons() {
        return keyboard.getKeyBoardButtons();
    }

    public JButton[] getLetterBoxesRow(int i) {
        return letterBoxes[i];
    }
}
