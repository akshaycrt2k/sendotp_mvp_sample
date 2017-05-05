package com.akshaycrt2k.kisannetworkotp.event;

import com.akshaycrt2k.kisannetworkotp.data.model.Contact;

/**
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class ContactSelectedEvent {
    private Contact contact;

    public ContactSelectedEvent(Contact contact) {
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }
}
