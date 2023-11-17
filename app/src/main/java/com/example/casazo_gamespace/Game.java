package com.example.casazo_gamespace;

public class Game {
    private int HighScore;
    private int CurrentScore;

    public Game(int n, int j){
        HighScore = n;
        CurrentScore = j;
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

    public void setHighScore(int n){
        this.HighScore = n;
    }

    public void setCurrentScore(int n){
        this.CurrentScore = n;
    }
}
