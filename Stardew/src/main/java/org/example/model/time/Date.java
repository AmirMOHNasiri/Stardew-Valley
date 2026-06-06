package org.example.model.time;

public class Date {
    private int hour;
    private int day;
    private Season season;

    public Date() {
        this.hour = 9;
        this.day = 1;
        this.season = Season.SPRING;
    }

    public String time() {
        return hour + ":00";
    }

    public String date() {
        return day + " of " + season.getSeason();
    }

    public String datetime() {
        return time() + "\n" + date();
    }

    public String dayOfTheWeek() {
        String[] daysOfWeek = {
                "Saturday",
                "Sunday",
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday"
        };
        return daysOfWeek[(day - 1) % 7];
    }

    public void addHour(int hourPlus) {
        hour += hourPlus;
        if (hour > 22) {
            addDay(1);
            hour = 9;
        }
    }

    public void addDay(int dayPlus) {
        day += dayPlus;
        if (day > 28) {
            day = 1;
            season = season.nextSeason();
        }
    }
}