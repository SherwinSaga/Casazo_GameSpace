package com.example.casazo_gamespace.swipegame;
import com.example.casazo_gamespace.R;
import com.example.casazo_gamespace.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SwipeGameModel extends Game{
    private List<Integer> vectorAssetIds;
    private List<String> directions;

    private int currentAsset;


    public SwipeGameModel() {

        super(0,0);
        directions = new ArrayList<>();
        vectorAssetIds = Arrays.asList(
                R.drawable.swipegame_1up,
                R.drawable.swipegame_2down,
                R.drawable.swipegame_3right,
                R.drawable.swipegame_4left
        );

        currentAsset = new Random().nextInt(vectorAssetIds.size());
    }


    public List<String> getDirections() {
        return directions;
    }

    public void addDirection(String s){
        directions.add(s);
    }
    public int getCurrentAsset(){
        return currentAsset;
    }

    public void setCurrentAsset(int s){
        this.currentAsset = s;
    }

    public int getVectorAssetIdsSize(){
        return vectorAssetIds.size();
    }

    public List<Integer> getVectorAssetIds(){
        return vectorAssetIds;
    }

    public int getAssetID(int i){
        return vectorAssetIds.get(i);
    }

    public void clear(){
        directions.clear();
    }


}
