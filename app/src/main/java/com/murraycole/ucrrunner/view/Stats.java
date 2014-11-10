package com.murraycole.ucrrunner.view;

/**
 * Created by mbrevard on 11/9/14.
 */
public class Stats {
    double averageSpeed;
    double topSpeed;
    double caloriesBurned;
    double distance;
    double elevation;

    Stats() {
        averageSpeed = -1.0;
        topSpeed = -1.0;
        caloriesBurned = -1.0;
        distance = -1.0;
        elevation = -1.0;
    }

    Stats(double averageSpeed, double topSpeed, double caloriesBurned, double distance, double elevation) {
        this.averageSpeed = averageSpeed;
        this.topSpeed = topSpeed;
        this.caloriesBurned = caloriesBurned;
        this.distance = distance;
        this.elevation = elevation;
    }


    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public void setTopSpeed(double topSpeed) {
        this.topSpeed = topSpeed;
    }

    public void setCaloriesBurned(double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public double getTopSpeed() {
        return topSpeed;
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    public double getDistance() {
        return distance;
    }

    public double getElevation() {
        return elevation;
    }
}
