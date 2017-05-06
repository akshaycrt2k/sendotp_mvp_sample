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


/**
 * Message Presenter
 * -----------------------------------------
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class MessagePresenter implements MessageContract.Presenter {

    MessageContract.View view;
    ApiService apiService;
    Contact contact;
    int otp = -1;

    /**
     *
     * @param view for the Message Screen UI
     * @param apiService Provides the network call methods
     * @param contact data
     */
    @Inject
    public MessagePresenter(MessageContract.View view, ApiService apiService, Contact contact) {
        this.view = view;
        this.contact = contact;
        this.apiService = apiService;
    }


    /**
     * Sets Presenter
     */
    @Inject
    public void setupListeners() {
        view.setPresenter(this);
    }

    /**
     * Create a random Otp and set it to the view
     */
    @Override
    public void start() {
        Log.d("message", "start: ");

        otp = getRandomOtp();

        //Display Otp to User
        view.setOtp(otp);
    }


    /**
     * Creates the random number to be used at an OTP
     * @return 6 Digit random number
     */
    private int getRandomOtp() {
        //Create Random 6 digit otp
        return (100000 + (int)(new Random().nextFloat() * 899999));
    }

    @Override
    public void onDestroy() {

    }


    /**
     * Creates a Retrofit Call with the help of apiService
     * and execute it in the background.
     */
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


    /**
     * Raise an event to go to the home screen
     */
    @Override
    public void onDone() {
        EventBus.getDefault().post(new LaunchHomeScreenEvent());
    }
}
