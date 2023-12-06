package com.example.casazo_gamespace.Widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;


public class WidgetState {

    //to do fix
    //ig press ug back sa menu kay duplicate widget
    //ig play game nya ma gameover, restart or menu kay duplicate gihapon ang widget
    public static void startWidgetService(Activity activity, boolean isForeground) {
        if (!isForeground && !Settings.canDrawOverlays(activity)) {
            checkPermission(activity);
        } else if (!isForeground) {
            Intent intent = new Intent(activity, WidgetService.class);
            activity.startService(intent);
        }
    }

    public static void hideWidget(Context context) {
        // Check if the widget is running and hide it
        if (Settings.canDrawOverlays(context)) {
            Intent intent = new Intent(context, WidgetService.class);
            context.stopService(intent);
        }
    }

    public static void checkPermission(Activity activity) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(activity)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + activity.getPackageName()));
            activity.startActivityForResult(intent, 1);
        }
    }
}
