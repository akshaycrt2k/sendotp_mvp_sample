package com.akshaycrt2k.kisannetworkotp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.akshaycrt2k.kisannetworkotp.Constants;
import com.akshaycrt2k.kisannetworkotp.R;
import com.akshaycrt2k.kisannetworkotp.dagger.component.DaggerMessageComponent;
import com.akshaycrt2k.kisannetworkotp.dagger.module.ApiServiceModule;
import com.akshaycrt2k.kisannetworkotp.dagger.module.ContactDataModule;
import com.akshaycrt2k.kisannetworkotp.dagger.module.MessageModule;
import com.akshaycrt2k.kisannetworkotp.data.model.Contact;
import com.akshaycrt2k.kisannetworkotp.event.LaunchHomeScreenEvent;
import com.akshaycrt2k.kisannetworkotp.network.ApiClient;
import com.akshaycrt2k.kisannetworkotp.ui.fragment.ContactDetailsFragment;
import com.akshaycrt2k.kisannetworkotp.ui.fragment.MessageFragment;
import com.akshaycrt2k.kisannetworkotp.ui.presenter.MessagePresenter;
import com.akshaycrt2k.kisannetworkotp.utility.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by Akshay Mundotia on 04-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class MessageActivity extends AppCompatActivity {


    Contact contact;
    MessageFragment messageFragment;

    @Inject
    MessagePresenter messagePresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);

        Bundle bundle  = getIntent().getExtras();

        if(bundle!=null) {
            contact = bundle.getParcelable(Constants.KEY_CONTACT);
        }

        messageFragment = MessageFragment.newInstance();


        DaggerMessageComponent.builder()
                .apiServiceModule(new ApiServiceModule(ApiClient.getApiService()))
                .contactDataModule(new ContactDataModule(contact))
                .messageModule(new MessageModule(messageFragment))
                .build()
                .inject(this);


        setup();


    }


    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    /**
     * Sets the Fragment to the framelayout and chains to setupToolbar
     */
    private void setup() {
        setupToolbar();
        Utils.ReplaceFragment(this,messageFragment, MessageFragment.TAG,R.id.frameLayout,false,false);
    }


    /**
     * Here we use setDisplayHomeAsUpEnabled(true) to show the back button on the action bar
     */
    private void setupToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.title_toolbar_message);
        }
    }

    /**
     * We consume the home button pressed event to make sure the transition animation and the flow is consistent
     * @param item the menuItem selected by the user
     * @return true if the event is consumed, false otherwise
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home) {
            onBackPressed();
            return true;
        } else  {
            return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Override onBackpressed to start the parent Activity ourselves
     */
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,ContactDetailActivity.class).putExtra(Constants.KEY_CONTACT,contact));
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }

    /**
     * Respond to this event, to launch the homescreen
     * @param event raised to launch the homeActivity
     */
    @Subscribe( threadMode = ThreadMode.MAIN)
    public void onLaunchHomeScreen(LaunchHomeScreenEvent event) {
        startActivity(new Intent(this,HomeActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }
}
