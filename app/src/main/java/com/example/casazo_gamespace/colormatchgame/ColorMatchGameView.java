package com.example.casazo_gamespace.colormatchgame;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.casazo_gamespace.OnGameStatusChangedListener;
import com.example.casazo_gamespace.R;


public class ColorMatchGameView {
    private ImageView iv_arrow;
    private ImageView iv_button;
    private TextView tv_points;
    private ProgressBar progressBar;
    private Button btnRetry;
    private Button btnExit;
    private Activity activity;

    private final static int STATIC_BLUE = 1;
    private final static int STATIC_RED = 2;
    private final static int STATIC_YELLOW = 3;
    private final static int STATIC_GREEN = 4;

    int arrowState = STATIC_BLUE;

    int currentPoints = 0;
    private OnGameStatusChangedListener onGameStatusChangedListener;
    private boolean isGameFinished;

    private View rootView;
    public ColorMatchGameView(Activity activity){
        this.iv_arrow = activity.findViewById(R.id.iv_arrow);
        this.iv_button = activity.findViewById(R.id.iv_button);
        this.tv_points = activity.findViewById(R.id.tv_points);
        this.progressBar = activity.findViewById(R.id.progressBar);
        this.btnRetry = activity.findViewById(R.id.btnRetry);
        this.btnExit = activity.findViewById(R.id.btnExit);
        this.rootView = activity.findViewById(android.R.id.content);
        this.btnRetry.setVisibility(View.GONE);
        this.btnExit.setVisibility(View.GONE);
        this.activity = activity;
        isGameFinished = false;
    }

    public void setBackgroundColor(int color) {
        rootView.setBackgroundColor(color);
    }

    public void setArrowImage(int state){
        switch(state){
            case STATIC_BLUE:
                iv_arrow.setImageResource(R.drawable.ic_blue);
                arrowState = STATIC_BLUE;
                break;
            case STATIC_RED:
                iv_arrow.setImageResource(R.drawable.ic_red);
                arrowState = STATIC_RED;
                break;
            case STATIC_YELLOW:
                iv_arrow.setImageResource(R.drawable.ic_yellow);
                arrowState = STATIC_YELLOW;
                break;
            case STATIC_GREEN:
                iv_arrow.setImageResource(R.drawable.ic_green);
                arrowState = STATIC_GREEN;
                break;
        }
    }

    public void setRotation(final ImageView image, final int drawable){
        RotateAnimation rotateAnimation =new RotateAnimation(0, 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(100);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                image.setImageResource(drawable);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        image.startAnimation(rotateAnimation);
    }

    public ImageView getIvButton(){
        return iv_button;
    }

    public View getBtnRetry() {
        return btnRetry;
    }
    public View getBtnExit() { return btnExit; }

    public void displayPoints(int currentPoints){
        tv_points.setText("POINTS: " + currentPoints);
    }

    public void displayProgressBar(int max, int progress){
        progressBar.setMax(max);
        progressBar.setProgress(progress);
    }

    public void displayRetryButton(boolean visible){
        btnRetry.setVisibility(visible ? View.VISIBLE : View.GONE);
        btnExit.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setRotation(int buttonState, int buttonDrawable) {}

    public Activity getActivity() { return activity;}

    public void setOnGameStatusChangedListener(OnGameStatusChangedListener listener) {
        this.onGameStatusChangedListener = listener;
    }

    public void notifyGameStatusChangedListener() {
        if (onGameStatusChangedListener != null) {
            onGameStatusChangedListener.onGameStatusChanged(!isGameFinished);
        }
    }

    public boolean isFinished() {
        return isGameFinished;
    }

    public void setFinished(boolean finished) {
        isGameFinished = finished;
    }
}
