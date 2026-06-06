package org.example.model.time;

public enum Season {
    SPRING("Spring"),
    SUMMER("Summer"),
    FALL("Fall"),
    WINTER("Winter");

    private final String season;

    Season(String season) {
        this.season = season;
    }

    public String getSeason() {
        return season;
    }

    public Season nextSeason() {
        return values()[(this.ordinal() + 1) % values().length];
    }
}