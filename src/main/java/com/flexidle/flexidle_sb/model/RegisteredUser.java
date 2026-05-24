package com.flexidle.flexidle_sb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * PRIMARY KEY: username
 * username max 12 char
 * password max 24 char
 * s_question max 50 char
 * s_answer max 12 char ??!!! räcker det?
 */
@Entity
@Table(name = "registered_user", schema = "flexidle")
public class RegisteredUser {

    @Id
    private String username;    //max 12 char
    private String password;    //max 24 char
    private String s_question;  //max 50 char
    private String s_answer;    //max 12 char

    public RegisteredUser() {}

    public RegisteredUser(String username, String password, String s_question, String s_answer) {
        this.username = username;
        this.password = password;
        this.s_question = s_question;
        this.s_answer = s_answer;
    }

    //GETTERS
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getS_question() {
        return s_question;
    }

    public String getS_answer() {
        return s_answer;
    }

    //SETTERS
    public void setPassword(String password) {
        this.password = password;
    }

    public void setS_question(String s_question) {
        this.s_question = s_question;
    }

    public void setS_answer(String s_answer) {
        this.s_answer = s_answer;
    }
}
