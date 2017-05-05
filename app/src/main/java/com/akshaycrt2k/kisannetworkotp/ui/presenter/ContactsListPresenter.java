package com.akshaycrt2k.kisannetworkotp.ui.presenter;

import com.akshaycrt2k.kisannetworkotp.data.DataRepository;
import com.akshaycrt2k.kisannetworkotp.data.model.Contact;
import com.akshaycrt2k.kisannetworkotp.ui.contracts.ContactsListContract;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class ContactsListPresenter implements ContactsListContract.Presenter {

    private ContactsListContract.View view;
    private DataRepository contactsRepository;
    Observable<ArrayList<Contact>> contactsObservable;
    Observer<ArrayList<Contact>> contactsObserver;
    Subscription subscription;

    @Inject
    public ContactsListPresenter(ContactsListContract.View view, DataRepository contactsRepository) {
        this.view = view;
        this.contactsRepository = contactsRepository;
    }

    @Inject
    public void setupListeners() {
        view.setPresenter(this);
    }

    @Override
    public void start() {

        view.showLoading(true);

        contactsObservable = Observable.fromCallable(new Callable<ArrayList<Contact>>() {
            @Override
            public ArrayList<Contact> call() throws Exception {
                return contactsRepository.getContacts();
            }
        });

        contactsObserver = new Observer<ArrayList<Contact>>() {
            @Override
            public void onCompleted() {
                subscription = null;
                view.showLoading(false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                subscription = null;
                view.showLoading(false);
            }

            @Override
            public void onNext(ArrayList<Contact> contacts) {
                view.showLoading(false);
                view.setContacts(contacts);
            }
        };

        subscription = contactsObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contactsObserver);

    }

    @Override
    public void onDestroy() {
        if(subscription!=null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
