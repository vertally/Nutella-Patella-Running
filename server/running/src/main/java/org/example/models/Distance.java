package org.example.models;

public class Distance {

    private int distanceId;
    private String distance;

    public Distance() {
    }

    public Distance(int distanceId, String distance) {
        this.distanceId = distanceId;
        this.distance = distance;
    }

    public int getDistanceId() {
        return distanceId;
    }

    public void setDistanceId(int distanceId) {
        this.distanceId = distanceId;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
