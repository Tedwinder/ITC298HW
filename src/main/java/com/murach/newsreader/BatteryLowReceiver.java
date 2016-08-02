package com.murach.newsreader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by tedcu76 on 7/11/16.
 */
public class BatteryLowReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        Log.d("News Reader", "Battery Low");
        //stop the service
        Intent service = new Intent(context, NewsReaderService.class);
        context.stopService(service);

    }
}
