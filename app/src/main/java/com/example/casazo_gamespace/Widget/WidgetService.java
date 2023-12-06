package com.example.casazo_gamespace.Widget;

import java.security.Provider;
import java.util.Calendar;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.PixelCopy;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.casazo_gamespace.MainActivity;
import com.example.casazo_gamespace.R;

public class WidgetService extends Service {
    int LAYOUT_FLAG;
    View mFloatingView;
    WindowManager windowManager;
    ImageView imageClose;
    TextView tvWidget;
    float height, width;
    boolean isWidgetCreated = false;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }
        else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }

        //inflating the widget
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_widget, null);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        //initial position
        layoutParams.gravity = Gravity.TOP|Gravity.RIGHT;
        layoutParams.x = 0;
        layoutParams.y = 100;


        //layout params for close button
        WindowManager.LayoutParams imageParams = new WindowManager.LayoutParams(140, 140,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        imageParams.gravity = Gravity.BOTTOM|Gravity.CENTER;
        imageParams.y = 100;

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        imageClose = new ImageView(this);
        imageClose.setImageResource(R.drawable.widget_close);
        imageClose.setVisibility(View.INVISIBLE);
        windowManager.addView(imageClose, imageParams);
        windowManager.addView(mFloatingView, layoutParams);
        mFloatingView.setVisibility(View.VISIBLE);

        height = windowManager.getDefaultDisplay().getHeight();
        width = windowManager.getDefaultDisplay().getWidth();
        tvWidget = (TextView) mFloatingView.findViewById(R.id.text_widget);



        tvWidget.setOnTouchListener(new View.OnTouchListener() {
            int initX, initY;
            float initialTouchX, initialTouchY;
            long startclicktime;
            int MAX_CLICK_DURATION=200;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:

                        startclicktime = Calendar.getInstance().getTimeInMillis();
                        imageClose.setVisibility(View.VISIBLE);
                        initX = layoutParams.x;
                        initY = layoutParams.y;

                        //touch pos
                        initialTouchX = motionEvent.getRawX();
                        initialTouchY = motionEvent.getRawY();

                        return true;
                    case MotionEvent.ACTION_UP:

                        long clickDuration = Calendar.getInstance().getTimeInMillis()-startclicktime;
                        imageClose.setVisibility(View.GONE);

                        layoutParams.x = initX+(int)(initialTouchX-motionEvent.getRawX());
                        layoutParams.y = initY+(int)(motionEvent.getRawY()-initialTouchY);

                        if(clickDuration<MAX_CLICK_DURATION){ //on click
                            //to do
                            if (!isWidgetCreated) {
                                // Create the widget
                                isWidgetCreated = true;

                                // Launch the activity
                                Intent launchIntent = new Intent(WidgetService.this, MainActivity.class);
                                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(launchIntent);
                            }
                        }else {
                            if(layoutParams.y >(height*0.6)){
                                stopSelf();
                            }
                        }


                        return true;
                    case MotionEvent.ACTION_MOVE:

                        layoutParams.x = initX+(int)(initialTouchX-motionEvent.getRawX());
                        layoutParams.y = initY+(int)(motionEvent.getRawY()-initialTouchY);

                        windowManager.updateViewLayout(mFloatingView, layoutParams);

                        if(layoutParams.y >(height*0.6)){
                            imageClose.setImageResource(R.drawable.widget_close);
                        }
                        else {
                            imageClose.setImageResource(R.drawable.widget_close_red);
                        }
                        return true;

                }
                return false;
            }
        });

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mFloatingView!=null){
            windowManager.removeView(mFloatingView);
        }
        if(imageClose!=null){
            windowManager.removeView(imageClose);
        }
    }
}
