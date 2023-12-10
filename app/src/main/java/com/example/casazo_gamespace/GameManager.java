package com.example.casazo_gamespace;
import com.example.casazo_gamespace.colormatchgame.ColorMatchGameController;
import com.example.casazo_gamespace.colormatchgame.ColorMatchGameModel;
import com.example.casazo_gamespace.colormatchgame.ColorMatchGameView;
import com.example.casazo_gamespace.swipegame.SwipeGameView;

import java.util.Random;

public class GameManager implements OnGameStatusChangedListener {
    private MainActivity activity;
    private boolean isGameRunning;
    private int precededGame;

    public GameManager(MainActivity activity) {
        this.activity = activity;
        isGameRunning = false;
        precededGame = -1;
    }
    public void startRandomGame() {
        int randomGame;
        if (!isGameRunning) {
            while(true){
                Random random = new Random();
                randomGame = random.nextInt(2);
                if(randomGame!=precededGame) break;
            }
            switch (randomGame) {
                case 0:
                    precededGame = 0;
                    startSwipeGame();
                    break;
                case 1:
                    precededGame = 1;
                    startColorMatchGame();
                    break;
            }
        }
    }
    private void startSwipeGame() {
        SwipeGameView swipeGameView = new SwipeGameView(activity, null);
        activity.setContentView(swipeGameView);
        isGameRunning = true;

        swipeGameView.setOnGameStatusChangedListener(this);

        if (swipeGameView.isFinished()) {
            isGameRunning = false;
            startRandomGame();
        }
    }
    @Override
    public void onGameStatusChanged(boolean isGameRunning) {
        this.isGameRunning = isGameRunning;
        if (!isGameRunning) {
            startRandomGame();
        }
    }
    private void startColorMatchGame() {
        activity.setContentView(R.layout.colormatchgame_layout);
        ColorMatchGameModel colorMatchGameModel = new ColorMatchGameModel();
        ColorMatchGameView colorMatchGameView = new ColorMatchGameView(activity);
        ColorMatchGameController colorMatchGameController = new ColorMatchGameController(colorMatchGameModel, colorMatchGameView);
        isGameRunning = true;

        colorMatchGameView.setOnGameStatusChangedListener(this);

        if (colorMatchGameView.isFinished()) {
            isGameRunning = false;
            startRandomGame();
        }
    }
}
