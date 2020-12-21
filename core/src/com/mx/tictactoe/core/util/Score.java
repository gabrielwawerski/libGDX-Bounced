package com.mx.tictactoe.core.util;

public class Score {
    private int score;

    public Score(int score) {
        this.score = score;
    }

    public Score() {
        score = 0;
    }

    public void addScore() {
        score++;
    }

    public void resetScore() {
        score = 0;
    }

    public int getScore() {
        return score;
    }
}
