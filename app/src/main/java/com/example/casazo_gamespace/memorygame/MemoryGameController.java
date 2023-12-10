package com.example.casazo_gamespace.memorygame;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.casazo_gamespace.MainActivity;
import com.example.casazo_gamespace.R;
import com.example.casazo_gamespace.Sound;

import java.util.ArrayList;
import java.util.Random;

public class MemoryGameController extends AppCompatActivity {
    private MemoryGameModel mgmodel;
    private MemoryGameView mgview;
    private MemoryGameUpdater mgupdater;
    private Handler handler = new Handler();
    private Random random;
    int RandomLimit;
    public MemoryGameController(MemoryGameModel model, MemoryGameView view) {
        this.mgmodel = model;
        this.mgview = view;
        this.mgupdater = new MemoryGameUpdater(this.mgview);
        random = new Random();
        RandomLimit = random.nextInt(10) + 5;
        initListeners();
        updateView();
    }

    public void initListeners(){
        mgview.getButtonMemory().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mgmodel.clear();
                updateView();
                mgview.setButtonMemoryClicked(true);
                startRound();
            }
        });
        mgview.getButtonBlue().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mgview.isMemoryClicked() == true){
                    mgmodel.addUserSequence("blue");
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handleGradientDrawable((TextView) mgview.getButtonBlue(), Color.parseColor("#26ABFF"),Color.BLUE);
                        Sound.playSound(mgview.getActivity(), "blue");
                    }
                }, 0);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handleGradientDrawable((TextView) mgview.getButtonBlue(), Color.BLUE,Color.BLUE);
                    }
                },300);
            }
        });
        mgview.getButtonGreen().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mgview.isMemoryClicked() == true){
                    mgmodel.addUserSequence("green");
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handleGradientDrawable((TextView) mgview.getButtonGreen(), Color.WHITE, Color.GREEN);
                        Sound.playSound(mgview.getActivity(), "green");
                    }
                }, 0);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handleGradientDrawable((TextView) mgview.getButtonGreen(), Color.GREEN, Color.GREEN);
                    }
                }, 300);
            }
        });
        mgview.getButtonRed().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mgview.isMemoryClicked() == true){
                    mgmodel.addUserSequence("red");
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handleGradientDrawable((TextView) mgview.getButtonRed(), Color.parseColor("#ffcccb"),Color.RED);
                        Sound.playSound(mgview.getActivity(), "red");
                    }
                },0);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handleGradientDrawable((TextView) mgview.getButtonRed(), Color.RED,Color.RED);
                    }
                }, 300);
            }
        });
        mgview.getButtonYellow().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mgview.isMemoryClicked() == true){
                    mgmodel.addUserSequence("yellow");
                }
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handleGradientDrawable((TextView) mgview.getButtonYellow(), Color.WHITE, Color.YELLOW);
                        Sound.playSound(mgview.getActivity(), "yellow");
                    }
                }, 0);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handleGradientDrawable((TextView) mgview.getButtonYellow(), Color.YELLOW, Color.YELLOW);
                    }
                }, 300);
            }
        });
        mgview.getButtonNewGame().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameDone();
            }
        });
    }

    public void updateView(){
        //view.displayProgressBar();
        mgview.displayNewGameButton(false);
        resetColors();
    }

    public void gameDone(){
        mgview.setFinished(true);
        mgview.notifyGameStatusChangedListener();
    }

    public void handleGradientDrawable(TextView button, int startColor, int endColor) {
        GradientDrawable gradientDrawable = (GradientDrawable) button.getBackground();
        gradientDrawable.setColors(new int[] {startColor, endColor});
    }

    public void resetColors(){
        handleGradientDrawable((TextView) mgview.getButtonYellow(), Color.YELLOW,Color.YELLOW);
        handleGradientDrawable((TextView) mgview.getButtonGreen(), Color.GREEN,Color.GREEN);
        handleGradientDrawable((TextView) mgview.getButtonBlue(), Color.BLUE,Color.BLUE);
        handleGradientDrawable((TextView) mgview.getButtonRed(), Color.RED,Color.RED);
    }

    public void startRound()
    {
        int delay;
        if (mgmodel.getRound()==1){
            delay=100;
        }else{delay=50;}
        mgmodel.addRandomSequence();

        for(int i=0;i<mgmodel.getCurrentSequence().size();i++){
            if(mgmodel.getRound() == RandomLimit){
                gameOver();
                break;
            }
            if(mgmodel.getCurrentSequence().get(i).equals("yellow")) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handleGradientDrawable((TextView) mgview.getButtonYellow(), Color.WHITE, Color.YELLOW);
                        Sound.playSound(mgview.getActivity(), "yellow");
                    }
                }, delay);
                delay += 200;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handleGradientDrawable((TextView) mgview.getButtonYellow(), Color.YELLOW, Color.YELLOW);
                    }
                }, delay);
                delay+=200;
            }

            if(mgmodel.getCurrentSequence().get(i).equals("blue")){
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handleGradientDrawable((TextView) mgview.getButtonBlue(), Color.parseColor("#26ABFF"),Color.BLUE);
                        Sound.playSound(mgview.getActivity(), "blue");
                    }
                }, delay);
                delay+=200;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handleGradientDrawable((TextView) mgview.getButtonBlue(), Color.BLUE, Color.BLUE);
                    }
                },delay);
                delay+=200;
            }

            if(mgmodel.getCurrentSequence().get(i).equals("green")){
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handleGradientDrawable((TextView) mgview.getButtonGreen(), Color.WHITE, Color.GREEN);
                        Sound.playSound(mgview.getActivity(), "green");
                    }
                }, delay);
                delay+=200;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handleGradientDrawable((TextView) mgview.getButtonGreen(), Color.GREEN, Color.GREEN);
                    }
                }, delay);
                delay+=200;
            }

            if(mgmodel.getCurrentSequence().get(i).equals("red")) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handleGradientDrawable((TextView) mgview.getButtonRed(), Color.parseColor("#ffcccb"),Color.RED);
                        Sound.playSound(mgview.getActivity(), "red");
                    }
                }, delay);
                delay+=200;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handleGradientDrawable((TextView) mgview.getButtonRed(), Color.RED, Color.RED);
                    }
                }, delay);
                delay+=200;
            }

            if (i==mgmodel.getCurrentSequence().size()-1){
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        testPlayer();
                    }
                }, delay);
            }
        }
    }

    public void testPlayer(){
        mgview.setMessageTextView("Click the sequence shown!");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String s = mgmodel.getUserSequence().toString();
                Boolean isCorrect = false;
                if(mgmodel.getUserSequence().size()==mgmodel.getCurrentSequence().size()){
                    for(int i=0;i<mgmodel.getUserSequence().size();i++){
                        if(mgmodel.getUserSequence().get(i)==mgmodel.getCurrentSequence().get(i)){
                            isCorrect=true;
                        }
                        else{
                            isCorrect=false;
                            break;
                        }
                    }
                }
                evaluateRound(isCorrect);
            }
        }, 400*mgmodel.getRound());
    }

    public void evaluateRound(Boolean userEval){
        if(userEval){
            mgview.setMessageTextView("Displaying sequence.");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startRound();
                }
            }, 300);
        }
        else{
            gameOver();
        }
    }

    public void gameOver(){
        Sound.playSound(mgview.getActivity(), "lose");
        mgview.displayNewGameButton(true);
        mgview.setButtonMemoryClicked(false);
        mgview.setMessageTextView("Click MEMORY to start.");
        Log.d("Color", mgmodel.getColors().toString());
        Log.d("Current Sequence", mgmodel.getCurrentSequence().toString());
        Log.d("User Sequence", mgmodel.getUserSequence().toString());
        Log.d("Round", "" + mgmodel.getRound());
        Log.d("Random Limit", "" + RandomLimit);

    }
}
