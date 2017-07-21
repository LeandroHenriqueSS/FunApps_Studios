package com.todmonster.funappsstudios.model.scenario;

/**
 * A Location for a TodMonster
 *
 * Created by Leandro on 12/6/2015.
 */
public class Location {

    private int left;
    private int right;
    private int top;
    private int bottom;

    public Location(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }
}
