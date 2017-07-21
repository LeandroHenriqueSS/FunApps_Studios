package com.todmonster.funappsstudios.model.scenario;

/**
 *  An entity that represents a monster in the game
 */
public final class TodMonster {

    // position in the grid
    private int line;
    private int column;

    private boolean vulnerable;

    private Location location;

    /**
     * @param line The line side of the monster to be drawn
     * @param column The column side of the monster to be drawn
     */
    public TodMonster(final int line, final int column) {
        this.line = line;
        this.column = column;
    }

    public TodMonster() {}


    public boolean isVulnerable() {
        return vulnerable;
    }

    /** 2 monsters are considered equal if they are located in the same position in the grid */
    @Override
    public boolean equals(Object that) {
        return
                that instanceof TodMonster &&
                this.getLine() == ((TodMonster) that).getLine() &&
                this.getColumn() == ((TodMonster) that).getColumn();
    }


    public int getLine() {
        return line;
    }


    public int getColumn() {
        return column;
    }


    public void setLine(int line) {
        this.line = line;
    }


    public void setColumn(int column) {
        this.column = column;
    }


    public void setVulnerable(boolean vulnerable) {
        this.vulnerable = vulnerable;
    }


    public Location getLocation() {
        return location;
    }


    public void setLocation(Location location) {
        this.location = location;
    }

}