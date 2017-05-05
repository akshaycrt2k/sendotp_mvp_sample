package com.akshaycrt2k.kisannetworkotp.network;

import com.akshaycrt2k.kisannetworkotp.Constants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public interface ApiService {

    @FormUrlEncoded
    @POST(Constants.API_SEND_SMS)
    Call<NetworkResponse> sendMessage(
            @Field(Constants.KEY_RECIPIENT_NUMBER) String number,
            @Field(Constants.KEY_MESSAGE) String message);

}
