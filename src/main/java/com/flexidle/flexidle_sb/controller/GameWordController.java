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
    public GameWord getGameWordById(@PathVariable Long id) {
        return gameWordService.getGameWordById(id);
    }

    @GetMapping("/{length}/{language}")
    public String getWordWithParameters(@PathVariable int length, @PathVariable String language) {
        List<GameWord> allGameWords = gameWordService.getAllGameWords();

        Random random = new Random();
        int randomIndex;
        GameWord randomGameWord;

        boolean wordSelected = false;

        // will loop until a random word with correct properties is found in the full list of words
        while (!wordSelected) {
            randomIndex = random.nextInt(allGameWords.size());
            randomGameWord = allGameWords.get(randomIndex);

            if ( randomGameWord.getWord().length() == length && randomGameWord.getLanguage().equalsIgnoreCase(language) ) {
                System.out.println("DEBUG i getWordWithParameters, output blev " + randomGameWord.getWord());
                //ToDo Fundera om det är bäst att returnera bara String eller om hela objektet är bättre?
                return randomGameWord.getWord();
            }
        }

        //just nu tar den första bästa som stämmer bara
//        for (int i = 0; i < allGameWords.size(); i++) {
//            int entryLength = allGameWords.get(i).getWord().length();
//            String entryLanguage = allGameWords.get(i).getLanguage();
//
//            if ( entryLength == length && entryLanguage.equalsIgnoreCase(language) ) {
//                System.out.println("DEBUG i getWordWithParameters, output blev " + allGameWords.get(i).getWord());
//                return allGameWords.get(i).getWord();
//            }
//        }

        return null;
    }


    @PostMapping("")
    public GameWord createGameWord(@RequestBody GameWord gameword) {
        return gameWordService.createGameWord(gameword);
    }

    @PutMapping("/{id}")
    public GameWord updateGameWord(@PathVariable Long id, @RequestBody GameWord gameword) {
        return gameWordService.updateGameWord(id, gameword);
    }

    @DeleteMapping("/{id}")
    public void deleteGameWord(@PathVariable Long id) {
        gameWordService.deleteGameWord(id);
    }

}
