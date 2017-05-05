package com.akshaycrt2k.kisannetworkotp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.akshaycrt2k.kisannetworkotp.R;

/**
 * Created by Akshay Mundotia on 04-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class ContactDetailActivity extends AppCompatActivity{


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_contact_detail);


        setup();
    }

    private void setup() {


    }


    private void setupToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.title_toolbar_home);
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
}
