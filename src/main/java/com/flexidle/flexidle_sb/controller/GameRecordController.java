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
        int[][] stats = new int[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}};

        stats[0] = new int[]{10, 15, 35, 35, 5};
        stats[1] = new int[]{10, 12, 15, 25, 30, 8};
        stats[2] = new int[]{6, 8, 14, 18, 22, 25, 7};

        stats[3] = new int[]{8, 10, 20, 40, 22};
        stats[4] = new int[]{7, 8, 17, 25, 28, 15};
        stats[5] = new int[]{8, 8, 12, 15, 22, 25, 10};

        stats[6] = new int[]{7, 10, 20, 43, 20};
        stats[7] = new int[]{8, 8, 14, 25, 30, 15};
        stats[8] = new int[]{7, 8, 10, 15, 20, 20, 20};

        return stats;
    }

    @GetMapping("/allGlobalStatistics")
    public int[][] getAllGlobalStatistics() {
        int[][] stats = new int[][] {{}, {}, {}, {}, {}, {}, {}, {}, {}};

        stats[0] = new int[] { 5,10,30,45,10};
        stats[1] = new int[] { 5,10,20,20,35,10};
        stats[2] = new int[] { 3,7,10,15,20,30,15};

        stats[3] = new int[] { 3,5,15,45,32};
        stats[4] = new int[] { 2,3,12,35,25,23};
        stats[5] = new int[] { 3,3,10,19,20,20,25};

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

    @GetMapping("/personal/{username}/{language}/{wordLength}/{maxGuesses}")
    public int[] getPersonalStatistics(@PathVariable String username, @PathVariable String language, @PathVariable int wordLength, @PathVariable int maxGuesses) {
        List<GameRecord> allGameRecords = gameRecordService.getAllGameRecords();

        double[] winsInAmount = new double[maxGuesses];

        for (GameRecord record : allGameRecords) {

            if (record.getMade_guesses() != maxGuesses){
                continue;
            }

            int gameId = record.getGame_id();

            UsedWord usedWord = usedWordService.getUsedWordById(gameId);
            GameWord gameWord = gameWordService.getGameWordById(gameId);

            if (usedWord == null || gameWord == null) {
                continue;
            }
            if (record.getMade_guesses() < 0) {
                continue;
            }

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

        if (totalGames == 0) {
            System.out.println("winStatsInPercentages: " + Arrays.toString(winStatsInPercentages));
            return winStatsInPercentages;
        }

        for (int i  = 0; i < winStatsInPercentages.length; i++) {
            double percentage = (winsInAmount[i] * 100) / totalGames;
            winStatsInPercentages[i] = (int) Math.round(percentage);
        }

        int sumPercentages = 0;
        for (int value : winStatsInPercentages) {
            sumPercentages += value;
        }

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
