package com.akshaycrt2k.kisannetworkotp.ui.presenter;

import com.akshaycrt2k.kisannetworkotp.data.DataRepository;
import com.akshaycrt2k.kisannetworkotp.data.model.Message;
import com.akshaycrt2k.kisannetworkotp.ui.contracts.HistoryContract;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Presenter for the Message History View
 * ----------------------------------------
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class HistoryPresenter implements HistoryContract.Presenter {
    private HistoryContract.View view;
    private DataRepository dataRepository;

    Observable<List<Message>> messageObservable;
    Observer<List<Message>> messageObserver;

    Subscription subscription;

    /**
     *
     * @param view View Responsible for listing all the data
     * @param dataRepository Repository provides us with the past messages
     */
    @Inject
    public HistoryPresenter(HistoryContract.View view, DataRepository dataRepository) {
        this.view = view;
        this.dataRepository = dataRepository;
    }


    /**
     * Set the presenter to the view
     */
    @Inject
    void setupListeners() {
        view.setPresenter(this);
    }


    /**
     * Start with loading the past messages from the repository in background.
     * If there's no data then we show the empty message provided by the view
     */
    @Override
    public void start() {
        messageObservable = Observable.fromCallable(new Callable<List<Message>>() {
            @Override
            public List<Message> call() throws Exception {
                return dataRepository.getMessageHistory();
            }
        });

        messageObserver = new Observer<List<Message>>() {
            @Override
            public void onCompleted() {
                view.showLoading(false);
                subscription = null;
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                view.showLoading(false);
                subscription = null;
            }

            @Override
            public void onNext(List<Message> messages) {
                view.showLoading(false);
                if(messages==null || messages.isEmpty()) {
                    view.showEmptyView(true);
                } else {
                    view.showEmptyView(false);
                    view.setMessages(messages);
                }
            }
        };

        view.showLoading(true);

        subscription = messageObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(messageObserver);
    }

    /**
     * Unsubscribes to the observable subscription, in case the user leaves the screen
     */
    @Override
    public void onDestroy() {

        if(subscription!=null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
