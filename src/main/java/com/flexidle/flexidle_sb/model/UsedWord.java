package com.flexidle.flexidle_sb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * game_id      int     (PRIMARY KEY)
 * username     max 12 char
 * word_id      int
 */
@Entity
@Table(name = "used_word", schema = "flexidle")
public class UsedWord {

    @Id
    private int game_id;
    private String username;
    private int word_id;

    public UsedWord() {}

    public UsedWord(int game_id, String username, int word_id) {
        this.game_id = game_id;
        this.username = username;
        this.word_id = word_id;
    }

    //GETTERS
    public int getGame_id() {
        return game_id;
    }

    public String getUsername() {
        return username;
    }

    public int getWord_id() {
        return word_id;
    }

    //SETTERS
    public void setWord_id(int word_id) {
        this.word_id = word_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

