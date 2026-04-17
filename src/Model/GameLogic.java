package Model;

import java.util.Arrays;

public class GameLogic {

    private String[] wordToGuessInLetters;
    private String[] colorArrayForLetterBox;

    private int amountOfCorrectLetters;
    private boolean isWinner = false;

    /**
     * Takes the correct word and break it up letter by letter.
     *
     * @param wordToGuess   the correct word the uses guesses on.
     *
     * @author Isabell Persson
     */
    public void addWordToGuess(String wordToGuess){
        wordToGuessInLetters = wordToGuess.split("");
    }

    /**
     * Checks each letter from the new guess and compares to the correct word. If the letter is correct
     * "GREEN" will be added to the list, if the letter is in the word but not on the right place "YELLOW"
     * will be added, if neither of above "GRAY" will be added.
     *
     * @param currentGuess  is the current guess the user has made.
     *
     * @return              an array of Strings with the new colors for each letterbox.
     *
     * @author Isabell Persson
     * @author Elin Piho
     */
    public String[] selectCorrectColorsForGameBoard(String[] currentGuess) {

        colorArrayForLetterBox = new String[currentGuess.length];

        int[] letterCount = new int[65536];
        for (String letter : wordToGuessInLetters) {
            letterCount[letter.charAt(0)]++;
        }

        for (int i = 0; i < wordToGuessInLetters.length; i++) {
            if (currentGuess[i].equals(wordToGuessInLetters[i])) {
                colorArrayForLetterBox[i] = "GREEN";
                letterCount[currentGuess[i].charAt(0)]--;
                amountOfCorrectLetters++;
            }
        }

        for (int i = 0; i < wordToGuessInLetters.length; i++) {
            if (colorArrayForLetterBox[i] != null) {
                continue;
            }

            char colorCheck = currentGuess[i].charAt(0);

            if (letterCount[colorCheck] > 0) {
                colorArrayForLetterBox[i] = "YELLOW";
                letterCount[colorCheck]--;
            }
            else {
                colorArrayForLetterBox[i] = "GRAY";
            }
        }

        if (amountOfCorrectLetters == wordToGuessInLetters.length){
            isWinner = true;

        }
        amountOfCorrectLetters = 0;

        return colorArrayForLetterBox;
    }




    /**
     * Checks if all the letterboxes has a letter and returns true or false depending on result.
     *
     * @param currentGuess  is the current guess the user has made.
     *
     * @return              true if all letterboxes has a letter, otherwise false.
     *
     * @author Isabell Persson
     */
    public boolean isWhitespaceInWord(String[] currentGuess){
        for (int i = 0; i < currentGuess.length; i++){
            if (currentGuess[i].isBlank()){
                return false;
            }
        }
        return true;
    }

    /**
     * Will be generating a word from database correct language and word length
     *
     * @author Isabell Persson
     */
    public String selectNewWord(int selectedWordLength, String selectedLanguage){
        return "NOT IMPLEMENTED YET";
    }

    /**
     * Returns isWinners value.
     *
     * @return  true or false depending on value of isWinner.
     *
     * @author Isabell Persson
     */
    public boolean isWinner(){
        return isWinner;
    }

    /**
     * Sets isWinner to false. Is called when resetting game.
     *
     * @author Isabell Persson
     */
    public void setIsWinnerToFalse(){
        isWinner = false;
    }
}
