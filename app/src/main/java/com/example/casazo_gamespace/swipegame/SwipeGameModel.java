package com.example.casazo_gamespace.swipegame;

import com.example.casazo_gamespace.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SwipeGameModel {
    private List<String> directions;
    private int score;

    public SwipeGameModel() {
        directions = new ArrayList<>();
        score = 0;
    }

    public void generateRandomDirection() {
        String[] allDirections = {"UP", "DOWN", "LEFT", "RIGHT"};
        Random random = new Random();
        String randomDirection = allDirections[random.nextInt(allDirections.length)];
        directions.add(randomDirection);
    }

    public List<String> getDirections() {
        return directions;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score++;
    }

    public void resetGame() {
        directions.clear();
        score = 0;
    }
}
