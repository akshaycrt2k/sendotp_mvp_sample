package com.akshaycrt2k.kisannetworkotp.ui.presenter;

import com.akshaycrt2k.kisannetworkotp.ui.contracts.ContactDetailContract;

import javax.inject.Inject;

/**
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class ContactDetailPresenter implements ContactDetailContract.Presenter {


    ContactDetailContract.View view;


    @Inject
    public ContactDetailPresenter(ContactDetailContract.View view) {
        this.view = view;
    }

    @Inject
    void setupListeners() {
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void onDestroy() {

    }
}
