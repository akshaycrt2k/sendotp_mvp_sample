package com.akshaycrt2k.kisannetworkotp.dagger.module;

import com.akshaycrt2k.kisannetworkotp.ui.contracts.MessageContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Akshay Mundotia on 04-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */
@Module
public class MessageModule {
    private MessageContract.View view;

    public MessageModule(MessageContract.View view) {
        this.view = view;
    }

    @Provides
    public MessageContract.View provideView() {
        return this.view;
    }
}
