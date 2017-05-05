package com.akshaycrt2k.kisannetworkotp.dagger.module;

import com.akshaycrt2k.kisannetworkotp.ui.contracts.HistoryContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Akshay Mundotia on 04-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */
@Module
public class HistoryModule {
    private HistoryContract.View view;

    public HistoryModule(HistoryContract.View view) {
        this.view = view;
    }

    @Provides
    public HistoryContract.View provideView() {
        return this.view;
    }
}
