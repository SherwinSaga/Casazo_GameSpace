package com.example.casazo_gamespace;

public class Game {
    private int HighScore;
    private int CurrentScore;

    public Game(){
        HighScore = 0;
        CurrentScore = 0;
    }

    public int getHighScore() {
        return HighScore;
    }

    public int getCurrentScore() {
        return CurrentScore;
    }

    public void incrementCurrentScore(){
        CurrentScore++;
    }
}
