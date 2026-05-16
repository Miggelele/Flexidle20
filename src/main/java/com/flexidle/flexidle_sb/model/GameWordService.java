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

    public GameWord getGameWordById(Long id) {
        return gameWordRepository.findById(id).orElse(null);
    }

    public GameWord createGameWord(GameWord gameword) {
        return gameWordRepository.save(gameword);
    }

    public GameWord updateGameWord(Long id, GameWord gameword) {
        GameWord existingGameword = gameWordRepository.findById(id).orElse(null);
        if (existingGameword != null) {
            existingGameword.setWord(gameword.getWord());
            existingGameword.setLanguage(gameword.getLanguage());
            existingGameword.setDescription(gameword.getDescription());
            return gameWordRepository.save(existingGameword);
        } else {
            return null;
        }
    }

    public void deleteGameWord(Long id) {
        gameWordRepository.deleteById(id);
    }
}
