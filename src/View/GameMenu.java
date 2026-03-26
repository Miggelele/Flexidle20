package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;

import static Controller.Controller.MAX_GUESSES_OPTIONS;
import static Controller.Controller.WORD_LENGTH_OPTIONS;

public class GameMenu extends JPanel {

    private GUI gui;
    private int width;
    private int height;

    public GameMenu(GUI gui, int width, int height) {
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
        JLabel title = new JLabel("GameMenu");
        title.setLocation(10,10);
        title.setSize(200,50);
        title.setForeground(Color.white);
        add(title);

        int[] buttonSize = {300,70};
        int startPositionY = 100;
        int spacingY = 90;

        Font buttonFont= new Font("Dialog", Font.BOLD, 24);

        JLabel guessLabel = new JLabel("Select maximum allowed guesses");
        guessLabel.setLocation(50,startPositionY);
        guessLabel.setSize(200,50);
        guessLabel.setForeground(Color.white);
        add(guessLabel);

        String[] maxGuessesOptions = new String[MAX_GUESSES_OPTIONS.length];
        for (int  i = 0; i < MAX_GUESSES_OPTIONS.length; i++) {
            maxGuessesOptions[i] = String.valueOf(Controller.MAX_GUESSES_OPTIONS[i]);
        }

        JComboBox maxGuesses = new JComboBox(maxGuessesOptions);
        maxGuesses.setLocation(width/2-buttonSize[0]/2 , startPositionY );
        maxGuesses.setSize(buttonSize[0], buttonSize[1]);
        maxGuesses.setMaximumRowCount(maxGuessesOptions.length);
        add(maxGuesses);


        JLabel wordlengthLabel = new JLabel("Select length of word");
        wordlengthLabel.setLocation(50, startPositionY+spacingY);
        wordlengthLabel.setSize(200,50);
        wordlengthLabel.setForeground(Color.white);
        add(wordlengthLabel);

        String[] wordLengthOptions = new String[WORD_LENGTH_OPTIONS.length];
        for (int  i = 0; i < WORD_LENGTH_OPTIONS.length; i++) {
            wordLengthOptions[i] = String.valueOf(Controller.WORD_LENGTH_OPTIONS[i]);
        }

        JComboBox wordLength = new JComboBox(wordLengthOptions);
        wordLength.setLocation(width/2-buttonSize[0]/2 , startPositionY + spacingY );
        wordLength.setSize(buttonSize[0], buttonSize[1]);
        wordLength.setMaximumRowCount(wordLengthOptions.length);
        add(wordLength);

        JLabel languageLabel = new JLabel("Select language of word");
        languageLabel.setLocation(50, startPositionY+spacingY*2);
        languageLabel.setSize(200,50);
        languageLabel.setForeground(Color.white);
        add(languageLabel);

        JComboBox language = new JComboBox(Controller.LANGUAGE_OPTIONS);
        language.setLocation(width/2-buttonSize[0]/2, startPositionY + spacingY*2 );
        language.setSize(buttonSize[0], buttonSize[1]);
        language.setMaximumRowCount(Controller.LANGUAGE_OPTIONS.length);
        add(language);

        JButton randomizeButton = new JButton("RANDOMIZE!");
        randomizeButton.setEnabled(true);
        randomizeButton.setBackground(Color.DARK_GRAY);
        randomizeButton.setForeground(Color.RED);
        randomizeButton.setFont(buttonFont);
        randomizeButton.setSize(buttonSize[0], buttonSize[1]);
        randomizeButton.setLocation(width/2-buttonSize[0]/2 , startPositionY+spacingY*3);
        randomizeButton.addActionListener(l -> gui.gameMenuButtonPressed(randomizeButton.getText()) );
        add(randomizeButton);

        JButton startGameButton = new JButton("START GAME");
        startGameButton.setEnabled(true);
        startGameButton.setBackground(Color.DARK_GRAY);
        startGameButton.setForeground(Color.white);
        startGameButton.setFont(buttonFont);
        startGameButton.setSize(buttonSize[0], buttonSize[1]);
        startGameButton.setLocation(width/2-buttonSize[0]/2 , startPositionY+spacingY*4);
        startGameButton.addActionListener(l -> gui.gameMenuButtonPressed(startGameButton.getText()) );
        add(startGameButton);


        JButton backToMainMenuButton = new JButton("BACK TO MAIN MENU");
        backToMainMenuButton.setEnabled(true);
        backToMainMenuButton.setBackground(Color.DARK_GRAY);
        backToMainMenuButton.setForeground(Color.white);
        backToMainMenuButton.setFont(buttonFont);
        backToMainMenuButton.setSize(buttonSize[0], buttonSize[1]);
        backToMainMenuButton.setLocation( width/2-buttonSize[0]/2 , startPositionY +  spacingY*5);
        backToMainMenuButton.addActionListener(l -> gui.gameMenuButtonPressed(backToMainMenuButton.getText()) );
        add(backToMainMenuButton);

    }
}
