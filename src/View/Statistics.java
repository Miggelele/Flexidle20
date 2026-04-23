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

        Font labelFont = new Font("Courier New", Font.BOLD, 14);

        int buttonY = 650;

        int startY = 50;
        int rowHeight = 25;
        int col1 = 50;
        int col2 = 180;
        int col3 = 320;
        int col4 = 460;
        int col5 = 620;
        int col6 = 760;
        int colW = 130;

        JLabel dateHeader = new JLabel("DATE");
        dateHeader.setLocation(col1, startY);
        dateHeader.setSize(colW, rowHeight);
        dateHeader.setFont(labelFont);
        dateHeader.setForeground(Color.white);
        add(dateHeader);

        JLabel languageHeader = new JLabel("LANGUAGE");
        languageHeader.setLocation(col2, startY);
        languageHeader.setSize(colW, rowHeight);
        languageHeader.setFont(labelFont);
        languageHeader.setForeground(Color.white);
        add(languageHeader);

        JLabel wordLengthHeader = new JLabel("WORD LENGTH");
        wordLengthHeader.setLocation(col3, startY);
        wordLengthHeader.setSize(colW, rowHeight);
        wordLengthHeader.setFont(labelFont);
        wordLengthHeader.setForeground(Color.white);
        add(wordLengthHeader);

        JLabel guessesHeader = new JLabel("GUESSES");
        guessesHeader.setLocation(col4, startY);
        guessesHeader.setSize(colW, rowHeight);
        guessesHeader.setFont(labelFont);
        guessesHeader.setForeground(Color.white);
        add(guessesHeader);

        JLabel guessedInHeader = new JLabel("GUESSED IN");
        guessedInHeader.setLocation(col5, startY);
        guessedInHeader.setSize(180, rowHeight);
        guessedInHeader.setFont(labelFont);
        guessedInHeader.setForeground(Color.white);
        add(guessedInHeader);

        JLabel wordHeader = new JLabel("WORD");
        wordHeader.setLocation(col6, startY);
        wordHeader.setSize(180, rowHeight);
        wordHeader.setFont(labelFont);
        wordHeader.setForeground(Color.white);
        add(wordHeader);

        String[][] statistics = {
                {"0315-26:", "English", "6", "7", "FAILED", "STUPID"},
                {"0319-16:", "English", "5", "4", "2", "RETRO"},
                {"0320-26:", "English", "5", "6", "5", "ZELDA"},
                {"0413-26:", "Swedish", "6", "4", "FAILED", "SOMMAR"},
                {"0414-26", "Swedish", "4", "3", "1", "HUND"},
                {"0418-26:", "German",  "5", "5", "5", "HANDY"},
                {"0420-26:", "English", "6", "3", "2", "STINKY"},
                {"0420-26:", "German", "4", "7", "FAILED", "KEKS", },
                {"0421-26:", "Swedish", "5", "5", "3", "VAMPYR"}
        };

        for (int i = 0; i < statistics.length; i++) {
            int y = startY + rowHeight + (i * rowHeight);

            JLabel date = new JLabel(statistics[i][0]);
            date.setLocation(col1, y);
            date.setSize(colW, rowHeight);
            date.setFont(labelFont);
            date.setForeground(Color.white);
            add(date);

            JLabel language = new JLabel(statistics[i][1]);
            language.setLocation(col2, y);
            language.setSize(colW, rowHeight);
            language.setFont(labelFont);
            language.setForeground(Color.white);
            add(language);

            JLabel wordLength = new JLabel(statistics[i][2]);
            wordLength.setLocation(col3, y);
            wordLength.setSize(colW, rowHeight);
            wordLength.setFont(labelFont);
            wordLength.setForeground(Color.white);
            add(wordLength);

            JLabel guesses = new JLabel(statistics[i][3]);
            guesses.setLocation(col4, y);
            guesses.setSize(colW, rowHeight);
            guesses.setFont(labelFont);
            guesses.setForeground(Color.white);
            add(guesses);

            JLabel guessedIn = new JLabel(statistics[i][4]);
            guessedIn.setLocation(col5, y);
            guessedIn.setSize(colW, rowHeight);
            guessedIn.setFont(labelFont);
            guessedIn.setForeground(Color.white);
            add(guessedIn);

            JLabel guessedWord = new JLabel(statistics[i][5]);
            guessedWord.setLocation(col6, y);
            guessedWord.setSize(colW, rowHeight);
            guessedWord.setFont(labelFont);
            guessedWord.setForeground(Color.white);
            add(guessedWord);
        }

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
