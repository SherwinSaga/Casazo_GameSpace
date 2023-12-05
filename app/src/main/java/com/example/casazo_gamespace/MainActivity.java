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
import com.example.casazo_gamespace.swipegame.SwipeGameView;

public class MainActivity extends AppCompatActivity {

    Button play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        //play = findViewById(R.id.playButton);

        SwipeGameView swipeGameView = new SwipeGameView(this, null);



        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                setContentView(swipeGameView);
                //TO DO
                //maybe ontouch animation
                //save the score??
            }
        });

        */

        checkPermission();

        if(!Settings.canDrawOverlays(MainActivity.this)){
            checkPermission();
        }
        else {
            Intent intent = new Intent(MainActivity.this, WidgetService.class);
            startActivity(intent);
        }
    }

    public void checkPermission(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)){
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:"+getPackageName()));
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(!Settings.canDrawOverlays(MainActivity.this)){
                Toast.makeText(this, "Permission denied by user.", Toast.LENGTH_SHORT).show();
            }

        }
    }
}