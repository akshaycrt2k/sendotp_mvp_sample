package com.akshaycrt2k.kisannetworkotp.ui.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akshaycrt2k.kisannetworkotp.R;
import com.akshaycrt2k.kisannetworkotp.ui.contracts.MessageContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class MessageFragment extends Fragment implements MessageContract.View{

    public static final String TAG = MessageFragment.class.getSimpleName();

    @BindView(R.id.textViewMessage) TextView textViewMessage;
    ProgressDialog progressDialog;
    MessageContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message,container,false);
        ButterKnife.bind(this,view);
        setup();
        return view;
    }


    public static MessageFragment newInstance() {

        Bundle args = new Bundle();

        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    private void setup() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);

    }

    @Override
    public void setPresenter(MessageContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgress(boolean show) {
        if(show && !progressDialog.isShowing()) {
            progressDialog.show();
        } else if(!show && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showSuccessDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle(R.string.title_success_dialog);
        alertDialogBuilder.setMessage(R.string.messsage_success_dialog);

        alertDialogBuilder.setNeutralButton(R.string.done, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                presenter.onDone();
            }
        });

        alertDialogBuilder.create().show();

    }

    @Override
    public void showFailureDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle(R.string.title_failure_dialog);
        alertDialogBuilder.setMessage(R.string.messsage_failure_dialog);

        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialogBuilder.setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                presenter.onSend();
            }
        });

        alertDialogBuilder.create().show();
    }

    @Override
    public void setOtp(int otp) {
        textViewMessage.setText(getString(R.string.text_message_otp,otp));
    }

    @Override
    public String getMessage() {
        return textViewMessage.getText().toString();
    }

    @OnClick(R.id.buttonSend)
    public void onClickSend() {
        presenter.onSend();
    }
}
