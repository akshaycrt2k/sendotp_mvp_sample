package com.akshaycrt2k.kisannetworkotp.ui.contracts;

import com.akshaycrt2k.kisannetworkotp.data.model.Contact;
import com.akshaycrt2k.kisannetworkotp.ui.BasePresenter;
import com.akshaycrt2k.kisannetworkotp.ui.BaseView;

/**
 * Created by Akshay Mundotia on 04-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public interface ContactDetailContract {
    interface View extends BaseView<Presenter> {
        void setContactName(String name);
        void setContactNumber(String number);
    }

    interface Presenter extends BasePresenter {
        void onSendMessage();
    }
}
