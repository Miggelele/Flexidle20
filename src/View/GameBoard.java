package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;

public class GameBoard extends JPanel {

    private Keyboard keyboard;
//    private JFormattedTextField[][] letterBoxes;
    private JTextField[][] letterBoxes;
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

        letterBoxes = new JTextField[chosenMaxGuesses][chosenWordlength];
        setup();
    }

    private void setup() {
        int labelSpacingY = 60;

        JLabel title = new JLabel("GameBoard");
        title.setLocation(10,10);
        title.setSize(200,50);
        title.setForeground(Color.white);
        add(title);

        JLabel chosenGuessesLabel = new JLabel("Maximum Guesses: " +  chosenMaxGuesses);
        chosenGuessesLabel.setLocation(10,labelSpacingY);
        chosenGuessesLabel.setSize(200,50);
        chosenGuessesLabel.setForeground(Color.white);
        add(chosenGuessesLabel);

        JLabel chosenWordLengthLabel = new JLabel("Wordlength: " +  chosenWordlength);
        chosenWordLengthLabel.setLocation(10,labelSpacingY*2);
        chosenWordLengthLabel.setSize(200,50);
        chosenWordLengthLabel.setForeground(Color.white);
        add(chosenWordLengthLabel);

        JLabel chosenLanguageLabel = new JLabel("Language: " +  chosenLanguage);
        chosenLanguageLabel.setLocation(10,labelSpacingY*3);
        chosenLanguageLabel.setSize(200,50);
        chosenLanguageLabel.setForeground(Color.white);
        add(chosenLanguageLabel);

        int startPositionX = 250;
        int startPositionY = 10;
        int spacingX = 50;
        int spacingY = 50;

        Font letterBoxFont = new Font("Dialog", Font.BOLD, 24);

        for (int i = 0; i < letterBoxes.length; i++) {
            for (int j = 0; j < letterBoxes[i].length; j++) {
                letterBoxes[i][j] = new JTextField();
                letterBoxes[i][j].setLocation( startPositionX+spacingX*j, startPositionY+spacingY*i);
                letterBoxes[i][j].setSize(40, 40);
                letterBoxes[i][j].setFont(letterBoxFont);
                if (i != 0) {
                    letterBoxes[i][j].setBackground( new Color(117, 117, 117, 255));
//                    letterBoxes[i][j].setEditable(false);
                    letterBoxes[i][j].setEnabled(false);
                }
                letterBoxes[i][j].addPropertyChangeListener(l -> disableLetterBox(l));
                add(letterBoxes[i][j]);
            }
        }
//        JTextField = new JTextField(0);
//        nameEntryField.setLocation(200,80);
//        nameEntryField.setSize(270,30);
//        leftPanel.add(nameEntryField);


    }

    protected JButton[][] getKeyBoardButtons() {
        return keyboard.getKeyBoardButtons();
    }

    private void disableLetterBox(PropertyChangeEvent e) {
        System.out.println("property change in letter box");
        if ( ( (JTextField) e.getSource()).getText().length() != 0 ) {
            ((JTextField) e.getSource()).setEnabled(false);
        }
    }
}
