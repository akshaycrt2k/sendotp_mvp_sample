package com.akshaycrt2k.kisannetworkotp.dagger.module;

import com.akshaycrt2k.kisannetworkotp.ui.contracts.ContactDetailContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Akshay Mundotia on 04-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */
@Module
public class ContactDetailModule {
    private ContactDetailContract.View view;

    public ContactDetailModule(ContactDetailContract.View view) {
        this.view = view;
    }

    @Provides
    public ContactDetailContract.View provideView() {
        return this.view;
    }
}
