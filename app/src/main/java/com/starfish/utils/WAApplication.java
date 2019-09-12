package com.starfish.utils;

import android.app.Application;

public class WAApplication extends Application {
    public static Application mApplicationContext;

    public static boolean mIsLogin = false;


    public static boolean isLoading = false;
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
