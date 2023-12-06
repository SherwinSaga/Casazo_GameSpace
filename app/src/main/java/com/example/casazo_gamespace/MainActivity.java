package com.example.casazo_gamespace;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.casazo_gamespace.Widget.WidgetService;
import com.example.casazo_gamespace.Widget.WidgetState;
import com.example.casazo_gamespace.colormatchgame.ColorMatchGameController;
import com.example.casazo_gamespace.colormatchgame.ColorMatchGameModel;
import com.example.casazo_gamespace.colormatchgame.ColorMatchGameView;
import com.example.casazo_gamespace.swipegame.SwipeGameView;

public class MainActivity extends AppCompatActivity {



    private ColorMatchGameModel colorMatchGameModel;
    private ColorMatchGameView colorMatchGameView;
    private ColorMatchGameController colorMatchGameController;
    private SwipeGameView sw;

    private boolean isAppInForeground = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        WidgetState.checkPermission(this);
        WidgetState.hideWidget(this);

        //saga game
        sw = new SwipeGameView(this, null);
        //setContentView(sw);

        //castro game
        setContentView(R.layout.colormatchgame_layout);
        colorMatchGameModel = new ColorMatchGameModel();
        colorMatchGameView = new ColorMatchGameView(MainActivity.this);
        colorMatchGameController = new ColorMatchGameController(colorMatchGameModel, colorMatchGameView);



        //to do (saga)
        //on boot integrate with widget
        //notifs during hours na common ang sleepiness

        //assigned Jess
        //to do
        //random connect
        //ang reset na button kay decide lang
        //reset either start balik current game or simply leave it to random
        //disregard score??? idk sir pwede rasad

        //so if mag implement kag restart jess kay di malikayan na naay ma edit sa games namo kung ma gameover.

    }


    //ayaw ni hilabti ty kay mabuang ang widget
    @Override
    protected void onStart() {
        super.onStart();
        isAppInForeground = true;
        WidgetState.hideWidget(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        isAppInForeground = false;
        WidgetState.startWidgetService(this, isAppInForeground);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (!Settings.canDrawOverlays(MainActivity.this)) {
                Toast.makeText(this, "Permission denied by user.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }
}