package org.example.model;


import jakarta.persistence.*;
import org.example.model.enums.Question;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String passwordHash;
    private String nickname;
    private String email;
    private String gender;
    private Question question;
    private String answer;
    private int gamePlayed;
    private int highScore;
    @Transient
    private Game currentGame;
}