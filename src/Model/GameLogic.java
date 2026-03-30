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
     */
    public String[] selectCorrectColorsForGameBoard(String[] currentGuess){

        colorArrayForLetterBox = new String[currentGuess.length];
        for (int i = 0; i < wordToGuessInLetters.length; i++){

           if (currentGuess[i].equals(wordToGuessInLetters[i])) {
                colorArrayForLetterBox[i] = "GREEN";
                amountOfCorrectLetters++;
           }
           else if (Arrays.asList(wordToGuessInLetters).contains(currentGuess[i])) {
               colorArrayForLetterBox[i] = "YELLOW";
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
     */
    public boolean isNoWhitespaceInWord(String[] currentGuess){
        for (int i = 0; i < currentGuess.length; i++){
            if (currentGuess[i].isBlank()){
                return false;
            }
        }
        return true;
    }

    /**
     * Will be generating a word from database correct language and word length
     */
    public String selectNewWord(int selectedWordLength, String selectedLanguage){
        return "NOT IMPLEMENTED YET";
    }

    /**
     * Returns isWinners value.
     *
     * @return  true or false depending on value of isWinner.
     */
    public boolean isWinner(){
        return isWinner;
    }

    /**
     * Sets isWinner to false. Is called when resetting game.
     */
    public void setIsWinnerToFalse(){
        isWinner = false;
    }
}
