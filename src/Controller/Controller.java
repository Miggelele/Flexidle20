package Controller;

import Model.Database;
import Model.GameLogic;
import Model.User;
import View.GUI;
import Model.SecurityQuestion;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * The Controller starts the GUI and handles communication with the view package. It should also handle the logic
 * of when to change the GUI in response to events in the game.
 *
 * @author Frida Sjögren
 * @author Isabell Persson
 */
public class Controller {
    private GUI gui;
    private GameLogic gameLogic;
    private Database db;

    public static final int[] MAX_GUESSES_OPTIONS = {5, 6, 7};
    public static final int[] WORD_LENGTH_OPTIONS = {4, 5, 6};
    public static final String[] LANGUAGE_OPTIONS = {"SWEDISH", "ENGLISH", "GERMAN"};

    private String wordToGuess;
    private int numberOfGuessesMade;
    private int chosenMaxGuesses;
    private int chosenWordLength;
    private String chosenLanguage;
    private int lettersTyped;

    private User currentUser;

    /**
     * In this constructor the GUI is started by initiating a JFrame object (the GUI class).
     *
     * @author Frida Sjögren
     */
    public Controller() {
        gui = new GUI(this, 900, 750);
        gameLogic = new GameLogic();
        db = new Database();

        setupDatabaseConnection();
    }

    /**
     * Loads data from the config file and sets up the correct information for a connection to the database
     *
     * @author Mikael Szalai
     */
    private void setupDatabaseConnection() {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("config.properties"));
        } catch (FileNotFoundException e) {
            System.out.println("config.properties file not found");
            System.out.println("Failed to run program");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = props.getProperty("db.url");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");

        db.setUrl(url);
        db.setUsername(username);
        db.setPassword(password);
    }

    /**
     * Called when a button in the MainMenu panel is pressed. ("PLAY", "ACCOUNT", "STATISTICS")
     *
     * @param buttonName, a String, which is the text on the button
     * @author Frida Sjögren
     */
    public void mainMenuButtonPressed(String buttonName) {
        switch (buttonName) {
            case "PLAY":
                System.out.println("Pressed PLAY in MainMenu.");
                gui.setPanel("GameMenu");
                break;

            case "ACCOUNT":
                System.out.println("Pressed ACCOUNT in MainMenu.");
                gui.setPanel("Account");
                //ToDo implementera log in
                break;

            case "STATISTICS":
                System.out.println("Pressed STATISTICS in MainMenu.");
                gui.setPanel("Statistics");
                break;

            default:
                System.out.println("Invalid mainMenu button name.");

        }
    }

    /**
     * Called when a button in the Statistics panel is pressed. ("BACK TO MAIN MENU")
     *
     * @param buttonName, a String, which is the text on the button
     * @author Frida Sjögren
     */
    public void statisticsButtonPressed(String buttonName) {
        switch (buttonName) {
            case "BACK TO MAIN MENU":
                System.out.println("Pressed BACK TO MAIN MENU in Statistics ");
                gui.setPanel("MainMenu");
                break;
            default:
                System.out.println("Invalid statistics button name.");
        }
    }

    /**
     * Called when a button in the Account panel is pressed. ("BACK TO MAIN MENU")
     *
     * @param buttonName, a String, which is the text on the button
     * @author Frida Sjögren
     */
    public void accountButtonPressed(String buttonName) {
        switch (buttonName) {
            case "BACK TO MAIN MENU":
                System.out.println("Pressed BACK TO MAIN MENU in Account ");
                gui.setPanel("MainMenu");
                break;
            default:
                System.out.println("Invalid account button name.");
        }
    }

    /**
     * Called when a button in the GameMenu panel is pressed. ("PLAY RANDOM GAME", "START GAME", "BACK TO MAIN MENU")
     *
     * @param buttonName, a String, which is the text on the button
     * @author Frida Sjögren
     */
    public void gameMenuButtonPressed(String buttonName) {
        switch (buttonName) {
            case "BACK TO MAIN MENU":
                System.out.println("Pressed BACK TO MAIN MENU in GameMenu");
                gui.setPanel("MainMenu");
                break;
            case "START GAME":
                System.out.println("Pressed START GAME in GameMenu.");
                chosenMaxGuesses = gui.getChosenMaxGuesses();   //hämtar värderna från drop down menyerna
                chosenWordLength = gui.getChosenWordLength();
                chosenLanguage = gui.getChosenLanguage();

                startNewGame(false);

                break;
            case "PLAY RANDOM GAME!":
                System.out.println("Pressed PLAY RANDOM GAME! in GameMenu.");

                startNewGame(true);

                break;

            default:
                System.out.println("Invalid gameMenu button name.");
        }
    }

    /**
     * Called when a button in the GameBoard panel (or from its Keyboard panel!) is pressed.
     * (buttons include all the keyboard letters, ENTER, BACKSPACE, main menu-button and game menu-button)
     * The buttons with the letters from the keyboard currently all go to the default case in the switch case and are
     * handled there.
     *
     * @param buttonName, a String, which is the text on the button
     * @author Frida Sjögren
     */
    public void gameBoardButtonPressed(String buttonName) {
        switch (buttonName) {
            case "BACK TO MAIN MENU":
                System.out.println("Pressed BACK TO MAIN MENU in GameBoard");
                gui.setPanel("MainMenu");
                break;
            case "BACK TO GAME MENU":
                System.out.println("Pressed BACK TO GAME MENU in GameBoard");
                gui.setPanel("GameMenu");
                break;
            case "<- BACKSPACE":
                System.out.println("Pressed BACKSPACE in GameBoard");
                if (isGuessRemaining()) {
                    lettersTyped = gui.checkNumberOfFilledLetterBoxes(numberOfGuessesMade);
                    if (lettersTyped > 0) {
                        gui.clearLetterBox(numberOfGuessesMade, lettersTyped - 1);
                    }
                }
                break;
            case "ENTER GUESS":
                System.out.println("Pressed ENTER GUESS in GameBoard");

                //kolla om alla letterboxes fyllda

                if (isGuessRemaining()) {
                    compareGuessToWord();
                }
                break;
            default:        //för alla bokstavsknappar i keyboard (i gameboard)
                System.out.println("pressed button " + buttonName + " in GameBoard");
                updateNextFreeLetterBox(buttonName);
        }
    }

    /**
     * Button pressed in gui when creating an account
     * Takes the input from the gui, and creates a new account
     *
     * @param buttonName a String that is the name of the button pressed
     * @author Elin Piho
     */
    public void createAccountButtonPressed(String buttonName) {
        switch (buttonName) {
            case "CREATE ACCOUNT":
                System.out.println("Pressed Create Account.");
                String username = gui.getUsername();
                String password = gui.getPassword();
                SecurityQuestion securityQuestion = gui.getSelectedQuestion();
                String securityAnswer = gui.getAnswer();

                currentUser = new User(username, password, securityQuestion, securityAnswer);
                break;
            default:
                System.out.println("Invalid create account button name.");
        }
    }

    /**
     * Button pressed in gui when logging in
     *
     * @param buttonName a String that is the name of the button pressed
     * @author Elin Piho
     */
    public void logInButtonPressed(String buttonName) {
        switch (buttonName) {
            case "LOG IN":
                System.out.println("Pressed LOG IN in Account");
                break;
            default:
                System.out.println("Invalid log in button name.");
        }
    }

    /**
     * Changes the color of a button in the keyboard in the GameBoard panel in the GUI. Used after a guess has been
     * made to indicate to the user which letters are correct and in the right position ("GREEN"), or correct but in
     * the wrong position ("YELLOW"), or completely wrong ("GRAY").
     *
     * @param keyboardButton a String, that is the letter on the button that should change color.
     * @param color          a String, that is the new color of the button.
     * @author Frida Sjögren
     */
    private void changeKeyBoardButtonColor(String keyboardButton, String color) {
        gui.changeKeyBoardButtonColor(keyboardButton, color);
    }

    /**
     * Used to start a new game when either "START NEW GAME" OR "PLAY RANDOM GAME!" was selected in the GameMenu in
     * the GUI. This function uses a boolean parameter to decide if the game starts with random settings or with the
     * settings selected in the GameMenu.
     * A word to guess is generated with the given (or randomized) settings, and then the game starts by showing the
     * GameBoard panel in the GUI.
     *
     * @param randomSettingsChosen a boolean, it represents if the new game should use random settings or not.
     * @author Frida Sjögren
     * @author Isabell Persson
     */
    private void startNewGame(boolean randomSettingsChosen) {

        if (randomSettingsChosen) {        //kommer från menyvalet "PLAY RANDOM GAME!"
            String[] newOptions = generateRandomOptions();
            chosenMaxGuesses = Integer.parseInt(newOptions[0]);
            chosenWordLength = Integer.parseInt(newOptions[1]);
            chosenLanguage = newOptions[2];
        }

        wordToGuess = generateNewWord(chosenWordLength, chosenLanguage);
        gameLogic.addWordToGuess(wordToGuess);
        gameReset();
        gui.setPanel("GameBoard");
    }

    public void gameReset() {
        gameLogic.setIsWinnerToFalse();
        numberOfGuessesMade = 0;
    }

    /**
     * Should generate a random word that fits the given parameters.
     *
     * @param wordLength an int, the length of the word to guess in the game.
     * @param language   a String, the language of the word to guess in the game.
     * @return a String, the word to guess in the game.
     * @author Frida Sjögren
     */
    private String generateNewWord(int wordLength, String language) {
        //ToDo should select a random word from our dictionaries
        //gameLogic.selectNewWord(wordLength, language); // NOT IMPLEMENTED

        if (language.equals("SWEDISH")) {
            if (wordLength == 4) {
                return "STOL";
            } else if (wordLength == 5) {
                return "KLUMP";
            } else if (wordLength == 6) {
                return "BOXARE";
            }
        } else if (language.equals("ENGLISH")) {
            if (wordLength == 4) {
                return "ROSE";
            } else if (wordLength == 5) {
                return "SILKY";
            } else if (wordLength == 6) {
                return "GOLDEN";
            }
        } else if (language.equals("GERMAN")) {
            if (wordLength == 4) {
                return "GRÜN";
            } else if (wordLength == 5) {
                return "HANDY";
            } else if (wordLength == 6) {
                return "STRAßE";
            }
        }
        return "ÄPPLE";
    }

    /**
     * Should generate randomly selected settings of maximum guesses, wordlength and language. A String[] is used so
     * that all the settings can be returned with one method (integer parsing will be needed for parts of the return).
     *
     * @return a String[] with the randomly selected settings of maximum guesses, wordlength and language.
     * @author Frida Sjögren
     */
    private String[] generateRandomOptions() {
        //ToDo should be selected at random from the hard-coded options
        return new String[]{"5", "6", "ENGLISH"};
    }

    /**
     * Called after the user has pressed "ENTER GUESS" in the GameBoard panel.
     * Should handle the logic of...
     * - which letters are correct
     * - update keyboard button colors
     * - update letterBox colors
     * - check if the game is over
     *
     * @author Frida Sjögren
     * @author Isabell Persson
     */
    private void compareGuessToWord() {

        String[] currentGuess = gui.getCurrentGuess(numberOfGuessesMade);

        if (gameLogic.isWhitespaceInWord(currentGuess)) {

            String[] newColorsForKeyboard = gameLogic.selectCorrectColorsForGameBoard(currentGuess);

            for (int i = 0; i < newColorsForKeyboard.length; i++) {
                changeKeyBoardButtonColor(currentGuess[i], newColorsForKeyboard[i]);
            }

            String[] newColorsForLetterBoxArray = new String[chosenWordLength];

            for (int i = 0; i < newColorsForKeyboard.length; i++) {
                newColorsForLetterBoxArray[i] = newColorsForKeyboard[i];
            }

            gui.updateLetterBoxColors(numberOfGuessesMade++, newColorsForLetterBoxArray);

            if (gameLogic.isWinner()) {
                gui.showMessage("CONGRATULATIONS!\nYOU GUESSED THE RIGHT WORD: " + wordToGuess + "\nPRESS MENU BUTTONS TO CONTINUE");
            }

        } else {
            gui.showMessage("You have not filled in all boxes with letters");
        }

        if (chosenMaxGuesses == numberOfGuessesMade && !gameLogic.isWinner()) {
            gui.showMessage("GAME OVER.\nTHE RIGHT WORD WAS: " + wordToGuess + "\nPRESS MENU BUTTONS TO CONTINUE");
        }
    }

    /**
     * Adds a letter to the first free letterBox in the GameBoard panel, in the row of the current guess. Used to
     * show the current guess after a keyboard button has been pressed. The method checks if the row is full before
     * adding the letter.
     *
     * @param letter a String, the letter of the pressed keyboard button that should be shown.
     * @author Frida Sjögren
     */
    private void updateNextFreeLetterBox(String letter) {
        if (isGuessRemaining()) {
            int currentGuessLength = gui.checkNumberOfFilledLetterBoxes(numberOfGuessesMade);
            if (currentGuessLength < chosenWordLength) {        //om alla letterboxes är fulla så läggs inget till
                gui.updateLetterBox(numberOfGuessesMade, currentGuessLength, letter);
            }
        }
    }

    /**
     * Method used to check if user has any remaining guesses to make
     *
     * @return Boolean for if a guess is allowed or not
     * @author Elin Piho
     */
    public boolean isGuessRemaining() {
        if (numberOfGuessesMade < chosenMaxGuesses) {
            return true;
        }
        return false;
    }

    /**
     * *insert javadoc*
     * @return boolean
     * @author Elin Piho
     */
    public boolean IsUserLoggedIn() {
        if (currentUser != null) {
            return true;
        }
        return false;
    }

    public int getChosenWordLength() {
        return chosenWordLength;
    }

    public int getChosenMaxGuesses() {
        return chosenMaxGuesses;
    }

    public String getChosenLanguage() {
        return chosenLanguage;
    }

    public String getWordToGuess() {
        return wordToGuess;
    }
}
