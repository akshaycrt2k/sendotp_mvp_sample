package com.akshaycrt2k.kisannetworkotp.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akshaycrt2k.kisannetworkotp.R;
import com.akshaycrt2k.kisannetworkotp.data.model.Contact;
import com.akshaycrt2k.kisannetworkotp.ui.adapter.ContactListAdapter;
import com.akshaycrt2k.kisannetworkotp.ui.contracts.ContactsListContract;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Akshay Mundotia on 04-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class ContactsListFragment extends Fragment implements ContactsListContract.View{

    public static final String TAG = ContactsListFragment.class.getSimpleName();

    ContactsListContract.Presenter presenter;
    ProgressDialog progressDialog;
    ContactListAdapter adapter;

    @BindView(R.id.recyclerViewContacts) RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts_list,container,false);
        ButterKnife.bind(this,view);
        setup();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onPause() {
        presenter.onDestroy();
        super.onPause();
    }


    /**
     * Here we set a LinearLayoutManager to our recyclerview
     * and the ContactListAdapter
     */
    private void setup() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);

        adapter = new ContactListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    public static ContactsListFragment newInstance() {

        Bundle args = new Bundle();

        ContactsListFragment fragment = new ContactsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(ContactsListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean show) {
        if(show && !progressDialog.isShowing()) {
            progressDialog.show();
        } else if(!show && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void setContacts(ArrayList<Contact> contacts) {
        adapter.setContacts(contacts);
    }
}
