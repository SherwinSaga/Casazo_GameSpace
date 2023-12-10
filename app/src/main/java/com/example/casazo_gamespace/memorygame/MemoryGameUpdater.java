package com.example.casazo_gamespace.memorygame;
import android.graphics.Color;
import android.os.Handler;

import java.util.Random;

public class MemoryGameUpdater {
    private MemoryGameView view;
    private int delayBetweenTurns;
    private Handler handler;

    public MemoryGameUpdater(MemoryGameView view) {
        this.view = view;
        this.handler = new Handler();
    }

    public void updateBackgroundColor() {
        int color = Color.rgb(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256));
        view.setBackgroundColor(color);
    }

}

