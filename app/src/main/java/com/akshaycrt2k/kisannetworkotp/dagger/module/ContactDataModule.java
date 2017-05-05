package com.akshaycrt2k.kisannetworkotp.dagger.module;

import com.akshaycrt2k.kisannetworkotp.data.model.Contact;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */
@Module
public class ContactDataModule {
    private Contact contact;

    public ContactDataModule(Contact contact) {
        this.contact = contact;
    }

    @Provides
    public Contact provideContact() {
        return contact;
    }

}
