package dev.mobile.bai3.model;

public class Task {
    private long id;
    private String title;
    private String location;
    private String date;
    private boolean state;

    public Task(long id, String title, String location, String date, boolean state) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.date = date;
        this.state = state;
    }

    public Task(String title, String location, String date, boolean state) {
        this.title = title;
        this.location = location;
        this.date = date;
        this.state = state;
    }

    public boolean isState() {
        return state;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return title + " " + location + " " + date;
    }

    public boolean getState() {
        return state;
    }

    public void toggleState() {
        state = !state;
    }
}

