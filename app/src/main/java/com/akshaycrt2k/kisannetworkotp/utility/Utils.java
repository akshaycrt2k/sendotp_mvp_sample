package com.akshaycrt2k.kisannetworkotp.utility;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.akshaycrt2k.kisannetworkotp.R;

/**
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class Utils {

    public static void ReplaceFragment(AppCompatActivity activity, Fragment baseFragment, final String TAG, final int frameLayoutId, boolean addToBackStack, boolean showAnimation){
        FragmentManager fragmentManager=activity.getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        if (showAnimation){
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        }
        transaction.replace(frameLayoutId,baseFragment,TAG);
        if (addToBackStack){
            transaction.addToBackStack(TAG);
        }
        transaction.commit();
    }

    public static void showLog(String tagName, String message)
    {
        if ( !TextUtils.isEmpty(message))
        {
            int maxLogSize = 1000;
            for (int i = 0; i <= message.length() / maxLogSize; i++)
            {
                int start = i * maxLogSize;
                int end = (i + 1) * maxLogSize;
                end = end > message.length() ? message.length() : end;
                Log.v(tagName, message.substring(start, end));
            }
        }
    }
}
