package com.example.casazo_gamespace.Widget;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.example.casazo_gamespace.MainActivity;

public class WidgetState {
    public static void hideWidget(Context context){
        // Check if the widget is running and hide it
        if (Settings.canDrawOverlays(context)) {
            Intent intent = new Intent(context, WidgetService.class);
            context.stopService(intent);
        }
    }
}
