package com.flexidle.flexidle_sb.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameRecordService {

    @Autowired
    private GameRecordRepository gameRecordRepository;

    public List<GameRecord> getAllGameRecords() {
        return gameRecordRepository.findAll();
    }

    public GameRecord getGameRecordById(int id) {
        return gameRecordRepository.findById(id).orElse(null);
    }

    public GameRecord createGameRecord(GameRecord gameRecord) {
        return gameRecordRepository.save(gameRecord);
    }

    public GameRecord updateGameRecord(int id, GameRecord newGameRecord) {
        GameRecord existingGameRecord = gameRecordRepository.findById(id).orElse(null);
        if (existingGameRecord != null) {
            existingGameRecord.setMax_guesses(newGameRecord.getMax_guesses());
            existingGameRecord.setMade_guesses(newGameRecord.getMade_guesses());
            existingGameRecord.setGame_won(newGameRecord.isGame_won());
            existingGameRecord.setDate(newGameRecord.getDate());
            return gameRecordRepository.save(existingGameRecord);
        } else {
            return null;
        }
    }

    public void deleteGameRecord(int id) {
        gameRecordRepository.deleteById(id);
    }

}
