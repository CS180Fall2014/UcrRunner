package com.murraycole.ucrrunner.view.DAO;

/**
 * Created by Martin on 11/11/14.
 */
public class User {
    String sex = new String();
    String email = new String();
    String nickname = new String();
    String friends = new String();
    double age = 0.0,
           height = 0.0,
           weight = 0.0;

    byte[] picture;
    Stats stats = new Stats(); // hopefully this doesn't break anything
    int totalRunCount = 0;

    public int getTotalRunCount() {
        return totalRunCount;
    }

    public void setTotalRunCount(int totalRunCount) {
        this.totalRunCount = totalRunCount;
    }


    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }


    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
