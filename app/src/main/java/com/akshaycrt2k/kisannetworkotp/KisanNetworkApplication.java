package com.akshaycrt2k.kisannetworkotp;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class KisanNetworkApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ActiveAndroid.initialize(this);
    }
}
