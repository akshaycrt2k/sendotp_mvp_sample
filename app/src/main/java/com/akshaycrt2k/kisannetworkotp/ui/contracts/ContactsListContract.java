package com.akshaycrt2k.kisannetworkotp.ui.contracts;

import com.akshaycrt2k.kisannetworkotp.data.model.Contact;
import com.akshaycrt2k.kisannetworkotp.ui.BasePresenter;
import com.akshaycrt2k.kisannetworkotp.ui.BaseView;

import java.util.ArrayList;

/**
 * Created by Akshay Mundotia on 04-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public interface ContactsListContract {
    interface View extends BaseView<Presenter> {
        void showLoading(boolean show);
        void setContacts(ArrayList<Contact> contacts);
    }

    interface Presenter extends BasePresenter {

    }
}
