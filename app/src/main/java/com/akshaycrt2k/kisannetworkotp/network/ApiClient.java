package com.akshaycrt2k.kisannetworkotp.network;

import com.akshaycrt2k.kisannetworkotp.Constants;
import com.github.aurae.retrofit2.LoganSquareConverterFactory;

import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class ApiClient {
    public static final String TAG=ApiClient.class.getSimpleName();
    private static final int RETRY_COUNT = 1;
    private static ApiService apiService;

    static {
        setupClient();
    }

    private ApiClient() {
    }

    private static void setupClient() {
        HttpInterceptor interceptor=new HttpInterceptor(RETRY_COUNT);
        interceptor.setLevel(HttpInterceptor.Level.BODY);
        RetrofitClientBuilder retrofitClientBuilder = new RetrofitClientBuilder();
        retrofitClientBuilder
                .setRetryOnConnectionFailure(true)
                .setConnectionTimeout(120, TimeUnit.SECONDS)
                .setReadTimeout(60, TimeUnit.SECONDS)
                .setWriteTimeout(120, TimeUnit.SECONDS)
                .setInterceptors(interceptor);

        OkHttpClient okHttpClient = retrofitClientBuilder.build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(LoganSquareConverterFactory.create())
                .client(okHttpClient)
                .build();

        apiService = retrofit.create(ApiService.class);
    }
    public static ApiService getApiService()
    {
        return apiService;
    }
}
