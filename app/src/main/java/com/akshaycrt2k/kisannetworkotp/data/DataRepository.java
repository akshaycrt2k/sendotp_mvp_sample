package com.akshaycrt2k.kisannetworkotp.data;

import android.content.Context;

import com.activeandroid.query.Select;
import com.akshaycrt2k.kisannetworkotp.data.model.Contact;
import com.akshaycrt2k.kisannetworkotp.data.model.Message;
import com.bluelinelabs.logansquare.LoganSquare;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class DataRepository {

    private Context context;

    public DataRepository(Context context) {
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

    public List<Message> getMessageHistory() {
        List<Message> messages = new Select().from(Message.class).execute();

        if(messages!=null) {
            Collections.sort(messages, new Comparator<Message>() {
                @Override
                public int compare(Message left, Message right) {
                    if(left.getTimestamp().lt(right.getTimestamp())) {
                        return 1;
                    } if(left.getTimestamp().gt(right.getTimestamp())) {
                        return -1;
                    } else {
                        return 0;
                    }

                }
            });

        }
        return messages;
    }
}
