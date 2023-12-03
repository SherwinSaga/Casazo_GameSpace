package com.example.casazo_gamespace.colormatchgame;

import android.graphics.Color;

import java.util.Random;

public class ColorMatchGameUpdater {
    private ColorMatchGameView view;

    public ColorMatchGameUpdater(ColorMatchGameView view) {
        this.view = view;
    }

    public void updateBackgroundColor() {
        int color = Color.rgb(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256));
        view.setBackgroundColor(color);
    }
}

