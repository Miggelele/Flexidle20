package com.flexidle.flexidle_sb.model;

import jakarta.persistence.*;

/**
 * PRIMARY KEY word_id int, auto-generated.
 *
 * word_id      int
 * word         max 6 char
 * language     max 12 char
 * description  max 50 char
 */
@Entity
@Table(name = "game_word", schema = "flexidle" )
public class GameWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int word_id;
    private String word;
    private String language;
    private String description;

    protected GameWord() {}

    public GameWord(String word, String language, String description) {
        this.word = word;
        this.language = language;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format(
                "GameWord   10%d  20%s    20%s    20%s]",
                word_id, word, language, description);
    }

    //GETTERS
    public int getWord_id() {
        return word_id;
    }

    public String getWord() {
        return word;
    }

    public String getLanguage() {
        return language;
    }

    public String getDescription() {
        return description;
    }

    //SETTERS
    public void setWord(String word) {
        this.word = word;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

