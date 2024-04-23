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

    private int getDistanceId() {
        return distanceId;
    }

    private void setDistanceId(int distanceId) {
        this.distanceId = distanceId;
    }

    private String getDistance() {
        return distance;
    }

    private void setDistance(String distance) {
        this.distance = distance;
    }
}
