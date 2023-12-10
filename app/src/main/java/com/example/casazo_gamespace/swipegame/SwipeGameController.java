package com.example.casazo_gamespace.swipegame;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.casazo_gamespace.MainActivity;
import com.example.casazo_gamespace.Sound;

import java.util.List;
import java.util.Random;


public class SwipeGameController {
    private SwipeGameModel model;
    private SwipeGameView view;
    public SwipeGameUpdater sgUpdater;
    private float x1, y1, x2, y2;
    private CountDownTimer timer;
    private boolean isGameFinished;
    private int goal;

    public SwipeGameController(SwipeGameModel model, SwipeGameView view) {
        this.model = model;
        this.view = view;
        this.sgUpdater = new SwipeGameUpdater(view);
        this.isGameFinished = false;

        this.timer = new CountDownTimer(800, 20) {
            public void onTick(long millisUntilFinished) {
                float progress = (float) millisUntilFinished / 1000; // Convert milliseconds to seconds
                view.setProgressBar(progress);
            }
            public void onFinish() {
                //When timer runs out, display restart
                resetGame();
                updateButtonRestart(true);
                Sound.playSound(view.getContext(),"fail");
            }
        };
        initializeListeners();

        Random random = new Random();
        this.goal = random.nextInt(6) + 10;
        //this.goal = 5;
        view.hideBtnRestart();
    }

    private void initializeListeners(){
        view.getBtnRestart().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //resetGame();
                gamedone();
            }
        });
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
            Sound.playSound(view.getContext(),"swipe");
            view.setScore(model.getCurrentScore());

            //WIN CONDITION
            if(model.getCurrentScore() == goal){
                //to do
                //transition to next game
                resetGame();
                view.setFinished(true);
                isGameFinished = true;
                view.notifyGameStatusChangedListener();
                //Toast.makeText(view.getContext(), "asdasdasd", Toast.LENGTH_SHORT).show();
                return;
            }


            //LOSE CONDITION
        } else {
            Sound.playSound(view.getContext(),"fail");
            if(model.getCurrentScore() > model.getHighScore()){
                model.setHighScore(model.getCurrentScore());
            }
            resetGame();
            updateButtonRestart(true);
        }
    }

    public View.OnTouchListener getOnTouchListener(){
        return new View.OnTouchListener(){

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(isGameFinished){
                    return true;
                }
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
        sgUpdater.resetUpdaterVariables();
        timer.cancel();
        generateRandomDirection();
        view.setTextviewDirections(model.getDirections());
        view.setScore(model.getCurrentScore());
        view.hideBtnRestart();
        isGameFinished = false;
    }

    public void updateButtonRestart(boolean n){
        this.isGameFinished = n;
        if (n) {
            view.displayRestart();
        } else {
            view.hideBtnRestart();
        }
    }

    public void timeResume(){
        timer.start();
    }

    public void generateRandomDirection() {
        sgUpdater.generateRandomDirection(model);
    }

    public void gamedone(){
        view.setFinished(true);
        view.notifyGameStatusChangedListener();
    }

}
