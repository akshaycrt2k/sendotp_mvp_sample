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
 * Presenter for the contacts list view
 * This loads all the contacts from the json file in assets
 * and passes the data to the view
 * ----------------------------------------------------------
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class ContactsListPresenter implements ContactsListContract.Presenter {

    private ContactsListContract.View view;
    private DataRepository contactsRepository;

    Observable<ArrayList<Contact>> contactsObservable;
    Observer<ArrayList<Contact>> contactsObserver;
    Subscription subscription;

    /**
     * Here Dagger Injects the view and the dataRepository
     * @param view This view implements the ContactsListContract.View interface
     * @param dataRepository The DataRepository (Model Layer) is responsible for providing data to the presenter
     */
    @Inject
    public ContactsListPresenter(ContactsListContract.View view, DataRepository dataRepository) {
        this.view = view;
        this.contactsRepository = dataRepository;
    }

    /**
     *  Sets itself as presenter for the view
     */
    @Inject
    public void setupListeners() {
        view.setPresenter(this);
    }


    /**
     * Responsible for loading the data and setting it to the view.
     * Uses RxAndroid to create an Observable from a blocking method call
     * and loads the data in a separate thread.
     */
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


        // We subscribe to the observable on a new thread so as not to block the UI Thread
        // Once the observable emits the data, we observe and respond to it in the UI/Main thread
        subscription = contactsObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contactsObserver);

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
