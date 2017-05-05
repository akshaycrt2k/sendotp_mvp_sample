package com.akshaycrt2k.kisannetworkotp.dagger.component;

import com.akshaycrt2k.kisannetworkotp.dagger.module.MessageModule;
import com.akshaycrt2k.kisannetworkotp.ui.activity.MessageActivity;

import dagger.Component;

/**
 * Created by Akshay Mundotia on 04-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

@Component(modules = {MessageModule.class})
public interface MessageComponent {
    void inject(MessageActivity activity);
}
