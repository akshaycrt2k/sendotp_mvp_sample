package com.akshaycrt2k.kisannetworkotp.dagger.module;

import com.akshaycrt2k.kisannetworkotp.data.DataRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

@Module
public class DataRepositoryModule {
    private DataRepository dataRepository;

    public DataRepositoryModule(DataRepository contactsRepository) {
        this.dataRepository = contactsRepository;
    }


    @Provides
    public DataRepository provideRepository() {
        return dataRepository;
    }
}
