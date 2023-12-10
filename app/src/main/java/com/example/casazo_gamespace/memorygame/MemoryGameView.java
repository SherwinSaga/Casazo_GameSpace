package com.example.casazo_gamespace.memorygame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.casazo_gamespace.MainActivity;
import com.example.casazo_gamespace.OnGameStatusChangedListener;
import com.example.casazo_gamespace.R;

public class MemoryGameView{

    private TextView MessageTextView;
    private TextView buttonGreen;
    private TextView buttonRed;
    private TextView buttonYellow;
    private TextView buttonBlue;
    private TextView buttonMemory;
    private Button buttonNewGame;
    private ProgressBar progressBar;
    private Activity activity;
    private OnGameStatusChangedListener onGameStatusChangedListener;
    private boolean isGameFinished;
    private boolean isButtonMemoryClicked;

    public MemoryGameView(Activity activity) {
        this.MessageTextView = activity.findViewById(R.id.messageTextView);
        this.buttonGreen = activity.findViewById(R.id.buttonGreen);
        this.buttonRed = activity.findViewById(R.id.buttonRed);
        this.buttonYellow = activity.findViewById(R.id.buttonYellow);
        this.buttonBlue = activity.findViewById(R.id.buttonBlue);
        this.buttonMemory = activity.findViewById(R.id.buttonMemory);
        this.buttonNewGame = activity.findViewById(R.id.buttonNewGame);
        this.progressBar = activity.findViewById(R.id.progressBar);
        this.activity = activity;
        isGameFinished = false;
        isButtonMemoryClicked = false;
    }

    public void setMessageTextView(String string){ MessageTextView.setText(string.toString());}
    public View getButtonGreen(){ return buttonGreen; }
    public View getButtonRed(){ return buttonRed; }
    public View getButtonYellow(){ return buttonYellow; }
    public View getButtonBlue(){ return buttonBlue; }
    public View getButtonMemory(){ return buttonMemory; }
    public View getButtonNewGame(){ return buttonNewGame;}
    public void displayProgressBar(int max, int progress){
        progressBar.setMax(max);
        progressBar.setProgress(progress);
    }
    public void displayNewGameButton(boolean visible){
        buttonNewGame.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
    public Activity getActivity() { return activity;}
    public void setOnGameStatusChangedListener(OnGameStatusChangedListener listener) {
        this.onGameStatusChangedListener = listener;
    }
    public void notifyGameStatusChangedListener() {
        if(onGameStatusChangedListener != null) {
            onGameStatusChangedListener.onGameStatusChanged(!isGameFinished);
        }
    }
    public boolean isFinished(){ return isGameFinished; }
    public void setFinished(boolean finished){ isGameFinished = finished;}
    public boolean isMemoryClicked() { return isButtonMemoryClicked;}
    public void setButtonMemoryClicked(boolean clicked){ isButtonMemoryClicked = clicked;}
}
