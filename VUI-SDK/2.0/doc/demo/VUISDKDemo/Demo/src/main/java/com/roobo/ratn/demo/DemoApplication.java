package com.roobo.ratn.demo;

import android.app.Application;

import com.roobo.xutils.util.NativeLogCatReceiver;


public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NativeLogCatReceiver.startNativeLogcat(this);
    }
}
