package com.example.casazo_gamespace.colormatchgame;

import android.util.Log;
import android.view.View;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;

import com.example.casazo_gamespace.R;
import java.util.Random;

public class ColorMatchGameController implements View.OnClickListener {
    private ColorMatchGameModel colorMatchGameModel;
    private ColorMatchGameView colorMatchGameView;
    private ColorMatchGameUpdater colorMatchGameUpdater;
    Handler handler;
    Runnable runnable;
    int Randomlimit;

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
        Random r = new Random();
        Randomlimit = r.nextInt(7)+10;
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
                Button btnRetry = (Button) colorMatchGameView.getBtnRetry();
                btnRetry.setVisibility(View.GONE);
                colorMatchGameModel.setCurrentTime(colorMatchGameModel.getCurrentTime() - 100);
                colorMatchGameView.displayProgressBar(colorMatchGameModel.getStartTime(), colorMatchGameModel.getCurrentTime());
                //colorMatchGameUpdater.updateBackgroundColor();

                if (colorMatchGameModel.getCurrentTime() > 0) {
                    handler.postDelayed(runnable, 100);
                } else {
                    if (buttonState == colorMatchGameModel.getArrowState()) {
                        colorMatchGameModel.setCurrentPoints(colorMatchGameModel.getCurrentPoints() + 1);
                        colorMatchGameView.displayPoints(colorMatchGameModel.getCurrentPoints());

                        if (colorMatchGameModel.getCurrentPoints() % 5 == 0 && colorMatchGameModel.getCurrentPoints() > 0) {
                            colorMatchGameModel.setStartTime(colorMatchGameModel.getStartTime() - 100);
                            if (colorMatchGameModel.getStartTime() < 1000) {
                                colorMatchGameModel.setStartTime(1000);
                            }
                            colorMatchGameView.displayProgressBar(colorMatchGameModel.getStartTime(), colorMatchGameModel.getStartTime());
                        }

                        if (colorMatchGameModel.getCurrentPoints() == Randomlimit) {
                            colorMatchGameView.getIvButton().setEnabled(false);
                            colorMatchGameView.displayRetryButton(true);
                            return;
                        }
                        colorMatchGameModel.setArrowState(new Random().nextInt(4) + 1);
                        colorMatchGameView.setArrowImage(colorMatchGameModel.getArrowState());

                        colorMatchGameModel.setCurrentTime(colorMatchGameModel.getStartTime());
                        colorMatchGameView.displayProgressBar(colorMatchGameModel.getStartTime(), colorMatchGameModel.getStartTime());

                        handler.postDelayed(runnable, 100);
                    } else {
                        colorMatchGameView.getIvButton().setEnabled(false);
                        btnRetry.setVisibility(View.VISIBLE);
                    }
                }
            }
        };
        handler.postDelayed(runnable, 100);
    }

    private void resetGame() {
        colorMatchGameModel.setCurrentTime(colorMatchGameModel.getOriginalStartTime());
        colorMatchGameModel.setCurrentPoints(0);
        colorMatchGameModel.setButtonState(new Random().nextInt(4) + 1);
        colorMatchGameModel.setArrowState(new Random().nextInt(4) + 1);

        colorMatchGameView.displayProgressBar(colorMatchGameModel.getOriginalStartTime(), colorMatchGameModel.getOriginalStartTime());
        colorMatchGameView.displayPoints(colorMatchGameModel.getCurrentPoints());
        colorMatchGameView.setArrowImage(colorMatchGameModel.getArrowState());
        colorMatchGameView.setRotation(colorMatchGameModel.getButtonState(), getButtonDrawable(colorMatchGameModel.getButtonState()));

        colorMatchGameView.getIvButton().setEnabled(true);
        colorMatchGameView.displayRetryButton(true);

        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 100);
    }
}
