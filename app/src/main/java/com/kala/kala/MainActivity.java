package com.kala.kala;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public UserSessionManager session;
    public DatabaseHelper db;
    public static SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    String nameFromServer, usernameFromServer, emailFromServer, phoneFromServer;
    Toolbar toolbar;
    ViewPager viewPager;
    Drawable contact, agenda, setting;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        db = new DatabaseHelper(getApplicationContext());
        session = new UserSessionManager(getApplicationContext());
        contact = getResources().getDrawable(R.drawable.contact);
        agenda = getResources().getDrawable(R.drawable.agenda);
        setting = getResources().getDrawable(R.drawable.setting);

        if(sharedPreferences.getString("name",null) == null &&
                sharedPreferences.getString("username",null) == null &&
                sharedPreferences.getString("phone",null) == null &&
                sharedPreferences.getString("email",null) == null) {
            // set shared preferences for now
            // should get it from server database
            nameFromServer = "Edo";
            usernameFromServer = "edo1994";
            emailFromServer = "edo1994@gmail";
            phoneFromServer = "0812345678";

            editor.putInt("picture", R.drawable.images1);
            editor.putString("name", nameFromServer);
            editor.putString("username", usernameFromServer);
            editor.putString("email", emailFromServer);
            editor.putString("phone", phoneFromServer);
            editor.apply();
            // end of setting shared preferences
            Log.e("shared pref", "changed");
        } else {
            // DO NOTHING
            Log.e("shared pref", "no changes");
        }

        Log.e("shared pref", sharedPreferences.getString("name","null") + " " +
                sharedPreferences.getString("username","null") + " " +
                sharedPreferences.getString("email","null") + " " +
                sharedPreferences.getString("phone","null"));

        // check if user login's status is logged in
        if(session.checkLogin())
            finish();
        // end of checking user login status

        findViewById();

        setSupportActionBar(toolbar);

        final Button newAgendaButton = (Button) findViewById(R.id.newAgendaButton);
        newAgendaButton.setVisibility(View.INVISIBLE);
        newAgendaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReminderNewActivity.class);
                startActivity(intent);
            }
        });

        getSupportActionBar().setTitle("Contact");

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
        setupViewPager(viewPager);
        contact.setColorFilter(getResources().getColor(R.color.colorIcon), PorterDuff.Mode.SRC_ATOP);
        agenda.setColorFilter(getResources().getColor(R.color.colorIcon), PorterDuff.Mode.SRC_ATOP);
        setting.setColorFilter(getResources().getColor(R.color.colorIcon), PorterDuff.Mode.SRC_ATOP);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(contact);
        tabLayout.getTabAt(1).setIcon(agenda);
        tabLayout.getTabAt(2).setIcon(setting);
    }

    private void findViewById() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ContactFragment(), "");
        adapter.addFragment(new ReminderFragment(), "");
        adapter.addFragment(new SettingFragment(), "");
        viewPager.setAdapter(adapter);
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