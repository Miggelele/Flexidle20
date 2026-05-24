package com.flexidle.flexidle_sb.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsedWordService {

    @Autowired
    private UsedWordRepository usedWordRepository;

    public List<UsedWord> getAllUsedWords() {
        return usedWordRepository.findAll();
    }

    public UsedWord getUsedWordById(int id) {
        return usedWordRepository.findById(id).orElse(null);
    }

    public UsedWord createUsedWord(UsedWord usedWord) {
        return usedWordRepository.save(usedWord);
    }

    public UsedWord updateUsedWord(int id, UsedWord newUsedWord) {
        UsedWord existingUsedWord = usedWordRepository.findById(id).orElse(null);
        if (existingUsedWord != null) {
            existingUsedWord.setUsername(newUsedWord.getUsername());
            existingUsedWord.setWord_id(newUsedWord.getWord_id());
            return usedWordRepository.save(existingUsedWord);
        } else {
            return null;
        }
    }

    public void deleteUsedWord(int id) {
        usedWordRepository.deleteById(id);
    }

}
