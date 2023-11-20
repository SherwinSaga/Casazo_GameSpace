package com.example.casazo_gamespace.swipegame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.casazo_gamespace.MainActivity;
import com.example.casazo_gamespace.R;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;


public class SwipeGameView extends ConstraintLayout {

    private TextView directionTextView;
    private TextView scoreTextView;
    private SwipeGameController controller;
    private float x1, y1, x2, y2;
    private ImageView vectorAssetImageView;
    public SwipeGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
        controller.updateBackgroundColor();
    }

    private void initialize(Context context) {
        inflate(context, R.layout.swipegame_layout, this);
        directionTextView = findViewById(R.id.textView1);
        scoreTextView = findViewById(R.id.scoreTextView);
        vectorAssetImageView = findViewById(R.id.vectorAssetImageView);


        SwipeGameModel model = new SwipeGameModel();
        controller = new SwipeGameController(model, this);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        x2 = motionEvent.getX();
                        y2 = motionEvent.getY();
                        float deltaX = x2 - x1;
                        float deltaY = y2 - y1;

                        // Horizontal swipe
                        if (Math.abs(deltaX) > 150) {
                            if (x2 > x1) {
                                controller.onSwipe("RIGHT");
                            } else {
                                controller.onSwipe("LEFT");
                            }
                        }
                        // Vertical swipe
                        else if (Math.abs(deltaY) > 150) {
                            if (y2 > y1) {
                                controller.onSwipe("DOWN");
                            } else {
                                controller.onSwipe("UP");
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
        });

        controller.generateRandomDirection();
        textviewDirections(model.getDirections());
        updateScore(model.getCurrentScore());
    }

    public void textviewDirections(List<String> directions) {
        StringBuilder directionText = new StringBuilder();
        for (String dir : directions) {
            directionText.append(dir).append(", ");
        }
        directionTextView.setText(directionText.toString().replaceAll(", $", ""));
    }

    public void displayRandomImage(int vectorResourceId) {
        vectorAssetImageView.setImageResource(vectorResourceId);
    }

    public void updateScore(int score) {
        scoreTextView.setText("Score: " + score);
    }

    public void showGameOver(int score) {
        new AlertDialog.Builder(getContext())
                .setTitle("Game Over")
                .setMessage("Your score is: " + score)
                .setPositiveButton("Restart", (dialog, which) -> {
                    SwipeGameModel model = new SwipeGameModel();
                    controller = new SwipeGameController(model, this);
                    controller.generateRandomDirection();
                    textviewDirections(model.getDirections());
                    updateScore(model.getCurrentScore());
                })
                .setNegativeButton("Exit", (dialog, which) -> {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                })
                .show();
    }



}
