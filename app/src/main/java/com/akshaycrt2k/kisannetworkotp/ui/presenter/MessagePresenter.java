package com.akshaycrt2k.kisannetworkotp.ui.presenter;

import android.util.Log;

import com.akshaycrt2k.kisannetworkotp.data.model.Contact;
import com.akshaycrt2k.kisannetworkotp.data.model.Message;
import com.akshaycrt2k.kisannetworkotp.event.LaunchHomeScreenEvent;
import com.akshaycrt2k.kisannetworkotp.network.ApiService;
import com.akshaycrt2k.kisannetworkotp.network.NetworkResponse;
import com.akshaycrt2k.kisannetworkotp.ui.contracts.MessageContract;

import org.greenrobot.eventbus.EventBus;

import java.util.Random;
import java.util.TimeZone;

import javax.inject.Inject;

import hirondelle.date4j.DateTime;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class MessagePresenter implements MessageContract.Presenter {

    MessageContract.View view;
    ApiService apiService;
    Contact contact;
    int otp = -1;

    @Inject
    public MessagePresenter(MessageContract.View view, ApiService apiService, Contact contact) {
        this.view = view;
        this.contact = contact;
        this.apiService = apiService;
    }


    @Inject
    public void setupListeners() {
        view.setPresenter(this);
    }

    @Override
    public void start() {
        Log.d("message", "start: ");
        //Create Random 6 digit otp
        otp = 100000 + (int)(new Random().nextFloat() * 899999);

        //Display Otp to User
        view.setOtp(otp);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSend() {

        view.showProgress(true);

        Call<NetworkResponse> call = apiService.sendMessage(contact.getPhoneNumber(),view.getMessage());
        call.enqueue(new Callback<NetworkResponse>() {
            @Override
            public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {
                view.showProgress(false);
                if(response.isSuccessful() && response.body().getStatus()) {

                    //Save the message to DB
                    new Message(contact, DateTime.now(TimeZone.getDefault()),otp).save();

                    view.showSuccessDialog();
                } else {
                    view.showFailureDialog();
                }
            }

            @Override
            public void onFailure(Call<NetworkResponse> call, Throwable t) {
                t.printStackTrace();
                view.showProgress(false);
                view.showFailureDialog();
            }
        });

    }

    @Override
    public void onDone() {
        EventBus.getDefault().post(new LaunchHomeScreenEvent());
    }
}
