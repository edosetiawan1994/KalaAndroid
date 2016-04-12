package com.kala.kala;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.kala.kala.Activity.ReminderNewActivity;
import com.kala.kala.Fragment.ContactFragment;
import com.kala.kala.Fragment.ReminderFragment;
import com.kala.kala.Fragment.SettingFragment;
import com.kala.kala.Util.DatabaseHelper;
import com.kala.kala.Util.UserSessionManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public UserSessionManager session;
    public DatabaseHelper db;
    Toolbar toolbar;
    ViewPager viewPager;
    Drawable contact, agenda, setting;
    TabLayout tabLayout;
    Button newAgendaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize variables ===============================================================start
        db = new DatabaseHelper(getApplicationContext());
        session = new UserSessionManager(getApplicationContext());
        contact = getResources().getDrawable(R.drawable.contact);
        agenda = getResources().getDrawable(R.drawable.agenda);
        setting = getResources().getDrawable(R.drawable.setting);
        // ======================================================================================end

        // check user session =================================================================start
        if (session.checkLogin())
            finish();
        // ======================================================================================end

        // set findViewById ===================================================================start
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        newAgendaButton = (Button) findViewById(R.id.newAgendaButton);
        // ======================================================================================end

        // set listener =======================================================================start
        setListener();
        // ======================================================================================end

        // set up view pager ==================================================================start
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ContactFragment(), "");
        adapter.addFragment(new ReminderFragment(), "");
        adapter.addFragment(new SettingFragment(), "");
        viewPager.setAdapter(adapter);
        // ======================================================================================end

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contact");
        newAgendaButton.setVisibility(View.INVISIBLE);
        contact.setColorFilter(getResources().getColor(R.color.colorIcon), PorterDuff.Mode.SRC_ATOP);
        agenda.setColorFilter(getResources().getColor(R.color.colorIcon), PorterDuff.Mode.SRC_ATOP);
        setting.setColorFilter(getResources().getColor(R.color.colorIcon), PorterDuff.Mode.SRC_ATOP);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(contact);
        tabLayout.getTabAt(1).setIcon(agenda);
        tabLayout.getTabAt(2).setIcon(setting);
    }

    private void setListener() {
        // new agenda button listener =========================================================start
        newAgendaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReminderNewActivity.class);
                startActivity(intent);
            }
        });
        // ======================================================================================end

        // viewpager listener =================================================================start
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        getSupportActionBar().setTitle("Contact");
                        newAgendaButton.setVisibility(View.INVISIBLE);
                        break;

                    case 1:
                        getSupportActionBar().setTitle("Agenda");
                        newAgendaButton.setVisibility(View.VISIBLE);
                        break;

                    case 2:
                        getSupportActionBar().setTitle("Setting");
                        newAgendaButton.setVisibility(View.INVISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        // ======================================================================================end

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}