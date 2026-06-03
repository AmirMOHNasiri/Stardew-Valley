package org.example.model;


import org.example.model.enums.Question;

public class User {
    private String username;
    private String passwordHash;
    private String nickname;
    private String email;
    private String gender;
    private Question question;
    private String answer;
    private int gamePlayed;
    private int highScore;
    private Game currentGame;
}