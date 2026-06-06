package com.flexidle.flexidle_sb.model;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * game_id          int (PRIMARY KEY)
 * max_guesses      int
 * made_guesses     int
 * game_won         boolean
 * date             LocalDate
 */
@Entity
@Table(name = "game_record", schema = "flexidle")
public class GameRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int game_id;
    private int max_guesses;
    private int made_guesses;
    private boolean game_won;
    private LocalDate date;

    public GameRecord() {}

    public GameRecord(int max_guesses, int made_guesses, boolean game_won) {
        this.max_guesses = max_guesses;
        this.made_guesses = made_guesses;
        this.game_won = game_won;
        this.date = LocalDate.now();
    }

    public int getGame_id() {
        return game_id;
    }

    public int getMax_guesses() {
        return max_guesses;
    }

    public int getMade_guesses() {
        return made_guesses;
    }

    public boolean isGame_won() {
        return game_won;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setMax_guesses(int max_guesses) {
        this.max_guesses = max_guesses;
    }

    public void setMade_guesses(int made_guesses) {
        this.made_guesses = made_guesses;
    }

    public void setGame_won(boolean game_won) {
        this.game_won = game_won;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
