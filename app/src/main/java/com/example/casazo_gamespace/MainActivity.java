package com.example.casazo_gamespace;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.widget.Toast;

import com.example.casazo_gamespace.FloatingWidget.WidgetState;
import com.example.casazo_gamespace.colormatchgame.ColorMatchGameController;
import com.example.casazo_gamespace.colormatchgame.ColorMatchGameModel;
import com.example.casazo_gamespace.colormatchgame.ColorMatchGameView;
import com.example.casazo_gamespace.memorygame.MemoryGameActivity;
import com.example.casazo_gamespace.swipegame.SwipeGameView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ColorMatchGameModel colorMatchGameModel;
    private ColorMatchGameView colorMatchGameView;
    private ColorMatchGameController colorMatchGameController;
    private SwipeGameView sw;
    private Random random;

    private boolean isAppInForeground = false;

    private static final String CHANNEL_ID = "playReminderChannel";
    private static final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        WidgetState.checkPermission(this);
        WidgetState.hideWidget(this);
        scheduleNotificationReminder();

        random = new Random();
        int randomGame = random.nextInt(3);

        switch (randomGame) {
            case 0:
                // Saga game (SwipeGameView)
                SwipeGameView sw = new SwipeGameView(this, null);
                setContentView(sw);
                break;
            case 1:
                // Castro game (ColorMatchGame)
                setContentView(R.layout.colormatchgame_layout);
                colorMatchGameModel = new ColorMatchGameModel();
                colorMatchGameView = new ColorMatchGameView(MainActivity.this);
                colorMatchGameController = new ColorMatchGameController(colorMatchGameModel, colorMatchGameView);
                break;
            case 2:
                // Zoilo game (MemoryGameActivity)
                Intent intent = new Intent(MainActivity.this, MemoryGameActivity.class);
                startActivity(intent);
                break;
        }

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

    //NOTIFICATION
    private void scheduleNotificationReminder() {
        final Handler handler = new Handler(Looper.getMainLooper());

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> showNotificationReminder());
            }
        }, 0, 10 * 1000); //60secs para demo purposes
    }

    private void showNotificationReminder() {
        if (!isAppInForeground) {
            createNotificationChannel();

            Intent mainIntent = new Intent(this, MainActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_IMMUTABLE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.widget_icon)
                    .setContentTitle("Play Reminder")
                    .setContentText("Bored? Sleepy? Play Now")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
                return;
            }
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showNotificationReminder();
            } else {
                Toast.makeText(this, "Permission denied by user.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Play Reminder Channel";
            String description = "Channel for play reminders";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}