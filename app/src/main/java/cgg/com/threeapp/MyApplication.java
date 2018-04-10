package cgg.com.threeapp;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.dash.zxinglibrary.activity.ZXingLibrary;

/**
 * author: Wanderer
 * date:   2018/2/23 18:59
 * email:  none
 */

public class MyApplication extends Application{
    private static int myTid;
    private static Handler myHandler;
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        myTid = Process.myTid();
        myHandler = new Handler();
        context = this;
        ZXingLibrary.initDisplayOpinion(this);
    }

    public static int getMyTid() {
        return myTid;
    }

    public static Handler getMyHandler() {
        return myHandler;
    }

    public static Context getContext() {
        return context;
    }
}
