package com.akshaycrt2k.kisannetworkotp.ui.presenter;

import com.akshaycrt2k.kisannetworkotp.data.model.Contact;
import com.akshaycrt2k.kisannetworkotp.event.LaunchMessageScreenEvent;
import com.akshaycrt2k.kisannetworkotp.ui.contracts.ContactDetailContract;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class ContactDetailsPresenter implements ContactDetailContract.Presenter {


    ContactDetailContract.View view;
    Contact contact;


    @Inject
    public ContactDetailsPresenter(ContactDetailContract.View view, Contact contact) {
        this.view = view;
        this.contact = contact;
    }

    @Inject
    void setupListeners() {
        view.setPresenter(this);
    }

    @Override
    public void start() {
        view.setContactName(contact.getName());
        view.setContactNumber(contact.getPhoneNumber());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSendMessage() {
        EventBus.getDefault().post(new LaunchMessageScreenEvent());
    }
}
