package com.example.admin.androidutils.ui;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by Admin on 2018/6/22.
 */

public class MyApplication extends Application {
    private static Context context;

    // 返回
    public static Context getContextObject() {

        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isBackground(this);

    }

    public boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Log.i("zjh", "处于后台" + appProcess.processName);
                    return true;
                } else {
                    Log.i("zjh", "处于前台" + appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

}


