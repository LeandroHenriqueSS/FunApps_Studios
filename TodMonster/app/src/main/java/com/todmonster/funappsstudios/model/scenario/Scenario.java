package com.todmonster.funappsstudios.model.scenario;

/**
 * An entity that represents a scenario in the game
 */
public class Scenario {

    private int currentLevel;

    private int totalTime;
    private int remainingTime;

    private int totalMonsters;
    private int numMonstersAlive;

    private int MAX_MONSTERS        = 60; // grid 10x6
    private int LOWEST_DURATION_SEC = 18;


    public Scenario(boolean smallScreen) {
        // initial values
        currentLevel = 1;
        totalTime = 40;
        totalMonsters = 6;
        numMonstersAlive = totalMonsters;
        remainingTime = totalTime;

        if (smallScreen) {
            MAX_MONSTERS = 45;
            LOWEST_DURATION_SEC = 12;
        }

    }

    public void nextLevel() {
        currentLevel++;
        totalMonsters += (totalMonsters < MAX_MONSTERS)    ? 2 : 0;
        totalTime     -= (totalTime > LOWEST_DURATION_SEC) ? 2 : 0;

        resetValues();
    }

    public void resetValues() {
        numMonstersAlive  = totalMonsters;
        remainingTime     = totalTime;
    }


    public void killMonster() {
        numMonstersAlive--;
    }

    public int getNumMonstersAlive() {
        return numMonstersAlive;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getTotalMonsters() {
        return totalMonsters;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void decRemainingTime() {
        if (remainingTime != 0)
            remainingTime--;
    }

    public int getNumMonsterKilled() {
        return totalMonsters - numMonstersAlive;
    }
}
