package com.flexidle.flexidle_sb.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameWordService {

    @Autowired
    private GameWordRepository gameWordRepository;

    public List<GameWord> getAllGameWords() {
        return gameWordRepository.findAll();
    }

    public GameWord getGameWordById(int id) {
        return gameWordRepository.findById(id).orElse(null);
    }

    public GameWord createGameWord(GameWord gameword) {
        return gameWordRepository.save(gameword);
    }

    public GameWord updateGameWord(int id, GameWord newGameword) {
        GameWord existingGameword = gameWordRepository.findById(id).orElse(null);
        if (existingGameword != null) {
            existingGameword.setWord(newGameword.getWord());
            existingGameword.setLanguage(newGameword.getLanguage());
            existingGameword.setDescription(newGameword.getDescription());
            return gameWordRepository.save(existingGameword);
        } else {
            return null;
        }
    }

    public void deleteGameWord(int id) {
        gameWordRepository.deleteById(id);
    }
}
