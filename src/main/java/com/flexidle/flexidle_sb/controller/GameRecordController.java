package com.flexidle.flexidle_sb.controller;

import com.flexidle.flexidle_sb.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/game_record")
public class GameRecordController {

    @Autowired
    private GameRecordService gameRecordService;
    @Autowired
    private UsedWordService usedWordService;
    @Autowired
    private GameWordService gameWordService;

    @GetMapping("")
    public List<GameRecord> getAllGameRecords() {
        return gameRecordService.getAllGameRecords();
    }

    @GetMapping("/{id}")
    public GameRecord getGameRecordById(@PathVariable int id) {
        return gameRecordService.getGameRecordById(id);
    }

    /**
     * Finds statistics from game records in database for a given language and wordlength, for all users.
     * Used in frontend to generate circle diagrams in statistics menu.
     * Returns an int array, where the elements represent the percentages of games won for the number of
     * guesses matching that index. For example, [10, 20, 40, 30] means 10 % of games were won on
     * the first guess, 20 % of games won on the second guess.
     *
     * @param language      language setting for the statistics wanted
     * @param wordLength    word length for the statistics wanted
     * @param maxGuesses    max guesses for statistics wanted
     *
     * @return              an int[], representing percentages of wins for the different number of
     *                      guesses.
     *
     * @author Frida Sjögren
     * @author Isabell Perrson (re-wrote it, but keept the function intact)
     */
    @GetMapping("/global/{language}/{wordLength}/{maxGuesses}")
    public int[] getGlobalStatistics(@PathVariable String language, @PathVariable int wordLength, @PathVariable int maxGuesses) {

        List<GameRecord> allGameRecords = gameRecordService.getAllGameRecords();

        double[] winsInAmount = new double[maxGuesses];

        for (GameRecord gameRecord : allGameRecords) {

            if (gameRecord.getMax_guesses() != maxGuesses) {
                continue;
            }

            int gameId = gameRecord.getGame_id();

            GameWord gameWord = gameWordService.getGameWordById(gameId);

            if (gameWord == null) {
                continue;
            }

            if (!gameWord.getLanguage().equalsIgnoreCase(language)) {
                continue;
            }

            if (gameWord.getWord().length() != wordLength) {
                continue;
            }

            int madeGuesses = gameRecord.getMade_guesses();

            if (madeGuesses >= 1 && madeGuesses <= maxGuesses) {
                winsInAmount[madeGuesses - 1]++;
            }
        }

        int totalGames = 0;

        for (double wins : winsInAmount) {
            totalGames += wins;
        }

        int[] winStatsInPercentages = new int[maxGuesses];

        if (totalGames == 0) {
            return winStatsInPercentages;
        }

        for (int i = 0; i < winsInAmount.length; i++) {

            double percentage =
                    (winsInAmount[i] * 100.0) / totalGames;

            winStatsInPercentages[i] =
                    (int) Math.round(percentage);
        }

        int sumPercentages = 0;

        for (int percentage : winStatsInPercentages) {
            sumPercentages += percentage;
        }

        if (sumPercentages != 100) {
            winStatsInPercentages[0] += (100 - sumPercentages);
        }

        return winStatsInPercentages;
    }

    /**
     * Finds statistics from game records in database for a given language and wordlength, for a
     * specific user.
     * Used in frontend to generate circle diagrams in statistics menu.
     * Returns an int array, where the elements represent the percentages of games won for the number of
     * guesses matching that index. For example, [10, 20, 40, 30] means 10 % of games were won on
     * the first guess, 20 % of games won on the second guess.
     *
     * @param language      language setting for the statistics wanted
     * @param wordLength    word length for the statistics wanted
     * @param maxGuesses    max guesses for statistics wanted
     * @param username      the username for the statistics wanted.
     *
     * @return              an int[], representing percentages of wins for the different number of
     *                      guesses, for a specific user.
     *
     * @author Frida Sjögren
     */
    @GetMapping("/personal/{username}/{language}/{wordLength}/{maxGuesses}")
    public int[] getPersonalStatistics(@PathVariable String username, @PathVariable String language, @PathVariable int wordLength, @PathVariable int maxGuesses) {
        List<GameRecord> allGameRecords = gameRecordService.getAllGameRecords();

        //keeps track of how many games were won at different number of guesses
        double[] winsInAmount = new double[maxGuesses];

        for (int i  = 0; i < allGameRecords.size(); i++) {
            //Checks for games matching the maxGuesses parameter
            if (allGameRecords.get(i).getMax_guesses() == maxGuesses) {

                //Gets the word used in the game_record.
                int gameId = allGameRecords.get(i).getGame_id();
                UsedWord usedWord = usedWordService.getUsedWordById(gameId);

                //checks if the username matches
                if (usedWord.getUsername().equals(username)) {

                    GameWord gameWord = gameWordService.getGameWordById(gameId);

                    //Checks for matching language parameter
                    if (gameWord.getLanguage().equalsIgnoreCase(language)) {

                        //Checks for matching word length
                        if (gameWord.getWord().length() == wordLength) {

                            //if the game_record matches then an index matching the made guesses is increased by one.
                            winsInAmount[allGameRecords.get(i).getMade_guesses()]++;
                        }
                    }
                }
            }
        }

        int totalGames = 0;
        for (int i = 0; i < winsInAmount.length; i++) {
            totalGames += winsInAmount[i];
        }

        int[] winStatsInPercentages = new int[maxGuesses];

        for (int i  = 0; i < winStatsInPercentages.length; i++) {
            double percentage = winsInAmount[i] * 100 / totalGames;
            winStatsInPercentages[i] = Math.toIntExact(Math.round(percentage));
        }

        //sums the percentages to see if they add up to 100.
        int sumPercentages = 0;
        for (int i  = 0; i < winStatsInPercentages.length; i++) {
            sumPercentages += winStatsInPercentages[i];
        }

        //if a rounding error has occurred the first index is adjusted
        if (sumPercentages > 100) {
            winStatsInPercentages[0] -= (sumPercentages-100);
        } else if (sumPercentages < 100) {
            winStatsInPercentages[1] += (sumPercentages-100);
        }

        System.out.println("winStatsInPercentages: " + Arrays.toString(winStatsInPercentages));
        return winStatsInPercentages;
    }


    @PostMapping("")
    public GameRecord createGameRecord(@RequestBody GameRecord gameRecord) {
        return gameRecordService.createGameRecord(gameRecord);
    }

    @PutMapping("/{id}")
    public GameRecord updateGameRecord(@PathVariable int id, @RequestBody GameRecord gameRecord) {
        return gameRecordService.updateGameRecord(id, gameRecord);
    }

    @DeleteMapping("/{id}")
    public void deleteGameRecord(@PathVariable int id) {
        gameRecordService.deleteGameRecord(id);
    }


}
