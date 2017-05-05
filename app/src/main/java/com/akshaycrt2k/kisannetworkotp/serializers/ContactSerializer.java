package com.akshaycrt2k.kisannetworkotp.serializers;

import com.activeandroid.serializer.TypeSerializer;
import com.akshaycrt2k.kisannetworkotp.data.model.Contact;

/**
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class ContactSerializer extends TypeSerializer {
    @Override
    public Class<?> getDeserializedType() {
        return Contact.class;
    }

    @Override
    public Class<?> getSerializedType() {
        return String.class;
    }

    @Override
    public String serialize(Object data) {

        String serializedString = ((Contact)data).getName()+";"+((Contact)data).getPhoneNumber();

        return serializedString;
    }

    @Override
    public Object deserialize(Object data) {

        String[] dataStr = ((String)data).split(";");

        return new Contact(dataStr[0],dataStr[1]);
    }
}
