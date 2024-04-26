package org.example.models;

import java.time.LocalDate;

public class Workout {

    private int workoutId;
    private int appUserId;
    private int trainingPlanId;
    private int workoutTypeId;
    private LocalDate date;
    private double distance;
    private String unit;
    private String description;
    private String effort;

    public Workout() {
    }

    public Workout(int workoutId, int appUserId, int trainingPlanId, int workoutTypeId, LocalDate date, double distance, String unit, String description, String effort) {
        this.workoutId = workoutId;
        this.appUserId = appUserId;
        this.trainingPlanId = trainingPlanId;
        this.workoutTypeId = workoutTypeId;
        this.date = date;
        this.distance = distance;
        this.unit = unit;
        this.description = description;
        this.effort = effort;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public int getTrainingPlanId() {
        return trainingPlanId;
    }

    public void setTrainingPlanId(int trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

    public int getWorkoutTypeId() {
        return workoutTypeId;
    }

    public void setWorkoutTypeId(int workoutTypeId) {
        this.workoutTypeId = workoutTypeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEffort() {
        return effort;
    }

    public void setEffort(String effort) {
        this.effort = effort;
    }
}
