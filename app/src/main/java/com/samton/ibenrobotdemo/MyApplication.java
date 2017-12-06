package com.samton.ibenrobotdemo;

import android.app.Application;

import com.samton.IBenRobotSDK.core.MainSDK;

/**
 * <pre>
 *     author : syk
 *     e-mail : shenyukun1024@gmail.com
 *     time   : 2017/12/01
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MainSDK.init(this);
    }
}
