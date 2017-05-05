package com.akshaycrt2k.kisannetworkotp.dagger.module;

import com.akshaycrt2k.kisannetworkotp.network.ApiService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */
@Module
public class ApiServiceModule {
    private ApiService apiService;

    public ApiServiceModule(ApiService apiService) {
        this.apiService = apiService;
    }

    @Provides
    public ApiService provideApiService() {
        return apiService;
    }
}
