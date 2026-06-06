package org.example.model;

import org.example.model.time.Date;
import org.example.model.time.Weather;

import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players;
    private Player currentPlayer;
    private Date date;
    private Weather todayWeather;
    private Weather tommorowWeather;
}
