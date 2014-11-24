package com.murraycole.ucrrunner.view.DAO;

/**
 * Created by Martin on 11/23/14.
 */
public class Message {
    String timestamp = new String();
    String message = new String();
    int from = 0;
    int to = 0;

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }


    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
