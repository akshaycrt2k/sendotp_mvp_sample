package com.akshaycrt2k.kisannetworkotp.ui.contracts;

import com.akshaycrt2k.kisannetworkotp.ui.BasePresenter;
import com.akshaycrt2k.kisannetworkotp.ui.BaseView;

/**
 * Created by Akshay Mundotia on 04-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public interface MessageContract {
    interface View extends BaseView<Presenter> {
        void showProgress(boolean show);
        void showSuccessDialog();
        void showFailureDialog();
        void setOtp(int otp);
        String getMessage();
    }

    interface Presenter extends BasePresenter {
        void onSend();
        void onDone();
    }
}
