package com.jyy.download.util;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.view.View;

/**
 * 常用工具类
 */
public class CommonUtil {

    //判断是否有网络
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable() && mNetworkInfo.isConnected();
            }
        }
        return false;
    }
    /**
     * @return 返回boolean ,是否为wifi网络
     *
     */
    public final boolean hasWifiConnection(Context context) {
        final ConnectivityManager connectivityManager= (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        //是否有网络并且已经连接
        return (networkInfo!=null&& networkInfo.isConnectedOrConnecting());


    }

    /**
     * @return  判断网络是否可用，并返回网络类型，ConnectivityManager.TYPE_WIFI，ConnectivityManager.TYPE_MOBILE，不可用返回-1
     */
    public static final int getNetWorkConnectionType(Context context){
        final ConnectivityManager connectivityManager=(ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo wifiNetworkInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final NetworkInfo mobileNetworkInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(wifiNetworkInfo!=null &&wifiNetworkInfo.isAvailable()) {
            return ConnectivityManager.TYPE_WIFI;
        }
        else if(mobileNetworkInfo!=null &&mobileNetworkInfo.isAvailable()) {
            return ConnectivityManager.TYPE_MOBILE;
        }
        else {
            return -1;
        }
    }

}

