package com.akshaycrt2k.kisannetworkotp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akshaycrt2k.kisannetworkotp.R;
import com.akshaycrt2k.kisannetworkotp.ui.contracts.HistoryContract;

import butterknife.ButterKnife;

/**
 * Created by Akshay Mundotia on 04-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class HistoryFragment extends Fragment implements HistoryContract.View{

    HistoryContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history,container,false);
        ButterKnife.bind(this,view);


        return view;
    }

    public static HistoryFragment newInstance() {

        Bundle args = new Bundle();

        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(HistoryContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
