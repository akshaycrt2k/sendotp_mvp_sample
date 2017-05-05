package com.akshaycrt2k.kisannetworkotp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akshaycrt2k.kisannetworkotp.R;
import com.akshaycrt2k.kisannetworkotp.ui.contracts.ContactDetailContract;
import com.akshaycrt2k.kisannetworkotp.ui.presenter.ContactDetailsPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class ContactDetailsFragment extends Fragment implements ContactDetailContract.View{

    public static final String TAG = ContactDetailsFragment.class.getSimpleName();

    @BindView(R.id.textViewName) TextView textViewName;
    @BindView(R.id.textViewNumber) TextView textViewNumber;
    ContactDetailContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_detail,container,false);
        ButterKnife.bind(this,view);
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

    public static ContactDetailsFragment newInstance() {

        Bundle args = new Bundle();

        ContactDetailsFragment fragment = new ContactDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(ContactDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setContactName(String name) {
        textViewName.setText(name==null?"":name);
    }

    @Override
    public void setContactNumber(String number) {
        textViewNumber.setText(number==null?"":number);
    }

    @OnClick(R.id.buttonSend)
    public void onClickSend() {
        presenter.onSendMessage();
    }
}
