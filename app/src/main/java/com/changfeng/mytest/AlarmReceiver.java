package com.changfeng.mytest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by changfeng on 2015/4/1.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override

    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, LongRunningService.class);
        context.startService(i);
    }
}
