package com.example.casazo_gamespace.swipegame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.casazo_gamespace.MainActivity;
import com.example.casazo_gamespace.OnGameStatusChangedListener;
import com.example.casazo_gamespace.R;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;


public class SwipeGameView extends ConstraintLayout {

    private TextView directionTextView;
    private TextView scoreTextView;
    private SwipeGameController controller;
    private ImageView vectorAssetImageView;
    private Button btnRestart;
    private boolean isGameFinished;
    private OnGameStatusChangedListener onGameStatusChangedListener;

    public SwipeGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        inflate(context, R.layout.swipegame_layout, this);
        directionTextView = findViewById(R.id.textView1);
        scoreTextView = findViewById(R.id.scoreTextView);
        vectorAssetImageView = findViewById(R.id.vectorAssetImageView);
        btnRestart = findViewById(R.id.restartbutton);
        btnRestart.setVisibility(View.GONE);
        isGameFinished = false;

        SwipeGameModel model = new SwipeGameModel();
        controller = new SwipeGameController(model, this);

        setOnTouchListener(controller.getOnTouchListener());

        controller.generateRandomDirection();
        setBackgroundColor(Color.argb(255, 252, 174, 30));
        setTextviewDirections(model.getDirections());
        setScore(model.getCurrentScore());
        controller.timeResume();
    }

    public void setTextviewDirections(List<String> directions) {
        StringBuilder directionText = new StringBuilder();
        for (String dir : directions) {
            directionText.append(dir).append(", ");
        }
        directionTextView.setText(directionText.toString().replaceAll(", $", ""));
    }

    public void setScore(int score) {
        scoreTextView.setText("Score: " + score);
    }

    public void setDisplayRandomImage(int vectorResourceId) {
        vectorAssetImageView.setImageResource(vectorResourceId);
    }

    public void displayRestart(){
        btnRestart.setVisibility(View.VISIBLE);
    }

    public void hideBtnRestart(){
        btnRestart.setVisibility(View.GONE);
    }
    public View getBtnRestart(){
        return this.btnRestart;
    }

    public boolean isFinished() {
        return isGameFinished;
    }

    public void setFinished(boolean finished) {
        isGameFinished = finished;
    }

    public void setOnGameStatusChangedListener(OnGameStatusChangedListener listener) {
        this.onGameStatusChangedListener = listener;
    }

    public void notifyGameStatusChangedListener() {
        if (onGameStatusChangedListener != null) {
            onGameStatusChangedListener.onGameStatusChanged(!isGameFinished);
        }
    }

}
