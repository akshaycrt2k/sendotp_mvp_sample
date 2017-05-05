package com.akshaycrt2k.kisannetworkotp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akshaycrt2k.kisannetworkotp.R;
import com.akshaycrt2k.kisannetworkotp.data.model.Contact;
import com.akshaycrt2k.kisannetworkotp.event.ContactSelectedEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Akshay Mundotia on 05-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder>{


    ArrayList<Contact> contacts;

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_contact_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.textViewName.setText(contact.getName());
        holder.contact = contact;
    }

    @Override
    public int getItemCount() {
        if(contacts!=null) {
            return contacts.size();
        } else {
            return 0;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewName) TextView textViewName;
        Contact contact;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick(R.id.layoutContact)
        public void onClickContact() {
            EventBus.getDefault().post(new ContactSelectedEvent(contact));
        }
    }
}
