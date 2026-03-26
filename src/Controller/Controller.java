package Controller;

import View.GUI;
import javax.swing.*;
import java.util.Random;

/**
 * The Controller handles the GUI and starts the
 */
public class Controller {
    private GUI gui;
    public static final int[] MAX_GUESSES_OPTIONS = {5,6,7};
    public static final int[] WORD_LENGTH_OPTIONS = {4,5,6};
    public static final String[] LANGUAGE_OPTIONS = {"SWEDISH", "ENGLISH", "GERMAN"};

    private String wordToGuess;

    public Controller() {

        gui = new GUI(this, 900, 700);


    }

    public void mainMenuButtonPressed(String buttonName) {
        switch(buttonName){
            case "PLAY":
                System.out.println("Pressed PLAY in MainMenu.");
                gui.setPanel("GameMenu");
                break;

            case "LOG IN":
                System.out.println("Pressed LOG IN in MainMenu.");
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

    public void statisticsButtonPressed(String buttonName) {
        switch(buttonName){
            case "BACK TO MAIN MENU":
                System.out.println("Pressed BACK TO MAIN MENU in Statistics ");
                gui.setPanel("MainMenu");
                break;
            default:
                System.out.println("Invalid statistics button name.");
        }
    }

    public void gameMenuButtonPressed(String buttonName) {
        switch(buttonName){
            case "BACK TO MAIN MENU":
                System.out.println("Pressed BACK TO MAIN MENU in GameMenu");
                gui.setPanel("MainMenu");
                break;
            case "START GAME":
                System.out.println("Pressed START GAME in GameMenu.");
                //ToDo kolla om user gjort giltiga val av game mode innan spel startas. Kanske disabla knappen tills dess?
                gui.setPanel("GameBoard");
                int chosenMaxGuesses = 7;
                int chosenWordLength = 5;
                String chosenLanguage = "SWEDISH";
                startNewGame(chosenMaxGuesses, chosenWordLength, chosenLanguage);

                break;
            case "RANDOMIZE!":      //ska knappen starta spelet direkt? nu är det så
                System.out.println("Pressed RANDOMIZE OPTIONS! in GameMenu.");
                gui.setPanel("GameBoard");
                startNewGame(0, 0, null);

                break;

            default:
                System.out.println("Invalid gameMenu button name.");
        }
    }

    public void gameBoardButtonPressed(String buttonName) {
        switch(buttonName){
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
                break;
            case "ENTER GUESS":
                System.out.println("Pressed ENTER GUESS in GameBoard");
                changeKeyBoardButtonColor("A", "GREEN");        //for testing
                changeKeyBoardButtonColor("T", "GREEN");
                changeKeyBoardButtonColor("B", "YELLOW");
                changeKeyBoardButtonColor("X", "YELLOW");
                changeKeyBoardButtonColor("C", "GRAY");
                changeKeyBoardButtonColor("V", "GRAY");
                break;
            default:        //för alla bokstavsknappar i keyboard (i gameboard)
                System.out.println("pressed button " + buttonName + " in GameBoard");
        }
    }

    //colours: "GREEN", "GRAY", "YELLOW"
    private void changeKeyBoardButtonColor(String keyboardButton, String colour) {
        gui.changeKeyBoardButtonColor(keyboardButton, colour);
    }

    private void startNewGame(int chosenMaxGuesses, int chosenWordLength, String chosenLanguage) {
        if (chosenMaxGuesses == 0) {
            String[] newOptions = generateRandomOptions();
            chosenMaxGuesses = Integer.parseInt(newOptions[0]);
            chosenWordLength = Integer.parseInt(newOptions[1]);
            chosenLanguage = newOptions[2];
        }
        wordToGuess = generateNewWord(chosenWordLength, chosenLanguage);
    }

    private String generateNewWord(int wordLength, String language) {
        if (language.equals("SWEDISH")) {
            if (wordLength == 4) {
                return "STOL";
            } else if  (wordLength == 5) {
                return "KLUMP";
            } else if (wordLength == 6) {
                return "BOXARE";
            }
        } else if (language.equals("ENGLISH")) {
            if (wordLength == 4) {
                return "TURD";
            } else if  (wordLength == 5) {
                return "SILKY";
            } else if (wordLength == 6) {
                return "GOLDEN";
            }
        } else if (language.equals("GERMAN")) {
            if (wordLength == 4) {
                return "GRÜN";
            } else if  (wordLength == 5) {
                return "HANDY";
            } else if (wordLength == 6) {
                return "STRAßE";
            }
        }

        return "ÄPPLE";
    }

    private String[] generateRandomOptions() {
        return new String[] {"5", "6", "ENGLISH"};
    }
}
