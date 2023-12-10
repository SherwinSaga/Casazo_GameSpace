package com.example.casazo_gamespace.memorygame;

import java.util.ArrayList;
import java.util.Random;

public class MemoryGame {

    public ArrayList<String> colors = new ArrayList<>();
    public ArrayList<String> currentPattern = new ArrayList<>();
    public int round = 1;
    public int score = 0;

    public MemoryGame(){
        colors.add("red");
        colors.add("blue");
        colors.add("green");
        colors.add("yellow");
    }
    public int getRandomInt(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
    public ArrayList<String> getPattern() {
        currentPattern.add(colors.get(getRandomInt(0, 3)));
        round = round + 1;
        return currentPattern;
    }

}
