package View;

import Controller.Controller;
import Model.SecurityQuestion;

import javax.swing.*;
import java.awt.*;

/**
 * This class contains and modifies the JPanels that makes up the different menus inside the game. This class handles
 * the communication between the controller and the view package.
 *
 * @author Frida Sjögren
 */
public class GUI extends JFrame {

    private Controller controller;
    private GameBoard gameBoard;
    private GameMenu gameMenu;
    private Statistics statistics;
    private MainMenu mainMenu;
    private Account account;
    private int width;
    private int height;

    //jag kan motivera att dessa ska vara public och static eftersom dem används överallt (och det bara är färger) men idk vad ni känner - Elin
    private Color wordleGreen  = new Color(37, 175, 92, 255);
    private  Color wordleYellow = new Color(255, 194, 23, 255);
    private Color wordleGray   = new Color(43, 33, 6, 255);


    /**
     * This constructor is used to start the GUI. It is used once by the controller when the application initiates.
     *
     * @param controller    a reference to the controller, which is used for communication to the controller package.
     * @param width         an int, the width of the application window.
     * @param height        an int, the height of the application window.
     *
     * @author Frida Sjögren
     */
    public GUI(Controller controller, int width, int height) {
        super("FLEXIDLE");

        //Finds the location that centers the frame in the screen.
        int locationWidth =  (int) (( Toolkit.getDefaultToolkit().getScreenSize().getWidth()  - width ) / 2);
        int locationHeight = (int) (( Toolkit.getDefaultToolkit().getScreenSize().getHeight() - height ) / 2 - 20);
        this.setLocation(locationWidth, locationHeight);

        this.controller = controller;
        this.width = width;
        this.height = height;
        this.setSize(width, height);
        this.mainMenu = new MainMenu(this, width, height);
        this.setContentPane(mainMenu);
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Called when a button in the MainMenu panel is pressed. ("PLAY", "ACCOUNT", "STATISTICS")
     *
     * @param buttonName,   a String, which is the text on the button
     *
     * @author Frida Sjögren
     */
    protected void mainMenuButtonPressed(String buttonName) {
        controller.mainMenuButtonPressed(buttonName);
    }

    /**
     * Called when a button in the Statistics panel is pressed. ("BACK TO MAIN MENU")
     *
     * @param buttonName,   a String, which is the text on the button
     *
     * @author Frida Sjögren
     */
    protected void statisticsButtonPressed(String buttonName) {
        controller.statisticsButtonPressed(buttonName);
    }

    /**
     * Called when a button in the GameMenu panel is pressed. ("PLAY RANDOM GAME", "START GAME", "BACK TO MAIN MENU")
     *
     * @param buttonName,   a String, which is the text on the button
     *
     * @author Frida Sjögren
     */
    protected void gameMenuButtonPressed(String buttonName) {
        controller.gameMenuButtonPressed(buttonName);
    }

    /**
     * Called when a button in the GameBoard panel (or from its Keyboard panel!) is pressed.
     * (buttons include all the keyboard letters, ENTER, BACKSPACE, main menu-button and game menu-button)
     *
     * @param buttonName,   a String, which is the text on the button
     *
     * @author Frida Sjögren
     */
    protected void gameBoardButtonPressed(String buttonName) {
        controller.gameBoardButtonPressed(buttonName);
    }

    protected void createAccountButtonPressed(String buttonName)  {
        controller.createAccountButtonPressed(buttonName);
    }

    protected void logInButtonPressed(String buttonName) {
        controller.logInButtonPressed(buttonName);
    }

    protected boolean isLoggedIn() {
        return controller.IsUserLoggedIn();
    }

    /**
     * Used when the user makes choices that updates which menu is showing. The menus are different JPanels, and the
     * application switches which one is shown in the JFrame using the panel name parameter.
     *
     * @param panelName     a String, which is the name of the panel that should be shown.
     *
     * @author Frida Sjögren
     */
    public void setPanel(String panelName) {
        switch (panelName) {
            case "GameBoard":
                this.gameBoard = new GameBoard(this, width, height, controller.getChosenMaxGuesses(), controller.getChosenWordLength(), controller.getChosenLanguage());
                setContentPane(this.gameBoard);
                break;
            case "GameMenu":
                this.gameMenu = new GameMenu(this, width, height);
                setContentPane(gameMenu);
                break;
            case "Statistics":
                this.statistics =  new Statistics(this, width, height);
                setContentPane(statistics);
                break;
            case "MainMenu":
                this.mainMenu = new MainMenu(this, width, height);
                setContentPane(mainMenu);
                break;
            case "Account":
                this.account = new Account(this, width, height);
                setContentPane(account);
                break;
            default:
                setContentPane(mainMenu);
                System.out.println("Invalid panel name, back to main menu you go.");
        }
    }

    /**
     * Changes the color of a button in the keyboard in the GameBoard panel. Used after a guess has been made to
     * indicate to the user which letters are correct and in the right position (GREEN), or correct but in the wrong
     * position (YELLOW), or completely wrong (GRAY). The function goes through all the buttons, finds one with
     * text matching the parameter, and then updates the color.
     *
     * @param keyboardButton    a String, that is the letter on the button that should change color.
     * @param color             a String, that is the new color of the button.
     *
     * @author Frida Sjögren
     * @author Elin Piho
     */
    public void changeKeyBoardButtonColor(String keyboardButton, String color) {
        JButton[][] keyboardButtons = gameBoard.getKeyBoardButtons();
        for (int  i = 0; i < keyboardButtons.length; i++) {
            for (int j = 0; j < keyboardButtons[i].length; j++) {
                if (keyboardButtons[i][j].getText().equals(keyboardButton)) {
                    if (color.equals("GREEN")) {
                        keyboardButtons[i][j].setBackground(wordleGreen);
                    }
                    else if(color.equals("YELLOW")) {
                        if (!keyboardButtons[i][j].getBackground().equals(wordleGreen)) {
                            keyboardButtons[i][j].setBackground(wordleYellow);
                        }
//                        keyboardButtons[i][j].setBackground(new Color(255, 237, 67, 255) );
                    }
                    else if(color.equals("GRAY")) {
                        if (!keyboardButtons[i][j].getBackground().equals(wordleGreen) &&
                                !keyboardButtons[i][j].getBackground().equals(wordleYellow)) {
                            keyboardButtons[i][j].setBackground(wordleGray);
                        }
                    }
                    return;
                }

            }
        }
    }

    /**
     * This function returns the letters of the current guess, which is the text from a row of the letterBoxes in the
     * GameBoard panel. If a letterBox is empty, an empty String (not null) is returned for that element.
     *
     * @param rowOfCurrentGuess     an int, which is the row where the current guess is (it's the same as the number
     *                              of made guesses).
     *
     * @return                      a String[], which is the letters of the current guess.
     *
     * @author Frida Sjögren
     */
    public String[] getCurrentGuess(int rowOfCurrentGuess) {
        JButton[] letterBoxRow = gameBoard.getLetterBoxesRow(rowOfCurrentGuess);;
        String[] currentGuess = new String[letterBoxRow.length];

        for (int i = 0; i < letterBoxRow.length; i++) {
            currentGuess[i] = letterBoxRow[i].getText();
        }

        return currentGuess;
    }

    /**
     * This functions sets the text in one of the letterBoxes in the GameBoard panel. It is used after the user presses
     * a keyboard button to select a letter for their guess.
     *
     * @param row       an int, which is the row where the current guess is (it's the same as the number of made
     *                  guesses).
     * @param column    an int, which is the index of where in the word the new letter should be added.
     * @param letter    a String, the letter that should be shown, same as the keyboard button that was pressed.
     *
     * @author Frida Sjögren
     */
    public void updateLetterBox(int row, int column, String letter) {
        gameBoard.getLetterBoxesRow(row)[column].setText(letter);
    }

    /**
     * This functions checks how many letters the current guess has (how many of the letterBoxes in a row have
     * a letter in them).
     *
     * @param row       an int, which is the row where the current guess is (it's the same as the number
     *                  of made guesses).
     *
     * @return          an int, which is the number of letters in the current guess.
     *
     * @author Frida Sjögren
     */
    public int checkNumberOfFilledLetterBoxes(int row) {
        JButton[] letterBoxRow = gameBoard.getLetterBoxesRow(row);
        int numberOfFilledLetterBoxes = 0;
        for (int i = 0; i < letterBoxRow.length; i++) {
            if ( !(letterBoxRow[i].getText().isBlank()) ) {
                numberOfFilledLetterBoxes++;
            }
        }

        return numberOfFilledLetterBoxes;
    }

    /**
     *  Changes the colors of a row of letterBoxes in the GameBoard panel. Used after a guess has been made to indicate
     *  to the user which letters of the last guess are correct and in the right position (GREEN), or correct but in
     *  the wrong position (YELLOW), or completely wrong (GRAY).
     *
     * @param row       an int, the coordinate for the row where the letterBox colors should be updated after a guess
     *                  has been checked.
     * @param colors    a String[], which is the new colors for the letterBoxes in the given row.
     *
     * @author Frida Sjögren
     */
    public void updateLetterBoxColors(int row, String[] colors) {
        JButton[] letterBoxes = gameBoard.getLetterBoxesRow(row);

        for (int i = 0; i < letterBoxes.length; i++) {
            if (colors[i].equals("GREEN")) {
                letterBoxes[i].setBackground(wordleGreen);
            } else if (colors[i].equals("YELLOW")) {
                letterBoxes[i].setBackground(wordleYellow);
            } else if (colors[i].equals("GRAY")) {
                letterBoxes[i].setBackground(wordleGray);
            }
        }
    }



    /**
     * Used when the BACKSPACE button is pressed to clear the text in a letterBox in the GameBoard panel (delete a
     * letter from the current guess).
     *
     * @param row       an int, the row coordinate of which letterBox should be cleared.
     * @param column    an int, the column coordinate of which letterBox should be cleared.
     *
     * @author Frida Sjögren
     */
    public void clearLetterBox(int row, int column) {
        gameBoard.getLetterBoxesRow(row)[column].setText("");
    }


    /**
     * Used after the game is over to tell the user to go start a new game using the menu buttons.
     *
     * @param message       a String, the message to the user.
     *
     * @author Frida Sjögren
     */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public String getChosenLanguage() {
        return gameMenu.getChosenLanguage();
    }

    public int getChosenMaxGuesses() {
        return gameMenu.getChosenMaxGuesses();
    }

    public int getChosenWordLength() {
        return gameMenu.getChosenWordLength();
    }

    protected String getCorrectWord() {
        return controller.getWordToGuess();
    }

    public String getUsername() {
        return account.getUsername();
    }

    public String getPassword() {
        return account.getPassword();
    }

    public SecurityQuestion getSelectedQuestion() {
        return account.getSelectedQuestion();
    }

    public String getAnswer() {
        return account.getAnswer();
    }
}
