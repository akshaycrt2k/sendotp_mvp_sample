package com.akshaycrt2k.kisannetworkotp.ui.contracts;

import com.akshaycrt2k.kisannetworkotp.data.model.Message;
import com.akshaycrt2k.kisannetworkotp.ui.BasePresenter;
import com.akshaycrt2k.kisannetworkotp.ui.BaseView;

import java.util.List;

/**
 * Created by Akshay Mundotia on 04-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public interface HistoryContract {
    interface View extends BaseView<Presenter> {
        void showLoading(boolean show);
        void showEmptyView(boolean show);

        void setMessages(List<Message> messages);
    }

    interface Presenter extends BasePresenter {

    }
}
