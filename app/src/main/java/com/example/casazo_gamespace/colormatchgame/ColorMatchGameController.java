package com.example.casazo_gamespace.colormatchgame;

import android.os.Handler;
import android.view.View;
import android.widget.Button;


import com.example.casazo_gamespace.R;

import java.util.Random;


public class ColorMatchGameController implements View.OnClickListener {
    private ColorMatchGameModel colorMatchGameModel;
    private ColorMatchGameView colorMatchGameView;
    private ColorMatchGameUpdater colorMatchGameUpdater;
    Handler handler;
    Runnable runnable;
    Random r;

    private final static int STATIC_BLUE = 1;
    private final static int STATIC_RED = 2;
    private final static int STATIC_YELLOW = 3;
    private final static int STATIC_GREEN = 4;

    int buttonState = STATIC_BLUE;

    public ColorMatchGameController(ColorMatchGameModel colorMatchGameModel, ColorMatchGameView colorMatchGameView){
        this.colorMatchGameModel = colorMatchGameModel;
        this.colorMatchGameView = colorMatchGameView;
        this.colorMatchGameUpdater = new ColorMatchGameUpdater(colorMatchGameView);
        colorMatchGameUpdater.updateBackgroundColor();
        initListeners();
        updateView();
        startGameLoop();
    }

    public void initListeners(){
        colorMatchGameView.getIvButton().setOnClickListener(this);
        colorMatchGameView.getBtnRetry().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v){
        int newState = colorMatchGameModel.getButtonState() + 1;
        if(newState == 5){
            newState = 1;
        }
        colorMatchGameModel.setButtonState(newState);
        colorMatchGameView.setRotation(colorMatchGameModel.getButtonState(), getButtonDrawable(colorMatchGameModel.getButtonState()));
        colorMatchGameUpdater.updateBackgroundColor();
    }

    public int getButtonDrawable(int state){
        switch(state){
            case STATIC_BLUE:
                colorMatchGameView.setRotation(colorMatchGameView.getIvButton(), R.drawable.ic_button_blue);
                buttonState = STATIC_BLUE;
                break;
            case STATIC_RED:
                colorMatchGameView.setRotation(colorMatchGameView.getIvButton(), R.drawable.ic_button_red);
                buttonState = STATIC_RED;
                break;
            case STATIC_YELLOW:
                colorMatchGameView.setRotation(colorMatchGameView.getIvButton(), R.drawable.ic_button_yellow);
                buttonState = STATIC_YELLOW;
                break;
            case STATIC_GREEN:
                colorMatchGameView.setRotation(colorMatchGameView.getIvButton(), R.drawable.ic_button_green);
                buttonState = STATIC_GREEN;
                break;
        }
        return state;
    }

    private void updateView(){
        colorMatchGameView.setArrowImage(colorMatchGameModel.getArrowState());
        colorMatchGameView.displayPoints(colorMatchGameModel.getCurrentPoints());
        colorMatchGameView.displayProgressBar(colorMatchGameModel.getStartTime(), colorMatchGameModel.getCurrentTime());
        colorMatchGameView.displayRetryButton(false);
    }

    private void startGameLoop() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Button btnRetry = (Button) colorMatchGameView.getBtnRetry(); // Use colorMatchGameView to get the button
                btnRetry.setVisibility(View.GONE);
                colorMatchGameModel.setCurrentTime(colorMatchGameModel.getCurrentTime() - 100);
                colorMatchGameView.displayProgressBar(colorMatchGameModel.getStartTime(), colorMatchGameModel.getCurrentTime());
                //colorMatchGameUpdater.updateBackgroundColor();

                // Check if there is still some time left in the progress bar
                if (colorMatchGameModel.getCurrentTime() > 0) {
                    handler.postDelayed(runnable, 100);
                } else {
                    // Check if the colors of the arrow and the button are the same
                    if (buttonState == colorMatchGameModel.getArrowState()) {
                        // Increase points and show them
                        colorMatchGameModel.setCurrentPoints(colorMatchGameModel.getCurrentPoints() + 1);
                        colorMatchGameView.displayPoints(colorMatchGameModel.getCurrentPoints());

                        // Make the speed higher after every turn/if the speed is 1 second make it again 2 seconds
                        if (colorMatchGameModel.getCurrentPoints() % 5 == 0) {
                            // Increase the speed after every 5 points
                            colorMatchGameModel.setStartTime(colorMatchGameModel.getStartTime() - 2000); // or any value you desire
                            if (colorMatchGameModel.getStartTime() < 1000) {
                                colorMatchGameModel.setStartTime(2000);
                            }
                            colorMatchGameView.displayProgressBar(colorMatchGameModel.getStartTime(), colorMatchGameModel.getStartTime());
                        }

                        // Generate a new color for the arrow
                        colorMatchGameModel.setArrowState(new Random().nextInt(4) + 1);
                        colorMatchGameView.setArrowImage(colorMatchGameModel.getArrowState());

                        colorMatchGameModel.setCurrentTime(colorMatchGameModel.getStartTime());
                        colorMatchGameView.displayProgressBar(colorMatchGameModel.getStartTime(), colorMatchGameModel.getStartTime());

                        handler.postDelayed(runnable, 100);
                    } else {
                        colorMatchGameView.getIvButton().setEnabled(false);
                        //Toast.makeText(colorMatchGameView.getContext(), "GAME OVER!", Toast.LENGTH_SHORT).show();
                        btnRetry.setVisibility(View.VISIBLE);
                    }
                }
            }
        };
        handler.postDelayed(runnable, 100);
    }


    private void resetGame() {
        // Reset all game variables to their initial values
        colorMatchGameModel.setCurrentTime(colorMatchGameModel.getOriginalStartTime());
        colorMatchGameModel.setCurrentPoints(0);
        colorMatchGameModel.setButtonState(new Random().nextInt(4) + 1); // Set buttonState to a random value
        colorMatchGameModel.setArrowState(new Random().nextInt(4) + 1); // Set arrowState to a random value

        // Reset UI elements
        colorMatchGameView.displayProgressBar(colorMatchGameModel.getOriginalStartTime(), colorMatchGameModel.getOriginalStartTime());
        colorMatchGameView.displayPoints(colorMatchGameModel.getCurrentPoints());
        colorMatchGameView.setArrowImage(colorMatchGameModel.getArrowState());
        colorMatchGameView.setRotation(colorMatchGameModel.getButtonState(), getButtonDrawable(colorMatchGameModel.getButtonState()));
        colorMatchGameView.getIvButton().setEnabled(true);
        colorMatchGameView.displayRetryButton(true);

        // Restart the game loop with the new time values
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 100);
    }
}
