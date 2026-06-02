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


    @GetMapping("/allPersonalStatistics/{username}")
    public int[][] getAllPersonalStatistics(@PathVariable String username) {
        int[][] stats = new int[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}}; //plats för 9 diagram

        //TODO felhantering av username när riktiga funktionen implementeras
        //personal
        //swedish
        stats[0] = new int[]{10, 15, 35, 35, 5};
        stats[1] = new int[]{10, 12, 15, 25, 30, 8};
        stats[2] = new int[]{6, 8, 14, 18, 22, 25, 7};
        //english
        stats[3] = new int[]{8, 10, 20, 40, 22};
        stats[4] = new int[]{7, 8, 17, 25, 28, 15};
        stats[5] = new int[]{8, 8, 12, 15, 22, 25, 10};
        //german
        stats[6] = new int[]{7, 10, 20, 43, 20};
        stats[7] = new int[]{8, 8, 14, 25, 30, 15};
        stats[8] = new int[]{7, 8, 10, 15, 20, 20, 20};

        return stats;
    }

    @GetMapping("/allGlobalStatistics")
    public int[][] getAllGlobalStatistics() {
        int[][] stats = new int[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}}; //plats för 9 diagram
        //global
        //swedish
        stats[0] = new int[] { 5,10,30,45,10};
        stats[1] = new int[] { 5,10,20,20,35,10};
        stats[2] = new int[] { 3,7,10,15,20,30,15};
        //english
        stats[3] = new int[] { 3,5,15,45,32};
        stats[4] = new int[] { 2,3,12,35,25,23};
        stats[5] = new int[] { 3,3,10,19,20,20,25};
        //german
        stats[6] = new int[] { 2,5,15,38,40};
        stats[7] = new int[] { 3,3,15,30,29,20};
        stats[8] = new int[] { 2,3,5,15,25,25,25};

        return stats;
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
//    @GetMapping("/personal/{username}/{language}/{wordLength}/{maxGuesses}")
//    public int[] getPersonalStatistics(@PathVariable String username, @PathVariable String language, @PathVariable int wordLength, @PathVariable int maxGuesses) {
//        List<GameRecord> allGameRecords = gameRecordService.getAllGameRecords();
//
//        //keeps track of how many games were won at different number of guesses
//        double[] winsInAmount = new double[maxGuesses];
//
//        for (int i  = 0; i < allGameRecords.size(); i++) {
//            //Checks for games matching the maxGuesses parameter
//            if (allGameRecords.get(i).getMax_guesses() == maxGuesses) {
//
//                //Gets the word used in the game_record.
//                int gameId = allGameRecords.get(i).getGame_id();
//                UsedWord usedWord = usedWordService.getUsedWordById(gameId);
//
//                //checks if the username matches
//                if (usedWord.getUsername().equals(username)) {
//
//                    GameWord gameWord = gameWordService.getGameWordById(gameId);
//
//                    //Checks for matching language parameter
//                    if (gameWord.getLanguage().equalsIgnoreCase(language)) {
//
//                        //Checks for matching word length
//                        if (gameWord.getWord().length() == wordLength) {
//
//                            //if the game_record matches then an index matching the made guesses is increased by one.
//                            winsInAmount[allGameRecords.get(i).getMade_guesses()]++;
//                        }
//                    }
//                }
//            }
//        }
//
//        int totalGames = 0;
//        for (int i = 0; i < winsInAmount.length; i++) {
//            totalGames += winsInAmount[i];
//        }
//
//        int[] winStatsInPercentages = new int[maxGuesses];
//
//        for (int i  = 0; i < winStatsInPercentages.length; i++) {
//            double percentage = winsInAmount[i] * 100 / totalGames;
//            winStatsInPercentages[i] = Math.toIntExact(Math.round(percentage));
//        }
//
//        //sums the percentages to see if they add up to 100.
//        int sumPercentages = 0;
//        for (int i  = 0; i < winStatsInPercentages.length; i++) {
//            sumPercentages += winStatsInPercentages[i];
//        }
//
//        //if a rounding error has occurred the first index is adjusted
//        if (sumPercentages > 100) {
//            winStatsInPercentages[0] -= (sumPercentages-100);
//        } else if (sumPercentages < 100) {
//            winStatsInPercentages[1] += (sumPercentages-100);
//        }
//
//        System.out.println("winStatsInPercentages: " + Arrays.toString(winStatsInPercentages));
//        return winStatsInPercentages;
//    }

    @GetMapping("/personal/{username}/{language}/{wordLength}/{maxGuesses}")
    public int[] getPersonalStatistics(@PathVariable String username, @PathVariable String language, @PathVariable int wordLength, @PathVariable int maxGuesses) {
        List<GameRecord> allGameRecords = gameRecordService.getAllGameRecords();

        //keeps track of how many games were won at different number of guesses
        double[] winsInAmount = new double[maxGuesses];

        for (GameRecord record : allGameRecords) {

            if (record.getMade_guesses() != maxGuesses){
                continue;
            }

            int gameId = record.getGame_id();

            UsedWord usedWord = usedWordService.getUsedWordById(gameId);
            GameWord gameWord = gameWordService.getGameWordById(gameId);

            // checks for 500 errors
            if (usedWord == null || gameWord == null) {
                continue;
            }
            if (record.getMade_guesses() < 0) {
                continue;
            }

            // checks statistics for the right person and language
            if (!usedWord.getUsername().equals(username)) {
                continue;
            }
            if (!gameWord.getLanguage().equalsIgnoreCase(language)) {
                continue;
            }
            if (gameWord.getWord().length() != wordLength) {
                continue;
            }

            int guesses = record.getMade_guesses();

            if (guesses >= 0 && guesses < winsInAmount.length) {
                winsInAmount[guesses]++;
            }

        }

        double totalGames = 0;
        for (double game : winsInAmount) {
            totalGames += game;
        }

        int[] winStatsInPercentages = new int[maxGuesses];

        // if 0 return now to prevent division by 0
        if (totalGames == 0) {
            System.out.println("winStatsInPercentages: " + Arrays.toString(winStatsInPercentages));
            return winStatsInPercentages;
        }

        for (int i  = 0; i < winStatsInPercentages.length; i++) {
            double percentage = (winsInAmount[i] * 100) / totalGames;
            winStatsInPercentages[i] = (int) Math.round(percentage);
        }

        //sums the percentages to see if they add up to 100.
        int sumPercentages = 0;
        for (int value : winStatsInPercentages) {
            sumPercentages += value;
        }

        //if a rounding error has occurred the first index is adjusted
        if (sumPercentages != 100 && totalGames > 0) {
            int difference = 100 - sumPercentages;
            winStatsInPercentages[0] += difference;
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
