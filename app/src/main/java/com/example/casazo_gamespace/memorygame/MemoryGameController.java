package com.example.casazo_gamespace.memorygame;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.casazo_gamespace.R;

public class MemoryGameController extends AppCompatActivity {
    private MemoryGameModel model;
    private MemoryGameView view;
    private MemoryGameUpdater updater;
    private Handler handler = new Handler();

    public MemoryGameController(MemoryGameModel model, MemoryGameView view) {
        this.model = model;
        this.view = view;
        this.updater = new MemoryGameUpdater(view);
    }

    public void testPlayer(){
        view.setMessageTextView("Click the correct button sequence!");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String s = model.getUserSequence().toString();
                Boolean isCorrect = false;
                if(model.getUserSequence().size()==model.getCurrentSequence().size()){
                    for(int i=0;i<model.getUserSequence().size();i++){
                        if(model.getUserSequence().get(i)==model.getCurrentSequence().get(i)){
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
        }, 1500*model.getRound());
    }

    public void evaluateRound(Boolean userEval){

        if(userEval){
//            model.incrementScore();
//            int score = model.getScore();
//            view.setScoreTextView("Score: " + score);
            view.setMessageTextView("Displaying sequence.");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.startRound();
                }
            }, 1000);
        }
        else{
            view.showGameOverUI(model.getScore());
        }
    }

    public void setClickedGreen(View v){
        MemoryGameModel memoryGameModel = new MemoryGameModel();
        memoryGameModel.addUserSequence("green");
        Handler handler = new Handler();
        TextView bGreen = findViewById(R.id.buttonGreen);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GradientDrawable gradientDrawable = (GradientDrawable) bGreen.getBackground();
                gradientDrawable.setColors(new int[] {Color.WHITE,Color.GREEN});
            }
        }, 0);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GradientDrawable gradientDrawable = (GradientDrawable) bGreen.getBackground();
                gradientDrawable.setColors(new int[] {Color.GREEN,Color.GREEN});
            }
        }, 400);
    }

    public void setClickedYellow(View v){
        MemoryGameModel memoryGameModel = new MemoryGameModel();
        memoryGameModel.addUserSequence("yellow");
        Handler handler = new Handler();
        TextView bYellow = findViewById(R.id.buttonYellow);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GradientDrawable gradientDrawable = (GradientDrawable) bYellow.getBackground();
                gradientDrawable.setColors(new int[] {Color.WHITE,Color.YELLOW});
            }
        }, 0);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GradientDrawable gradientDrawable = (GradientDrawable) bYellow.getBackground();
                gradientDrawable.setColors(new int[] {Color.YELLOW,Color.YELLOW});
            }
        }, 400);
    }

    public void setClickedRed(View v){
        MemoryGameModel memoryGameModel = new MemoryGameModel();
        memoryGameModel.addUserSequence("red");
        Handler handler = new Handler();
        TextView bRed = findViewById(R.id.buttonRed);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GradientDrawable gradientDrawable = (GradientDrawable) bRed.getBackground();
                gradientDrawable.setColors(new int[] {Color.parseColor("#ffcccb"),Color.RED});
            }
        },0);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GradientDrawable gradientDrawable = (GradientDrawable) bRed.getBackground();
                gradientDrawable.setColors(new int[] {Color.RED,Color.RED});
            }
        }, 400);
    }

    public void setClickedBlue(View v){
        MemoryGameModel memoryGameModel = new MemoryGameModel();
        memoryGameModel.addUserSequence("yellow");
        Handler handler = new Handler();
        TextView bBlue = findViewById(R.id.buttonBlue);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GradientDrawable gradientDrawable = (GradientDrawable) bBlue.getBackground();
                gradientDrawable.setColors(new int[] {Color.parseColor("#26ABFF"),Color.BLUE});
            }
        }, 0);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GradientDrawable gradientDrawable = (GradientDrawable) bBlue.getBackground();
                gradientDrawable.setColors(new int[] {Color.BLUE,Color.BLUE});
            }
        },400);
    }

}
