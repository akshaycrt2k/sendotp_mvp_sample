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
import android.widget.TextView;

import com.akshaycrt2k.kisannetworkotp.R;
import com.akshaycrt2k.kisannetworkotp.data.model.Message;
import com.akshaycrt2k.kisannetworkotp.ui.adapter.ContactListAdapter;
import com.akshaycrt2k.kisannetworkotp.ui.adapter.HistoryAdapter;
import com.akshaycrt2k.kisannetworkotp.ui.contracts.HistoryContract;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Akshay Mundotia on 04-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class HistoryFragment extends Fragment implements HistoryContract.View{

    public static final String TAG = HistoryFragment.class.getSimpleName();

    HistoryContract.Presenter presenter;
    ProgressDialog progressDialog;
    @BindView(R.id.recyclerViewHistory) RecyclerView recyclerView;
    @BindView(R.id.textViewEmpty) TextView textViewEmpty;
    HistoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history,container,false);
        ButterKnife.bind(this,view);

        setup();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    private void setup() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);

        adapter = new HistoryAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
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

    @Override
    public void showLoading(boolean show) {
        if(show && !progressDialog.isShowing()) {
            progressDialog.show();
        } else if(!show && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showEmptyView(boolean show) {
        textViewEmpty.setVisibility(show?View.VISIBLE:View.GONE);
        recyclerView.setVisibility(!show?View.VISIBLE:View.GONE);
    }

    @Override
    public void setMessages(List<Message> messages) {
        showEmptyView(false);
        adapter.setMessages(messages);
    }
}
