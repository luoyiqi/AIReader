package com.xiarh.aireader.config;

import android.app.Application;

/**
 * Created by xiarh on 2016/9/6.
 */
public class AppApplication extends Application {
    private static AppApplication instance = null;

    public static AppApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
