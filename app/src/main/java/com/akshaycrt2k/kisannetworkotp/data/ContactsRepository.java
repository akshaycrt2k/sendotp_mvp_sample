package com.akshaycrt2k.kisannetworkotp.data;

import android.content.Context;

import com.akshaycrt2k.kisannetworkotp.data.model.Contact;
import com.bluelinelabs.logansquare.LoganSquare;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class ContactsRepository {

    private Context context;

    public ContactsRepository(Context context) {
        this.context = context;
    }

    public ArrayList<Contact> getContacts() {

        ArrayList<Contact> contacts = null;
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open("contacts.json");
            contacts = new ArrayList<>(LoganSquare.parseList(inputStream,Contact.class));
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return contacts;

    }
}
