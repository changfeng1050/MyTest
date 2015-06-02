package com.changfeng.mytest;

import android.app.ActivityManager;
import android.content.Context;
import android.net.TrafficStats;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by changfeng on 2015/5/26.
 */
public class Utils {

    Context mAppContext;

    Utils(Context context) {
        mAppContext = context;
    }

    public static SimpleDateFormat sDateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss.SSS");

    public static String getCurrentTime() {
         return sDateFormat.format(new Date());
    }


    public static String getElapsedTimes() {
        long ut = SystemClock.elapsedRealtime() / 1000;
        if (ut == 0) {
            ut = 1;
        }
        int s = (int) (ut % 60);
        int m = (int) ((ut / 60) % 60);
        int h = (int) ((ut / 3600));
        return String.valueOf(h) + ":" + String.valueOf(m) + ":" + String.valueOf(s);
    }

    public static String getCurCpuFreq() {
        String result = "N/A";
        try {
            FileReader fr = new FileReader(
                    "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public double getTotalMemory() {
        ActivityManager am = (ActivityManager)mAppContext.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        DecimalFormat df = new DecimalFormat("#0.00");
        return Double.valueOf(df.format(mi.totalMem /1024.0 /1024.0 /1024.0));
    }
    public double getAvailMemory()
    {
        ActivityManager am = (ActivityManager) mAppContext.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        DecimalFormat df = new DecimalFormat("#0.00");
        df.format(mi.availMem);
        return Double.valueOf(df.format(mi.availMem /1024.0 /1024.0 /1024.0));
//        return Formatter.formatFileSize(getActivity().getBaseContext(), mi.availMem);
    }

    public String getLocalIp(){
        WifiManager wifiManager = (WifiManager)mAppContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        if(ipAddress==0) return "N/A";
        return ((ipAddress & 0xff)+"."+(ipAddress>>8 & 0xff)+"."
                +(ipAddress>>16 & 0xff)+"."+(ipAddress>>24 & 0xff));
    }

    public String getConnectWifiSSID() {
        WifiManager wifiManager = (WifiManager)mAppContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        if (wifiInfo != null) {
            return wifiInfo.getSSID();
        } else {
            return "N/A";
        }

    }

    public String getConnectWifiLevel() {
        WifiManager wifiManager = (WifiManager)mAppContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        if (wifiInfo != null) {
            return String.valueOf(wifiInfo.getRssi());
        } else {
            return "N/A";
        }

    }

    public String getConnectedWifiInfo() {
        WifiManager wifiManager = (WifiManager)mAppContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        if (wifiInfo != null) {
            int ipAddress = wifiInfo.getIpAddress();
            String address = ((ipAddress & 0xff)+"."+(ipAddress>>8 & 0xff)+"."
                    +(ipAddress>>16 & 0xff)+"."+(ipAddress>>24 & 0xff));
            return wifiInfo.getSSID() + " " + address + " " + wifiInfo.getRssi();
        } else {
            return "N/A";
        }

    }

    public long getTotalRxBytes(){  //获取总的接受字节数，包含Mobile和WiFi等
        return TrafficStats.getTotalRxBytes()==TrafficStats.UNSUPPORTED?0:(TrafficStats.getTotalRxBytes()/1024);
    }

    public long getTotalTxBytes(){  //总的发送字节数，包含Mobile和WiFi等
        return TrafficStats.getTotalTxBytes()==TrafficStats.UNSUPPORTED?0:(TrafficStats.getTotalTxBytes()/1024);
    }
    public long getMobileRxBytes(){  //获取通过Mobile连接收到的字节总数，不包含WiFi
        return TrafficStats.getMobileRxBytes()==TrafficStats.UNSUPPORTED?0:(TrafficStats.getMobileRxBytes()/1024);
    }

}
