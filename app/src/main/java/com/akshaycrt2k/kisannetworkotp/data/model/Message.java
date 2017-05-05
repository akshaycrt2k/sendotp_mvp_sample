package com.akshaycrt2k.kisannetworkotp.data.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import hirondelle.date4j.DateTime;

/**
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */
@Table( name="messages")
public class Message extends Model{

    @Column(name ="contact")
    private Contact recipient;

    @Column(name ="timestamp")
    private DateTime timestamp;

    @Column(name = "otp")
    private int otp;


    public Message() {
        super();
    }

    public Message(Contact recipient, DateTime timestamp, int otp) {
        this.recipient = recipient;
        this.timestamp = timestamp;
        this.otp = otp;
    }


    public Contact getRecipient() {
        return recipient;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public int getOtp() {
        return otp;
    }
}
