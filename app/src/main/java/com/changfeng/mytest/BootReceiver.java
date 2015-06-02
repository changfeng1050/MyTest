package com.changfeng.mytest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

/**
 * Created by changfeng on 2015/4/2.
 */
public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = "BootReceiver";
    private static final String BOOT_COMPLETED = "boot_completed";

    private static final String LOG_FILE = "log.txt";

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, TAG + " Boot completed.", Toast.LENGTH_LONG).show();


        try {
            String logFile = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + LOG_FILE ;
            String currentTime = Utils.getCurrentTime();
            String elapsed_time = Utils.getElapsedTimes();

            MyFile.appendToFile(logFile, currentTime + " " +
                    elapsed_time + " " +
                    BOOT_COMPLETED + "\n");


        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent i = new Intent(context, LongRunningService.class);
        context.startService(i);
    }
}
