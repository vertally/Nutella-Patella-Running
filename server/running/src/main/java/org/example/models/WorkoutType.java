package org.example.models;

public class WorkoutType {

    private int trainingPlanId;
    private String name;
    private String description;

    public WorkoutType() {
    }

    public WorkoutType(int trainingPlanId, String name, String description) {
        this.trainingPlanId = trainingPlanId;
        this.name = name;
        this.description = description;
    }

    public int getTrainingPlanId() {
        return trainingPlanId;
    }

    public void setTrainingPlanId(int trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}