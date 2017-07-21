package com.todmonster.funappsstudios.model.user;

public class Record {

    private int id;

    private String name;

    private int score;

    public Record(int score) {
        this.score = score;
    }

    public Record(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public Record() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
