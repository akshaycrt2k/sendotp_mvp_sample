package com.akshaycrt2k.kisannetworkotp.dagger.module;

import com.akshaycrt2k.kisannetworkotp.ui.contracts.ContactsListContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Akshay Mundotia on 04-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */
@Module
public class ContactsListModule {
    private ContactsListContract.View view;

    public ContactsListModule(ContactsListContract.View view) {
        this.view = view;
    }

    @Provides
    public ContactsListContract.View provideView() {
        return this.view;
    }
}
