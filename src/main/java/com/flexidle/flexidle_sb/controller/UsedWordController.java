package com.flexidle.flexidle_sb.controller;

import com.flexidle.flexidle_sb.model.UsedWord;
import com.flexidle.flexidle_sb.model.UsedWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/used_word")
public class UsedWordController {

    @Autowired
    private UsedWordService usedWordService;

    @GetMapping("")
    public List<UsedWord> getAllUsedWords() {
        return usedWordService.getAllUsedWords();
    }

    @GetMapping("/{id}")
    public UsedWord getUsedWordById(@PathVariable int id) {
        return usedWordService.getUsedWordById(id);
    }

    @PostMapping("")
    public UsedWord createUsedWord(@RequestBody UsedWord usedWord) {
        return usedWordService.createUsedWord(usedWord);
    }

    @PutMapping("/{id}")
    public UsedWord updateUsedWord(@PathVariable int id, @RequestBody UsedWord usedWord) {
        return usedWordService.updateUsedWord(id, usedWord);
    }

    @DeleteMapping("/{id}")
    public void deleteUsedWord(@PathVariable int id) {
        usedWordService.deleteUsedWord(id);
    }




}
