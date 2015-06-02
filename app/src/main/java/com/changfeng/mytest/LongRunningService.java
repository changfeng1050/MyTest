package com.changfeng.mytest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LongRunningService extends Service {

    private static final String TAG = "LongRunningService";
    private static final int ONE_MINUTE = 60 * 1000;
    private static final int ONE_SECOND = 1000;

    private static final String LOG_FILE = "log.txt";
    private static final String ERROR_FILE = "error.txt";

    private long lastRxBytes = 0;
    private long[] netSpeed = new long[60];

    private int count = 0;


    long startTime;
    long elapsed_time;

    private Context mContext;
    private Utils utils;


    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        utils = new Utils(this);

        startTime = System.nanoTime(); // 开始时间

        lastRxBytes = utils.getTotalRxBytes();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(LongRunningService.this, "Service中子线程启动！", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        elapsed_time = (System.nanoTime() - startTime) / 1000 /1000 /1000;



        new Thread(new Runnable() {
            @Override
            public void run() {

                handler.postDelayed(runnable, ONE_SECOND);

//                boolean loop = true;

//                while (loop) {
//                    Looper.prepare();
//
//                    netSpeed[count] = utils.getTotalRxBytes() - lastRxBytes;
//                    lastRxBytes = utils.getTotalRxBytes();
//
//                    if (count % 10 == 0) {
//                        Toast.makeText(getApplicationContext(), "NetSpeed：" + String.valueOf(netSpeed[count]) + " KB/s", Toast.LENGTH_LONG).show();
//                    }
//
//                    if (count == 59) {
//                        String logFile = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + LOG_FILE;
//                        String currentTime = Utils.getCurrentTime();
//                        String elapsed_time = Utils.getElapsedTimes();
//                        String totalMemory = String.valueOf(utils.getTotalMemory());
//                        String availableMemory = String.valueOf(utils.getAvailMemory());
//                        String wifiInfo = utils.getConnectedWifiInfo();
//
//                        long sum = 0;
//                        for (long s : netSpeed) {
//                            sum += s;
//                        }
//                        long speed = sum / 60;
//
//                        try {
//
//                            MyFile.appendToFile(logFile, currentTime + " " +
//                                    elapsed_time + " " +
//                                    availableMemory + " " +
//                                    totalMemory + " " +
//                                    wifiInfo + " " +
//                                    String.valueOf(speed) + "\n");
//
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                        String info = getString(R.string.elapse_time) + " : " + elapsed_time + "\n" +
//                                getString(R.string.available_memory) + " : " + availableMemory + "\n" +
//                                getString(R.string.wifi_ssid) + ":" + utils.getConnectWifiSSID() + "\n" +
//                                getString(R.string.ip) + " : " + utils.getLocalIp() + "\n" +
//                                getString(R.string.wifi_rssi) + " : " + utils.getConnectWifiLevel() + "\n" +
//                                getString(R.string.net_speed) + " : " + speed;
//
//                        Toast toast = Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG);
//                        toast.show();
//
//                    }
//                    count++;
//                    if (count == 60) {
//                        count = 0;
//                    }

//                    try {
//
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        String errorFile = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + ERROR_FILE;
//                        String currentTime = Utils.getCurrentTime();
//                        try {
//                            MyFile.appendToFile(errorFile, currentTime + " " + e.getMessage());
//                        } catch (IOException ioe) {
//                            ioe.printStackTrace();
//                        }
//                    }



                }
//            }
        }).start();

//        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
//
//        long triggerAtTime = SystemClock.elapsedRealtime() + ONE_MINUTE;
//        long triggerAtTime = SystemClock.elapsedRealtime() + ONE_SECOND;
//        Intent i = new Intent(this, AlarmReceiver.class);
//        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
//        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            Looper.prepare();
            netSpeed[count] = utils.getTotalRxBytes() - lastRxBytes;
            lastRxBytes = utils.getTotalRxBytes();

            if (count % 10 == 0) {
                Toast.makeText(getApplicationContext(), "NetSpeed：" + String.valueOf(netSpeed[count]) + " KB/s", Toast.LENGTH_LONG).show();
            }

            if (count == 59) {
                String logFile = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + LOG_FILE;
                String currentTime = Utils.getCurrentTime();
                String elapsed_time = Utils.getElapsedTimes();
                String totalMemory = String.valueOf(utils.getTotalMemory());
                String availableMemory = String.valueOf(utils.getAvailMemory());
                String wifiInfo = utils.getConnectedWifiInfo();

                long sum = 0;
                for (long s : netSpeed) {
                    sum += s;
                }
                long speed = sum / 60;

                try {

                    MyFile.appendToFile(logFile, currentTime + " " +
                            elapsed_time + " " +
                            availableMemory + " " +
                            totalMemory + " " +
                            wifiInfo + " " +
                            String.valueOf(speed) + "\n");


                } catch (Exception e) {
                    e.printStackTrace();
                }

                String info = getString(R.string.elapse_time) + " : " + elapsed_time + "\n" +
                        getString(R.string.available_memory) + " : " + availableMemory + "\n" +
                        getString(R.string.wifi_ssid) + ":" + utils.getConnectWifiSSID() + "\n" +
                        getString(R.string.ip) + " : " + utils.getLocalIp() + "\n" +
                        getString(R.string.wifi_rssi) + " : " + utils.getConnectWifiLevel() + "\n" +
                        getString(R.string.net_speed) + " : " + speed;

                Toast toast = Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG);
                toast.show();

            }
            count++;
            if (count == 60) {
                count = 0;
            }

            handler.postDelayed(this, ONE_SECOND);
//            Looper.loop();
        }
    };




}
