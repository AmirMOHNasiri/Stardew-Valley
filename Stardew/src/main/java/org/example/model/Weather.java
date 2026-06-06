package org.example.model;

import java.util.Arrays;
import java.util.List;

public enum Weather {
    SUNNY(1f, Season.SPRING, Season.SUMMER, Season.FALL, Season.WINTER),
    RAIN(1.5f, Season.SPRING, Season.SUMMER, Season.FALL),
    STORM(1.5f, Season.SPRING, Season.SUMMER, Season.FALL),
    SNOW(2f, Season.WINTER);

    private final List<Season> possibleSeasons;
    private final float energyMultiplier;

    Weather(float energyMultiplier, Season... seasons) {
        this.possibleSeasons = List.of(seasons);
        this.energyMultiplier = energyMultiplier;
    }

    public float getEnergyMultiplier() {
        return energyMultiplier;
    }

    private static List<Weather> getWeatherForSeason(Season season) {
        return Arrays.stream(values())
                .filter(weather -> weather.possibleSeasons.contains(season))
                .toList();
    }

    public static Weather randomWeatherForSeason(Season season) {
        List<Weather> weathers = getWeatherForSeason(season);
        return weathers.get((int) (Math.random() * weathers.size()));
    }
}