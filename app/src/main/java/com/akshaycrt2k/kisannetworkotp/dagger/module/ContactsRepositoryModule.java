package com.akshaycrt2k.kisannetworkotp.dagger.module;

import com.akshaycrt2k.kisannetworkotp.data.ContactsRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

@Module
public class ContactsRepositoryModule {
    private ContactsRepository contactsRepository;

    public ContactsRepositoryModule(ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }


    @Provides
    public ContactsRepository provideRepository() {
        return contactsRepository;
    }
}
