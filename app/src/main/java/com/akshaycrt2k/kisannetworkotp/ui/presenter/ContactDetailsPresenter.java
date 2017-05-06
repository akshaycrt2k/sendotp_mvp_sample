package com.akshaycrt2k.kisannetworkotp.ui.presenter;

import com.akshaycrt2k.kisannetworkotp.data.model.Contact;
import com.akshaycrt2k.kisannetworkotp.event.LaunchMessageScreenEvent;
import com.akshaycrt2k.kisannetworkotp.ui.contracts.ContactDetailContract;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Presenter responsible for managing the Contact Details View
 * -------------------------------------------------------------
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class ContactDetailsPresenter implements ContactDetailContract.Presenter {


    ContactDetailContract.View view;
    Contact contact;


    /**
     *
     * @param view Representing the view layer for the UI
     * @param contact   Data
     */
    @Inject
    public ContactDetailsPresenter(ContactDetailContract.View view, Contact contact) {
        this.view = view;
        this.contact = contact;
    }


    /**
     * Setting the presenter
     */
    @Inject
    void setupListeners() {
        view.setPresenter(this);
    }


    /**
     * On Start, we set the data to be displayed by the view
     */
    @Override
    public void start() {
        view.setContactName(contact.getName());
        view.setContactNumber(contact.getPhoneNumber());
    }

    /**
     * We could cancel pending tasks in this methods - ongoing network calls, DB access operations.
     */
    @Override
    public void onDestroy() {

    }

    /**
     * Handles the action by the user
     */
    @Override
    public void onSendMessage() {
        //  Post the event to launch the message screen to the eventbus
        //  We could also inject the Eventbus same as we've done with the view and model layer
        EventBus.getDefault().post(new LaunchMessageScreenEvent());
    }
}
