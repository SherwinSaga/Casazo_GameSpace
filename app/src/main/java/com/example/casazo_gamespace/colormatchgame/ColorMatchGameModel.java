package com.example.casazo_gamespace.colormatchgame;

import java.util.Random;

public class ColorMatchGameModel {
    private int buttonState;
    private int arrowState;
    private int currentTime;
    private int startTime;
    private int currentPoints;
    private int originalStartTime;

    public ColorMatchGameModel() {
        this.buttonState = getRandomState();
        this.arrowState = getRandomState();
        this.currentTime = 1000;
        this.startTime = 1000;
        this.currentPoints = 0;
        this.originalStartTime = 1000;
    }
    //GETTERS
    public int getButtonState() {return buttonState;}
    public int getArrowState() {return arrowState;}
    public int getCurrentTime() {return currentTime;}
    public int getStartTime() {return startTime;}
    public int getCurrentPoints() {return currentPoints;}
    public int getOriginalStartTime() {return originalStartTime;}

    //SETTERS
    public void setButtonState(int state) {this.buttonState = state;}
    public void setArrowState(int state) {this.arrowState = state;}
    public void setCurrentTime(int time) {this.currentTime = time;}
    public void setStartTime(int time) {this.startTime = time;}
    public void setOriginalStartTime(int originalStartTime) {this.originalStartTime = originalStartTime;}
    public void setCurrentPoints(int points) {this.currentPoints = points;}
    public void decreaseCurrentTime(int time) {this.currentTime -= time;}

    private int getRandomState() {
        return new Random().nextInt(4) + 1;
    }
}
