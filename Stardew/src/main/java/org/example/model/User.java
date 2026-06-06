package org.example.model;


import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    private String nickname;
    private String email;
    private String gender;
    @Enumerated(EnumType.STRING)
    private Question question;
    private String answer;
    @Column(name = "game_played")
    private int gamePlayed;
    @Column(name = "high_score")
    private int highScore;
    @Transient
    private Game currentGame;
    @Column(name = "stay_logged_in")
    private boolean stayLoggedIn;

    public User() {}

    public User(String username, String passwordHash, String nickname, String email, String gender) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.gamePlayed = 0;
        this.highScore = 0;
        this.currentGame = null;
        this.stayLoggedIn = false;
    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getGender() {
        return gender;
    }
    public Question getQuestion() {
        return question;
    }
    public void setQuestion(Question question) {
        this.question = question;
    }
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public int getGamePlayed() {
        return gamePlayed;
    }
    public void setGamePlayed(int gamePlayed) {
        this.gamePlayed = gamePlayed;
    }
    public int getHighScore() {
        return highScore;
    }
    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
    public Game getCurrentGame() {
        return currentGame;
    }
    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }
    public boolean isStayLoggedIn() {
        return stayLoggedIn;
    }
    public void setStayLoggedIn(boolean stayLoggedIn) {
        this.stayLoggedIn = stayLoggedIn;
    }
}