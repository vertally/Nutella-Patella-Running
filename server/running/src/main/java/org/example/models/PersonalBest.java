package org.example.models;

import java.time.LocalDate;

public class PersonalBest {

    private int personalBestId;
    private int appUserId;
    private int distanceId;
    private String time;
    private LocalDate date;

    public PersonalBest() {
    }

    public PersonalBest(int personalBestId, int appUserId, int distanceId, String time, LocalDate date) {
        this.personalBestId = personalBestId;
        this.appUserId = appUserId;
        this.distanceId = distanceId;
        this.time = time;
        this.date = date;
    }

    public int getPersonalBestId() {
        return personalBestId;
    }

    public void setPersonalBestId(int personalBestId) {
        this.personalBestId = personalBestId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public int getDistanceId() {
        return distanceId;
    }

    public void setDistanceId(int distanceId) {
        this.distanceId = distanceId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
