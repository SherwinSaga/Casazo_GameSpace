package com.example.casazo_gamespace.memorygame;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MemoryGameModel{
    private ArrayList<String> colors;
    private ArrayList<String> currentSequence;
    private ArrayList<String> userSequence;
    private int round;

    public MemoryGameModel() {
        colors = new ArrayList<>();
        currentSequence = new ArrayList<>();
        userSequence = new ArrayList<>();
        colors.add("red");
        colors.add("blue");
        colors.add("green");
        colors.add("yellow");
        round = 1;
    }
    //GETTERS
    public ArrayList<String> getColors() { return colors; }
    public List<String> getCurrentSequence(){
        return currentSequence;
    }
    public List<String> getUserSequence(){
        return userSequence;
    }
    public int getRound(){
        return round;
    }

    //SETTERS
    public void addRandomSequence(){
        Random r = new Random();
        currentSequence.add(colors.get(r.nextInt(4)));
        round = round + 1;
    }
    public void addUserSequence(String string){
        userSequence.add(string);
    }

    public void clear() {
        currentSequence.clear();
        userSequence.clear();
        round = 1;
    }
}

