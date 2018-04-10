package cgg.com.threeapp.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Process;
import android.util.Log;

import cgg.com.threeapp.MyApplication;

/**
 * author: Wanderer
 * date:   2018/2/23 18:59
 * email:  none
 */

public class CommedUtil {
    private static SharedPreferences msp;
    public static void runOnUiThread(Runnable runnable){
        if(Process.myTid() == MyApplication.getMyTid()){
            runnable.run();
        }else{
            MyApplication.getMyHandler().post(runnable);
        }
    }

    // 存储字符串
    public static void setSharedString(String key,String value){
        initShared();
        boolean commit = msp.edit().putString(key, value).commit();
        Log.e("SharedReturn:setString=",""+commit);
    };

    // 存储boolean
    public static void setSharedBoolean(String key,boolean value){
        initShared();
        boolean commit = msp.edit().putBoolean(key, value).commit();
        Log.e("SharedReturn:setString=",""+commit);
    };

    // 存储int
    public static void setSharedInt(String key,int value){
        initShared();
        boolean commit = msp.edit().putInt(key, value).commit();
        Log.e("SharedReturn:setInt=",""+commit);
    };


    // 获取字符串
    public static String getStringValue(String key){
        initShared();
        return msp.getString(key,"isNull");
    };

    // 获取boolean
    public static boolean getBooleanValue(String key){
        initShared();
        return msp.getBoolean(key,false);
    };

    // 获取int
    public static int getIntValue(String key){
        initShared();
        return msp.getInt(key,-1);
    };


    private static void initShared(){
        if(null == msp){
            msp = MyApplication.getContext().getSharedPreferences("ThreeAppShared", Context.MODE_PRIVATE);
        }
    }

    public static void backLogin(){
        initShared();
        boolean commit = msp.edit().clear().commit();
        Log.e("SharedReturn:clear=",""+commit);
    }

}
