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
import com.example.casazo_gamespace.colormatchgame.ColorMatchGameController;
import com.example.casazo_gamespace.colormatchgame.ColorMatchGameModel;
import com.example.casazo_gamespace.colormatchgame.ColorMatchGameView;
import com.example.casazo_gamespace.swipegame.SwipeGameView;

public class MainActivity extends AppCompatActivity {



    private ColorMatchGameModel colorMatchGameModel;
    private ColorMatchGameView colorMatchGameView;
    private ColorMatchGameController colorMatchGameController;
    Button play;
    private boolean isAppInForeground = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.playButton);

        checkPermission();
        hideWidget();

        SwipeGameView sw = new SwipeGameView(this, null);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(sw);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        isAppInForeground = true;
        hideWidget();
    }

    @Override
    protected void onStop() {
        super.onStop();
        isAppInForeground = false;
        startWidgetService();
    }

    private void startWidgetService() {
        if (!isAppInForeground && !Settings.canDrawOverlays(MainActivity.this)) {
            checkPermission();
        } else if (!isAppInForeground) {
            Intent intent = new Intent(MainActivity.this, WidgetService.class);
            startService(intent);
        }
    }

    private void hideWidget() {
        // Check if the widget is running and hide it
        if (Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(MainActivity.this, WidgetService.class);
            stopService(intent);
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
        if (requestCode == 1) {
            if (!Settings.canDrawOverlays(MainActivity.this)) {
                Toast.makeText(this, "Permission denied by user.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}