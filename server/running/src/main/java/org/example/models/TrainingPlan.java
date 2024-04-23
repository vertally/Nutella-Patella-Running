package org.example.models;

import java.time.LocalDate;

public class TrainingPlan {

    private int trainingPlanId;
    private int appUserId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;

    public TrainingPlan() {
    }

    public TrainingPlan(int trainingPlanId, int appUserId, String name, LocalDate startDate, LocalDate endDate, String description) {
        this.trainingPlanId = trainingPlanId;
        this.appUserId = appUserId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public int getTrainingPlanId() {
        return trainingPlanId;
    }

    public void setTrainingPlanId(int trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
