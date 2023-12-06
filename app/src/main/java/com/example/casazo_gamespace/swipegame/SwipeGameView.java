package com.example.casazo_gamespace.swipegame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.casazo_gamespace.MainActivity;
import com.example.casazo_gamespace.R;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;


public class SwipeGameView extends ConstraintLayout {

    private TextView directionTextView;
    private TextView scoreTextView;
    private ProgressBar timeBar;
    private SwipeGameController controller;

    private ImageView vectorAssetImageView;
    public SwipeGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        inflate(context, R.layout.swipegame_layout, this);
        directionTextView = findViewById(R.id.textView1);
        scoreTextView = findViewById(R.id.scoreTextView);
        vectorAssetImageView = findViewById(R.id.vectorAssetImageView);
        timeBar = findViewById(R.id.progressbarTimer);

        SwipeGameModel model = new SwipeGameModel();
        controller = new SwipeGameController(model, this);

        setOnTouchListener(controller.getOnTouchListener());

        timeBar.setBackgroundColor(Color.argb(0, 255, 0, 0));
        timeBar.setMax(1);
        timeBar.setVisibility(View.VISIBLE);
        controller.generateRandomDirection();
        setBackgroundColor(Color.argb(255, 252, 174, 30));
        setTextviewDirections(model.getDirections());
        setScore(model.getCurrentScore());
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

    public void setTimeBar(int n){
        timeBar.setProgress(n);
    }

    public void GameOverUI(int score) {
        new AlertDialog.Builder(getContext())
                .setTitle("Game Over")
                .setMessage("Your score is: " + score)
                .setPositiveButton("Restart", (dialog, which) -> {
                    SwipeGameModel model = new SwipeGameModel();
                    controller = new SwipeGameController(model, this);
                    controller.generateRandomDirection();
                    setOnTouchListener(controller.getOnTouchListener());
                    setTextviewDirections(model.getDirections());
                    setScore(model.getCurrentScore());
                })
                .setNegativeButton("Exit", (dialog, which) -> {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                })
                .show();
    }

    public void doneUI(int score) {
        new AlertDialog.Builder(getContext())
                .setTitle("COMPLETED")
                .setMessage("Your score is: " + score)
                .setPositiveButton("Restart", (dialog, which) -> {
                    SwipeGameModel model = new SwipeGameModel();
                    controller = new SwipeGameController(model, this);
                    controller.generateRandomDirection();
                    setOnTouchListener(controller.getOnTouchListener());
                    setTextviewDirections(model.getDirections());
                    setScore(model.getCurrentScore());
                })
                .setNegativeButton("Exit", (dialog, which) -> {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                })
                .show();
    }
}
