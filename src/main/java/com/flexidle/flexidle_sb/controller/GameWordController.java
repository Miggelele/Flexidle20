package com.flexidle.flexidle_sb.controller;

import com.flexidle.flexidle_sb.model.GameWord;
import com.flexidle.flexidle_sb.model.GameWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        //just nu tar den första bästa som stämmer bara
        for (int i = 0; i < allGameWords.size(); i++) {
            int entryLength = allGameWords.get(i).getWord().length();
            String entryLanguage = allGameWords.get(i).getLanguage();

            if ( entryLength == length && entryLanguage.equalsIgnoreCase(language) ) {
                System.out.println("DEBUG i getWordWithParameters, output blev " + allGameWords.get(i).getWord());
                return allGameWords.get(i).getWord();
            }
        }

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
