package com.akshaycrt2k.kisannetworkotp.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.akshaycrt2k.kisannetworkotp.Constants;
import com.akshaycrt2k.kisannetworkotp.R;
import com.akshaycrt2k.kisannetworkotp.dagger.component.DaggerHomeComponent;
import com.akshaycrt2k.kisannetworkotp.dagger.module.ContactsListModule;
import com.akshaycrt2k.kisannetworkotp.dagger.module.DataRepositoryModule;
import com.akshaycrt2k.kisannetworkotp.dagger.module.HistoryModule;
import com.akshaycrt2k.kisannetworkotp.data.DataRepository;
import com.akshaycrt2k.kisannetworkotp.data.model.Contact;
import com.akshaycrt2k.kisannetworkotp.event.ContactSelectedEvent;
import com.akshaycrt2k.kisannetworkotp.ui.fragment.ContactsListFragment;
import com.akshaycrt2k.kisannetworkotp.ui.fragment.HistoryFragment;
import com.akshaycrt2k.kisannetworkotp.ui.presenter.ContactsListPresenter;
import com.akshaycrt2k.kisannetworkotp.ui.presenter.HistoryPresenter;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * HomeActivity: Responsible for setting up the tabs, viewpager and the Presenters.
 * ---------------------------------------------------------------------------------------
 * Created by Akshay Mundotia on 04-05-2017.
 * Contact: akshaycrt2k@gmail.com
 */

public class HomeActivity extends AppCompatActivity {

    private final int NUM_TABS = 2;
    private final int POSITION_CONTACTS = 0;
    private final int POSITION_HISTORY = 1;
    @BindView(R.id.frameLayout) FrameLayout frameLayout;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.viewPager) ViewPager viewPager;

    @Inject ContactsListPresenter contactsListPresenter;
    @Inject HistoryPresenter historyPresenter;

    HistoryFragment historyFragment;
    ContactsListFragment contactsListFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        historyFragment = HistoryFragment.newInstance();
        contactsListFragment = ContactsListFragment.newInstance();


        //Use dagger to inject all the dependencies in the presenters
        DaggerHomeComponent.builder()
                .contactsListModule(new ContactsListModule(contactsListFragment))
                .historyModule(new HistoryModule(historyFragment))
                .dataRepositoryModule(new DataRepositoryModule(new DataRepository(getApplicationContext())))
                .build()
                .inject(this);



        setup();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Register this activity to subscribe to events
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        //UnRegister this activity
        EventBus.getDefault().unregister(this);
        super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.itemAbout) {
            startAboutActivity();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }


    private void startAboutActivity() {
        new LibsBuilder()
                .withAboutAppName(getString(R.string.app_name))
                .withAboutIconShown(true)
                .withAboutVersionShownName(true)
                .withAboutVersionShownCode(false)
                .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                .withActivityTitle("About")
                .withAboutDescription(getString(R.string.about_description))
                .withFields(R.string.class.getFields())
                .activity(this);
    }



    /**
     * Chain to submethods to setup Toolbar and ViewPager
     */
    private void setup() {
        setupToolbar();
        setupViewPager();

    }


    /**
     * Sets the title of the toolbar
     */
    private void setupToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setTitle(R.string.title_toolbar_home);
        }
    }


    /**
     * Setup the viewpager to show two tabs Contacts, History
     */
    private void setupViewPager() {

        //  Add the Tabs to the tablayout
        tabLayout.addTab(tabLayout.newTab().setText(R.string.title_tab_contacts));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.title_tab_history));

        //  Create the PagerAdapter responsible for handling the viewpager
        HomePagerAdapter homePagerAdapter= new HomePagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(homePagerAdapter);

        //  This will cause tablayout to respond to viewPager's Page Change Events
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //  This will cause touch events on tabs to be passed on to the viewpager
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    /**
     * Start the ContactDetailActivity
     * @param contact the contact selected
     */
    private void launchContactActivity(Contact contact) {
        Intent intent = new Intent(this,ContactDetailActivity.class);
        intent.putExtra(Constants.KEY_CONTACT, contact);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onContactSelected(ContactSelectedEvent contactSelectedEvent) {
        launchContactActivity(contactSelectedEvent.getContact());
    }


    /**
     * FragmentStatePagerAdapter subclass responsible for handling viewpager pageChange events
     */
    private class HomePagerAdapter extends FragmentStatePagerAdapter {


        public HomePagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case POSITION_CONTACTS :
                    return contactsListFragment;
                case POSITION_HISTORY :
                    return historyFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_TABS;
        }

    }

}
