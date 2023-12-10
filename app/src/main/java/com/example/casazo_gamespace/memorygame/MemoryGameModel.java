package com.example.casazo_gamespace.memorygame;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MemoryGameModel{
    private ArrayList<String> colors;
    private ArrayList<String> currentSequence;
    private ArrayList<String> userSequence;
    private int round;
    private int score;

    public MemoryGameModel() {
        colors = new ArrayList<>();
        currentSequence = new ArrayList<>();
        userSequence = new ArrayList<>();
        colors.add("red");
        colors.add("blue");
        colors.add("green");
        colors.add("yellow");
        round = 1;
        score = 0;
    }

    public List<String> getColors() {
        return colors;
    }
    public List<String> getCurrentSequence(){
        return currentSequence;
    }
    public void addRandomSequence(){
        Random r = new Random();
        currentSequence.add(colors.get(r.nextInt(4)));
        round = round + 1;
    }
    public List<String> getUserSequence(){
        return userSequence;
    }

    public void addUserSequence(String string){
        userSequence.add(string);
    }
    public int getRound(){
        return round;
    }
    public int getScore(){return score;}
    public void incrementScore(){score++;}

    public void clear() {
        currentSequence.clear();
        userSequence.clear();
        round = 1;
        score = 0;
    }
}

