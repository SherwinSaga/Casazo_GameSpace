package com.example.casazo_gamespace;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.casazo_gamespace.colormatchgame.ColorMatchGameController;
import com.example.casazo_gamespace.colormatchgame.ColorMatchGameModel;
import com.example.casazo_gamespace.colormatchgame.ColorMatchGameView;


public class MainActivity extends AppCompatActivity {

    private ColorMatchGameModel colorMatchGameModel;
    private ColorMatchGameView colorMatchGameView;
    private ColorMatchGameController colorMatchGameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colormatchgame_layout);

        // Instantiate Model, View, and Controller
        colorMatchGameModel = new ColorMatchGameModel();
        colorMatchGameView = new ColorMatchGameView(this);
        colorMatchGameController = new ColorMatchGameController(colorMatchGameModel, colorMatchGameView);
    }
}