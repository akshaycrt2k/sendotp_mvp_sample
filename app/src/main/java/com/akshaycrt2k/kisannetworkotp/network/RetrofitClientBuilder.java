package com.akshaycrt2k.kisannetworkotp.network;

import android.annotation.SuppressLint;
import android.content.res.Resources;

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class RetrofitClientBuilder {

    private static final String BOUNCY_CASTLE = "BKS";
    private static final String TLS = "TLS";
    protected OkHttpClient okHttpClient = new OkHttpClient();
    private OkHttpClient.Builder builder = okHttpClient.newBuilder();


    public RetrofitClientBuilder setConnectionTimeout(int connectionTimeout, TimeUnit timeUnit) {
        builder.connectTimeout(connectionTimeout, timeUnit);
        return this;
    }

    public RetrofitClientBuilder setReadTimeout(int connectionTimeout, TimeUnit timeUnit) {
        builder.readTimeout(connectionTimeout, timeUnit);
        return this;
    }

    public RetrofitClientBuilder setWriteTimeout(int connectionTimeout, TimeUnit timeUnit) {
        builder.writeTimeout(connectionTimeout, timeUnit);
        return this;
    }


    public RetrofitClientBuilder setInterceptors(Interceptor interceptors){

        builder.interceptors().add(interceptors);
        return this;
    }


    public RetrofitClientBuilder setNetworkInterceptors(Interceptor interceptors)
    {
        builder.networkInterceptors().add(interceptors);
        return this;
    }


    public RetrofitClientBuilder setRetryOnConnectionFailure(boolean isRetry){
        builder.retryOnConnectionFailure(isRetry);
        return this;
    }


    public RetrofitClientBuilder setCache(Cache cache){
        builder.cache(cache);
        return this;
    }


    public OkHttpClient build() {
        return builder.build();
    }

}
