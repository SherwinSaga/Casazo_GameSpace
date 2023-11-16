package com.example.casazo_gamespace.swipegame;
import com.example.casazo_gamespace.R;
import com.example.casazo_gamespace.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SwipeGameModel {
    private List<Integer> vectorAssetIds;
    private List<String> directions;
    private int score;
    private int currentAsset;

    public SwipeGameModel() {
        directions = new ArrayList<>();
        score = 0;
        vectorAssetIds = Arrays.asList(
                R.drawable.swipegame_1up,
                R.drawable.swipegame_2down,
                R.drawable.swipegame_3right,
                R.drawable.swipegame_4left
        );

        currentAsset = new Random().nextInt(vectorAssetIds.size());
    }

    public void generateRandomDirection() {
        String[] allDirections = {"UP", "DOWN", "LEFT", "RIGHT"};
        Random random = new Random();
        String randomDirection = allDirections[random.nextInt(allDirections.length)];
        directions.add(randomDirection);

    }

    public int getRandomVectorAssetId() {

        int randomIndex;
        while(true){
            randomIndex = new Random().nextInt(vectorAssetIds.size());
            if(randomIndex != currentAsset){ //to prevent same asset twice a row
                break;
            }
        }
        currentAsset = randomIndex;
        return vectorAssetIds.get(randomIndex);
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
