package org.example.models;

public class WorkoutType {

    private int workoutTypeId;
    private String name;
    private String description;

    public WorkoutType() {
    }

    public WorkoutType(int trainingPlanId, String name, String description) {
        this.workoutTypeId = trainingPlanId;
        this.name = name;
        this.description = description;
    }

    public int getWorkoutTypeId() {
        return workoutTypeId;
    }

    public void setWorkoutTypeId(int trainingPlanId) {
        this.workoutTypeId = trainingPlanId;
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