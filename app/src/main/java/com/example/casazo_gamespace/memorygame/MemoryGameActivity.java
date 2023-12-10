package com.example.casazo_gamespace.memorygame;


import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.casazo_gamespace.R;

import java.util.ArrayList;

public class MemoryGameActivity extends AppCompatActivity {
    public MemoryGame newGame;
    public Handler handler = new Handler();
    public ArrayList<String> userColorsClicked;

    Button play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_game);
        Button buttonSimon = (Button) findViewById(R.id.buttonMemory);
        buttonSimon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView t2 = findViewById(R.id.textView2);
                t2.setText("Click MEMORY to start.");
                handler.removeCallbacksAndMessages(null);
                resetColors();
                userColorsClicked = new ArrayList<String>();
                newGame= new MemoryGame();
                playSound("start");
                startRound();
            }
        });
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

    public void playSound(String sound){
        int audioRes = 0;
        switch (sound) {
            case "red":
                audioRes = R.raw.fa;
                break;
            case "green":
                audioRes = R.raw.mi;
                break;
            case "blue":
                audioRes = R.raw.si;
                break;
            case "yellow":
                audioRes = R.raw.sol;
                break;
            case "lose":
                audioRes = R.raw.lose;
                break;
            case "start":
                audioRes = R.raw.game_start;
                break;
        }
        MediaPlayer p = MediaPlayer.create(this, audioRes);
        p.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        p.start();
    }

    public void startRound()
    {
        TextView t = findViewById(R.id.textView);
        if (newGame.round==1){
            t.setText("Score: " + newGame.score);
        }
        TextView bYellow = findViewById(R.id.buttonYellow);
        TextView bRed = findViewById(R.id.buttonRed);
        TextView bBlue = findViewById(R.id.buttonBlue);
        TextView bGreen = findViewById(R.id.buttonGreen);
        int delay;

        if (newGame.round==1){
            delay=200;
        }else{delay=50;}
        newGame.getPattern();
        for(int i=0;i<newGame.currentPattern.size();i++){
            if(newGame.currentPattern.get(i)=="yellow") {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GradientDrawable gradientDrawable = (GradientDrawable) bYellow.getBackground();
                        gradientDrawable.setColors(new int[] {Color.WHITE,Color.YELLOW});
                        playSound("yellow");
                    }
                }, delay);
                delay += 200;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GradientDrawable gradientDrawable = (GradientDrawable) bYellow.getBackground();
                        gradientDrawable.setColors(new int[] {Color.YELLOW,Color.YELLOW});
                    }
                }, delay);
                delay+=200;
            }

            if(newGame.currentPattern.get(i)=="blue"){
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GradientDrawable gradientDrawable = (GradientDrawable) bBlue.getBackground();
                        gradientDrawable.setColors(new int[] {Color.parseColor("#26ABFF"),Color.BLUE});
                        playSound("blue");
                    }
                }, delay);
                delay+=200;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GradientDrawable gradientDrawable = (GradientDrawable) bBlue.getBackground();
                        gradientDrawable.setColors(new int[] {Color.BLUE,Color.BLUE});
                    }
                },delay);
                delay+=200;
            }

            if(newGame.currentPattern.get(i)=="green"){
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GradientDrawable gradientDrawable = (GradientDrawable) bGreen.getBackground();
                        gradientDrawable.setColors(new int[] {Color.WHITE,Color.GREEN});
                        playSound("green");
                    }
                }, delay);
                delay+=200;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GradientDrawable gradientDrawable = (GradientDrawable) bGreen.getBackground();
                        gradientDrawable.setColors(new int[] {Color.GREEN,Color.GREEN});
                    }
                }, delay);
                delay+=200;
            }

            if(newGame.currentPattern.get(i)=="red") {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GradientDrawable gradientDrawable = (GradientDrawable) bRed.getBackground();
                        gradientDrawable.setColors(new int[] {Color.parseColor("#ffcccb"),Color.RED});
                        playSound("red");
                    }
                }, delay);
                delay+=200;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GradientDrawable gradientDrawable = (GradientDrawable) bRed.getBackground();
                        gradientDrawable.setColors(new int[] {Color.RED,Color.RED});
                    }
                }, delay);
                delay+=200;
            }

            if (i==newGame.currentPattern.size()-1){
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
        TextView t = findViewById(R.id.textView2);
        userColorsClicked = new ArrayList<String>();
        t.setText("Click the correct button sequence!");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String s = userColorsClicked.toString();
                Boolean isCorrect = false;
                if(userColorsClicked.size()==newGame.currentPattern.size()){
                    for(int i=0;i<userColorsClicked.size();i++){
                        if(userColorsClicked.get(i)==newGame.currentPattern.get(i)){
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
        }, 400*newGame.round);
    }

    public void evaluateRound(Boolean userEval){
        if(userEval){
            newGame.score += 1;
            TextView t = findViewById(R.id.textView);
            t.setText("Score: "+ newGame.score);
            TextView t2 = findViewById(R.id.textView2);
            t2.setText("Displaying sequence.");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startRound();
                }
            }, 300);
        }
        else{
            playSound("lose");
            showGameOverUI(newGame.score);
        }
    }

    public void setClickedGreen(View v){
        userColorsClicked.add("green");
        TextView bGreen = findViewById(R.id.buttonGreen);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GradientDrawable gradientDrawable = (GradientDrawable) bGreen.getBackground();
                gradientDrawable.setColors(new int[] {Color.WHITE,Color.GREEN});
                playSound("green");
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
        userColorsClicked.add("yellow");
        TextView bYellow = findViewById(R.id.buttonYellow);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GradientDrawable gradientDrawable = (GradientDrawable) bYellow.getBackground();
                gradientDrawable.setColors(new int[] {Color.WHITE,Color.YELLOW});
                playSound("yellow");
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
        TextView bRed = findViewById(R.id.buttonRed);
        userColorsClicked.add("red");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GradientDrawable gradientDrawable = (GradientDrawable) bRed.getBackground();
                gradientDrawable.setColors(new int[] {Color.parseColor("#ffcccb"),Color.RED});
                playSound("red");
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
        TextView bBlue = findViewById(R.id.buttonBlue);
        userColorsClicked.add("blue");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GradientDrawable gradientDrawable = (GradientDrawable) bBlue.getBackground();
                gradientDrawable.setColors(new int[] {Color.parseColor("#26ABFF"),Color.BLUE});
                playSound("blue");
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

    public void showGameOverUI(int score) {
        new AlertDialog.Builder(MemoryGameActivity.this)
                .setTitle("Game Over")
                .setMessage("Your score is: " + score)
                .setNegativeButton("Exit", (dialog, which) -> {
                    finish();
                })
                .show();
    }
}