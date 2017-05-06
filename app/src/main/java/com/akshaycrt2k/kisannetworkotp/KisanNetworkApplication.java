package com.akshaycrt2k.kisannetworkotp;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * KissanNetworkApplication
 * UI Architecture followed for this app is MVP.
 * With the help of Dagger for Dependency Injection (Helps with Testing)
 *
 * Application subclass required for initializing Active Android
 * ---------------------------------------------------------------------
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
