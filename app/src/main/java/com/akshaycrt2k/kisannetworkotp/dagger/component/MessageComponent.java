package com.akshaycrt2k.kisannetworkotp.dagger.component;

import com.akshaycrt2k.kisannetworkotp.dagger.module.ApiServiceModule;
import com.akshaycrt2k.kisannetworkotp.dagger.module.ContactDataModule;
import com.akshaycrt2k.kisannetworkotp.dagger.module.MessageModule;
import com.akshaycrt2k.kisannetworkotp.ui.activity.MessageActivity;

import dagger.Component;

/**
 * MessageComponent
 * Dagger uses this interface to generate its own implementations
 * according to the modules provided in the notation
 * --------------------------------------------------------------
 * Created by Akshay Mundotia on 04-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

@Component(modules = {MessageModule.class, ApiServiceModule.class, ContactDataModule.class})
public interface MessageComponent {
    void inject(MessageActivity activity);
}
