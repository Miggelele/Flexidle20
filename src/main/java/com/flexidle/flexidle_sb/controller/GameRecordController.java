package com.flexidle.flexidle_sb.controller;

import com.flexidle.flexidle_sb.model.GameRecord;
import com.flexidle.flexidle_sb.model.GameRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game_record")
public class GameRecordController {

    @Autowired
    private GameRecordService gameRecordService;

    @GetMapping("")
    public List<GameRecord> getAllGameRecords() {
        return gameRecordService.getAllGameRecords();
    }

    @GetMapping("/{id}")
    public GameRecord getGameRecordById(@PathVariable int id) {
        return gameRecordService.getGameRecordById(id);
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
