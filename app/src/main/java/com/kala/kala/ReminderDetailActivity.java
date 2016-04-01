package com.kala.kala;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ReminderDetailActivity extends AppCompatActivity {

    Bundle bundle = new Bundle();

    public static long Id = 0;
    public static String Title = "titleKey";
    public static String Participant = "userKey";
    public static String Due_date = "dateKey";
    public static String Time = "timeKey";
    public static String Note = "NoteKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Reminder Detail");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Intent intent = getIntent();
        Id = intent.getLongExtra(ReminderFragment.EXTRA_ID, Id);
        Title = intent.getStringExtra(ReminderFragment.EXTRA_TITLE);
        Due_date = intent.getStringExtra(ReminderFragment.EXTRA_DUE_DATE);
        Participant = intent.getStringExtra(ReminderFragment.EXTRA_PARTICIPANT);
        Note = intent.getStringExtra(ReminderFragment.EXTRA_NOTE);

        bundle.putLong("id", Id);
        bundle.putString("participants", Participant);
        bundle.putString("title", Title);
        bundle.putString("date", Due_date);
        bundle.putString("time", Time);
        bundle.putString("note", Note);

        final Button editButton = (Button) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(getApplicationContext(),ReminderEditActivity.class);

                Toast.makeText(getApplicationContext(), "Edit Button Clicked", Toast.LENGTH_SHORT).show();
                startActivity(editIntent);
            }
        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        ReminderDetailFragment ReminderDetailObject = new ReminderDetailFragment();
        ReminderDetailObject.setArguments(bundle);
    }

    public Bundle getBundle(){
        return bundle;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ReminderDetailFragment(), "");
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

    public boolean onOptionsItemSelected(MenuItem item){
        super.onBackPressed();
        return true;
    }
}
