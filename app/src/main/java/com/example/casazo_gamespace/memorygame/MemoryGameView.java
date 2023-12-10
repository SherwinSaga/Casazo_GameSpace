package com.example.casazo_gamespace.memorygame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.casazo_gamespace.MainActivity;
import com.example.casazo_gamespace.R;

public class MemoryGameView extends ConstraintLayout {

    private MemoryGameController controller;
    private MemoryGameModel model;
    private TextView ScoreTextView, MessageTextView;
    private TextView buttonGreen, buttonRed, buttonYellow, buttonBlue, buttonMemory;
    private Handler handler = new Handler();

    public MemoryGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        inflate(context, R.layout.memorygame_layout, this);

        buttonGreen = findViewById(R.id.buttonGreen);
        buttonRed = findViewById(R.id.buttonRed);
        buttonYellow = findViewById(R.id.buttonYellow);
        buttonBlue = findViewById(R.id.buttonBlue);
        buttonMemory = findViewById(R.id.buttonMemory);
        ScoreTextView = findViewById(R.id.scoreTextView);
        MessageTextView = findViewById(R.id.messageTextView);

        model = new MemoryGameModel();
        controller = new MemoryGameController(model, this);

        MessageTextView.setText("Click MEMORY to start.");
        handler.removeCallbacksAndMessages(null);
    }

    public void setMessageTextView(String string){ MessageTextView.setText(string); }
//    public void setScoreTextView(String string){ ScoreTextView.setText(string); }
    public void startRound()
    {
//        if (model.getRound()==1){
//            ScoreTextView.setText("Score: " + model.getScore());
//        }
        int delay;
        if (model.getRound()==1){
            delay=100;
        }else{delay=50;}
        model.addRandomSequence();
        for(int i=0;i<model.getCurrentSequence().size();i++){
            if(model.getCurrentSequence().get(i)=="yellow") {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GradientDrawable gradientDrawable = (GradientDrawable) buttonYellow.getBackground();
                        gradientDrawable.setColors(new int[] {Color.WHITE,Color.YELLOW});
                    }
                }, delay);
                delay += 1000;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GradientDrawable gradientDrawable = (GradientDrawable) buttonYellow.getBackground();
                        gradientDrawable.setColors(new int[] {Color.YELLOW,Color.YELLOW});
                    }
                }, delay);
                delay+=1000;
            }

            if(model.getCurrentSequence().get(i)=="blue"){
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GradientDrawable gradientDrawable = (GradientDrawable) buttonBlue.getBackground();
                        gradientDrawable.setColors(new int[] {Color.parseColor("#26ABFF"),Color.BLUE});
                    }
                }, delay);
                delay+=1000;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GradientDrawable gradientDrawable = (GradientDrawable) buttonBlue.getBackground();
                        gradientDrawable.setColors(new int[] {Color.BLUE,Color.BLUE});
                    }
                },delay);
                delay+=1000;
            }

            if(model.getCurrentSequence().get(i)=="green"){
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GradientDrawable gradientDrawable = (GradientDrawable) buttonGreen.getBackground();
                        gradientDrawable.setColors(new int[] {Color.WHITE,Color.GREEN});
                    }
                }, delay);
                delay+=1000;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GradientDrawable gradientDrawable = (GradientDrawable) buttonGreen.getBackground();
                        gradientDrawable.setColors(new int[] {Color.GREEN,Color.GREEN});
                    }
                }, delay);
                delay+=1000;
            }

            if(model.getCurrentSequence().get(i)=="red") {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GradientDrawable gradientDrawable = (GradientDrawable) buttonRed.getBackground();
                        gradientDrawable.setColors(new int[] {Color.parseColor("#ffcccb"),Color.RED});
                    }
                }, delay);
                delay+=1000;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GradientDrawable gradientDrawable = (GradientDrawable) buttonRed.getBackground();
                        gradientDrawable.setColors(new int[] {Color.RED,Color.RED});
                    }
                }, delay);
                delay+=600;
            }

            if (i==model.getCurrentSequence().size()-1){
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        controller.testPlayer();
                    }
                }, delay);
            }
        }
    }

    public void resetColors(){
        TextView bYellowReset1 = findViewById(R.id.buttonYellow);
        GradientDrawable gradientDrawableReset1 = (GradientDrawable) bYellowReset1.getBackground();
        gradientDrawableReset1.setColors(new int[] {Color.YELLOW,Color.YELLOW});

        TextView bGreenReset1 = findViewById(R.id.buttonGreen);
        GradientDrawable gradientDrawableReset2 = (GradientDrawable) bGreenReset1.getBackground();
        gradientDrawableReset2.setColors(new int[] {Color.GREEN,Color.GREEN});

        TextView bBlueReset1 = findViewById(R.id.buttonBlue);
        GradientDrawable gradientDrawableReset3 = (GradientDrawable) bBlueReset1.getBackground();
        gradientDrawableReset3.setColors(new int[] {Color.BLUE,Color.BLUE});

        TextView bRedReset1 = findViewById(R.id.buttonRed);
        GradientDrawable gradientDrawableReset4 = (GradientDrawable) bRedReset1.getBackground();
        gradientDrawableReset4.setColors(new int[] {Color.RED,Color.RED});
    }

    public void showGameOverUI(int score) {
        new AlertDialog.Builder(getContext())
                .setTitle("Game Over")
                .setMessage("Your score is: " + score)
                .setPositiveButton("Restart", (dialog, which) -> {
                    TextView t2 = findViewById(R.id.messageTextView);
                    t2.setText("Click MEMORY to start.");
                    model = new MemoryGameModel();
                    controller = new MemoryGameController(new MemoryGameModel(), this);
//                    setScoreTextView("Score: " + score);
                })
                .setNegativeButton("Exit", (dialog, which) -> {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                })
                .show();
    }
}
