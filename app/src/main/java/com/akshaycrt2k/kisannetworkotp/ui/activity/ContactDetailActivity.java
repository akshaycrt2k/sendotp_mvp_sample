package com.akshaycrt2k.kisannetworkotp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.akshaycrt2k.kisannetworkotp.Constants;
import com.akshaycrt2k.kisannetworkotp.R;
import com.akshaycrt2k.kisannetworkotp.dagger.component.DaggerContactDetailsComponent;
import com.akshaycrt2k.kisannetworkotp.dagger.module.ContactDataModule;
import com.akshaycrt2k.kisannetworkotp.dagger.module.ContactDetailModule;
import com.akshaycrt2k.kisannetworkotp.data.model.Contact;
import com.akshaycrt2k.kisannetworkotp.event.LaunchMessageScreenEvent;
import com.akshaycrt2k.kisannetworkotp.ui.fragment.ContactDetailsFragment;
import com.akshaycrt2k.kisannetworkotp.ui.presenter.ContactDetailsPresenter;
import com.akshaycrt2k.kisannetworkotp.utility.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Akshay Mundotia on 04-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class ContactDetailActivity extends AppCompatActivity{

    Contact contact;
    ContactDetailsFragment contactDetailsFragment;

    @Inject ContactDetailsPresenter contactDetailsPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null) {
            contact = bundle.getParcelable(Constants.KEY_CONTACT);
        }

        contactDetailsFragment =  ContactDetailsFragment.newInstance();

        DaggerContactDetailsComponent.builder()
                .contactDetailModule(new ContactDetailModule(contactDetailsFragment))
                .contactDataModule(new ContactDataModule(contact))
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

    private void setup() {
        setupToolbar();
        Utils.ReplaceFragment(this,contactDetailsFragment, ContactDetailsFragment.TAG,R.id.frameLayout,false,false);
    }



    private void launchMessageActivity() {
        Intent intent = new Intent(this,MessageActivity.class);
        intent.putExtra(Constants.KEY_CONTACT, contact);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }

    private void setupToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(contact.getName().split(" ")[0]);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home) {
            onBackPressed();
            return true;
        } else  {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,HomeActivity.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLaunchMessageScreen(LaunchMessageScreenEvent event) {
        launchMessageActivity();
    }
}
