package com.example.casazo_gamespace;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.casazo_gamespace.swipegame.SwipeGameView;

public class MainActivity extends AppCompatActivity {

    Button play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.playButton);

        SwipeGameView swipeGameView = new SwipeGameView(this, null);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(swipeGameView);
                //TO DO
                //timer
                //maybe ontouch animation
                //save the score??
            }
        });


    }
}