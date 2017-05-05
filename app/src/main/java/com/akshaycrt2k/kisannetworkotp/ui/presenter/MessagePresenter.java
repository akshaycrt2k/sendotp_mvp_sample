package com.akshaycrt2k.kisannetworkotp.ui.presenter;

import com.akshaycrt2k.kisannetworkotp.ui.contracts.MessageContract;

import javax.inject.Inject;

/**
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class MessagePresenter implements MessageContract.Presenter {

    MessageContract.View view;

    @Inject
    public MessagePresenter(MessageContract.View view) {
        this.view = view;
    }


    @Inject
    public void setupListeners() {
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void onDestroy() {

    }
}
