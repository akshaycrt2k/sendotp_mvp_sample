package com.akshaycrt2k.kisannetworkotp.dagger.component;

import com.akshaycrt2k.kisannetworkotp.dagger.module.ContactDataModule;
import com.akshaycrt2k.kisannetworkotp.dagger.module.ContactDetailModule;
import com.akshaycrt2k.kisannetworkotp.ui.activity.ContactDetailActivity;

import dagger.Component;

/**
 * Created by Akshay Mundotia on 04-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

@Component(modules = {ContactDetailModule.class, ContactDataModule.class})
public interface ContactDetailsComponent {
    void inject(ContactDetailActivity activity);
}
