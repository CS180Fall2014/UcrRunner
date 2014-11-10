package com.murraycole.ucrrunner.view;

/**
 * Created by mbrevard on 11/9/14.
 */
public class Stats {
    double averageSpeed;
    double topSpeed;
    double caloriesBurned;
    double distance;
    int duration;
    //duration
    //current running speed
    double elevation; //extra

    Stats() {
        averageSpeed = -1.0;
        topSpeed = -1.0;
        caloriesBurned = -1.0;
        distance = -1.0;
        elevation = -1.0;
    }

    Stats(double averageSpeed, double topSpeed, double caloriesBurned, double distance, double elevation, int duration) {
        this.averageSpeed = averageSpeed;
        this.topSpeed = topSpeed;
        this.caloriesBurned = caloriesBurned;
        this.distance = distance;
        this.elevation = elevation;
        this.duration = duration;
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

    public void setDuration(int duration) { this.duration = duration; }

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

    public int getDuration() { return duration; }
}
