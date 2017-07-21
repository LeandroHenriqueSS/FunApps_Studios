package com.todmonster.funappsstudios.model.user;

/**
 * Created by Leandro on 6/21/2016.
 */
public class User {

    int score;

    int lives;

    public User() {
        lives = 3;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    //    /**
//     *
//     *
//     * @param level level achieved
//     * @param monsterKilled number of monster killed in the last level
//     */
//    public void generateScore(int level, int monsterKilled) {
//
//        for (int i = 0; i < level; i++) {
//            int tempScore = 5 + (level*2);
//
//            if (tempScore <)
//        }
//
//        score = level*;
//
//    }


}
