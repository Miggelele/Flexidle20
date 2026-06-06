package com.flexidle.flexidle_sb.controller;

import com.flexidle.flexidle_sb.model.GameWord;
import com.flexidle.flexidle_sb.model.GameWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/game_word")
public class GameWordController {

    @Autowired
    private GameWordService gameWordService;

    @GetMapping("")
    public List<GameWord> getAllGameWords() {
        return gameWordService.getAllGameWords();
    }

    @GetMapping("/{id}")
    public GameWord getGameWordById(@PathVariable int id) {
        return gameWordService.getGameWordById(id);
    }

    /**
     * This method is called when frontend requests a word for a new game, with the selected settings
     * as parameters. This method randomly queries the database table game_word until a word matching
     * the settings is found and returned. If parameters are invalid (possibly from user tampering
     * with url), a default message ("INVALID REQUEST") is returned to frontend.
     *
     * @param length        an int, the selected length of the word.
     * @param language      a String, the selected
     *
     * @return              a String, a randomly selected word from the database that matches the given
     *                      setting parameters.
     *
     * @author Frida Sjögren
     */
    @GetMapping("/{length}/{language}")
    public GameWord getWordWithParameters(@PathVariable int length, @PathVariable String language) {
        List<GameWord> allGameWords = gameWordService.getAllGameWords();

        Random random = new Random();
        int randomIndex;
        GameWord randomGameWord;

        for (int i = 0; i < allGameWords.size(); i++) {
            randomIndex = random.nextInt(allGameWords.size());
            randomGameWord = allGameWords.get(randomIndex);

            if ( randomGameWord.getWord().length() == length && randomGameWord.getLanguage().equalsIgnoreCase(language) ) {
                System.out.println(randomGameWord.getWord());
                return randomGameWord;
            }
        }
        return null;
    }

    @PostMapping("")
    public GameWord createGameWord(@RequestBody GameWord gameword) {
        return gameWordService.createGameWord(gameword);
    }

    @PutMapping("/{id}")
    public GameWord updateGameWord(@PathVariable int id, @RequestBody GameWord gameword) {
        return gameWordService.updateGameWord(id, gameword);
    }

    @DeleteMapping("/{id}")
    public void deleteGameWord(@PathVariable int id) {
        gameWordService.deleteGameWord(id);
    }

}
