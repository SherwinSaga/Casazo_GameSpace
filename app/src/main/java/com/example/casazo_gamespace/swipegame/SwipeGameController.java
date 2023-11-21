package com.example.casazo_gamespace.swipegame;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;


import java.util.List;
import java.util.Random;


public class SwipeGameController {
    private SwipeGameModel model;
    private SwipeGameView view;
    private SwipeGameUpdater sgUpdater;
    private float x1, y1, x2, y2;
    private CountDownTimer timer;


    public SwipeGameController(SwipeGameModel model, SwipeGameView view) {
        this.model = model;
        this.view = view;
        this.sgUpdater = new SwipeGameUpdater(view);

        this.timer = new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {
                // This method will be called every second
            }

            public void onFinish() {
                view.GameOverUI(model.getCurrentScore());
                resetGame();
            }
        };

    }

    public void onSwipe(String direction) {
        List<String> directions = model.getDirections();

        if (!directions.isEmpty() && direction.equals(directions.get(0))) {
            model.incrementCurrentScore();
            directions.remove(0);
            if (directions.isEmpty()) {
                sgUpdater.updateGame(model);
                timer.cancel();
                timer.start();
            }
            view.setScore(model.getCurrentScore());
        } else {
            if(model.getCurrentScore() > model.getHighScore()){    //track highscore
                model.setHighScore(model.getCurrentScore());
            }
            view.GameOverUI(model.getCurrentScore());
            resetGame();
        }
    }

    public View.OnTouchListener getOnTouchListener(){
        return new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        x2 = motionEvent.getX();
                        y2 = motionEvent.getY();

                        //for minimum span swipe
                        float deltaX = x2 - x1;
                        float deltaY = y2 - y1;

                        // Horizontal swipe
                        if (Math.abs(deltaX) > 150) {
                            if (x2 > x1) {
                                onSwipe("RIGHT");
                            } else {
                                onSwipe("LEFT");
                            }
                        }
                        // Vertical swipe
                        else if (Math.abs(deltaY) > 150) {
                            if (y2 > y1) {
                                onSwipe("DOWN");
                            } else {
                                onSwipe("UP");
                            }
                        }
                        x1 = x2;
                        y1 = y2;
                        break;

                    case MotionEvent.ACTION_DOWN:

                        x1 = motionEvent.getX();
                        y1 = motionEvent.getY();
                        break;
                }

                return true;
            }
        };
    }

    private void resetGame() {
        model.clear();
        model.setCurrentScore(0);
        sgUpdater.resetUpdater();
        timer.cancel();
    }





















    public void generateRandomDirection() {
        sgUpdater.generateRandomDirection(model);
    }


}
