package com.akshaycrt2k.kisannetworkotp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akshaycrt2k.kisannetworkotp.Constants;
import com.akshaycrt2k.kisannetworkotp.R;
import com.akshaycrt2k.kisannetworkotp.data.model.Message;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private ArrayList<Message> messages;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_history,parent,false);
        return new ViewHolder(view);
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.textViewContactName.setText(message.getRecipient().getName());
        holder.textViewOtp.setText(message.getOtp());
        holder.textViewTimestamp.setText(message.getTimestamp().format(Constants.DATE_TIME_FORMAT_MESSAGES, Locale.getDefault() ));
    }

    @Override
    public int getItemCount() {

        if(messages!=null) {
            return messages.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewName) TextView textViewContactName;
        @BindView(R.id.textViewOtp) TextView textViewOtp;
        @BindView(R.id.textViewMessageTime) TextView textViewTimestamp;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
