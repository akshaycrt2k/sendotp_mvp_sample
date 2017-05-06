package com.akshaycrt2k.kisannetworkotp.dagger.component;

import com.akshaycrt2k.kisannetworkotp.dagger.module.ContactsListModule;
import com.akshaycrt2k.kisannetworkotp.dagger.module.DataRepositoryModule;
import com.akshaycrt2k.kisannetworkotp.dagger.module.HistoryModule;
import com.akshaycrt2k.kisannetworkotp.ui.activity.HomeActivity;

import dagger.Component;

/**
 * HomeComponent
 * Dagger uses this interface to generate its own implementations
 * according to the modules provided in the notation
 * --------------------------------------------------------------
 * Created by Akshay Mundotia on 04-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

@Component( modules = {ContactsListModule.class, HistoryModule.class, DataRepositoryModule.class})
public interface HomeComponent {
    void inject(HomeActivity homeActivity);
}
