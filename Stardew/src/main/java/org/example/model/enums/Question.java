package org.example.model.enums;

public enum Question {
    PET_QUESTION("What is your favorite pet?"),
    GAME_QUESTION("What is your favorite game?"),
    CAR_QUESTION("What is your favorite car?"),
    COLOR_QUESTION("What is your favorite color?");

    private final String question;

    Question(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }
}