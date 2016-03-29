package com.kala.kala;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ReminderNewActivity extends AppCompatActivity {
    public final static String EXTRA_TITLE = "reminder_title";
    public final static String EXTRA_PARTICIPANTS = "reminder_participants";
    public final static String EXTRA_DATE = "reminder_date";
    public final static String EXTRA_TIME = "reminder_time";
    public final static String EXTRA_NOTE = "reminder_note";

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder);

        db = new DatabaseHelper(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Button doneButton = (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get data from fragment
                TextView newTitle = (TextView)findViewById(R.id.reminderNewTitle) ;
                TextView newParticipants = (TextView)findViewById(R.id.reminderNewParticipants) ;
                TextView newDate = (TextView)findViewById(R.id.reminderNewDate) ;
                TextView newTime = (TextView)findViewById(R.id.reminderNewTime) ;
                TextView newNote = (TextView)findViewById(R.id.reminderNewNote) ;
                String stringTitle = newTitle.getText().toString();
                String stringParticipants = newParticipants.getText().toString();
                String stringDate = newDate.getText().toString();
                String stringTime = newTime.getText().toString();
                String stringNote = newNote.getText().toString();
                // end of getting data from fragment

                // insert new Note into note table in local database
                Note noteNew = new Note(stringNote);
                long note_id = db.createNote(noteNew);

                Log.e("fullname", "" + stringParticipants);
                long user_id = db.getUserByFullname(stringParticipants);

                // insert new Reminder into reminder table in local database
                Reminder reminderNew = new Reminder(stringTitle, stringDate + " " + stringTime);
                long reminder_id = db.createReminder(reminderNew, new long[] {note_id}, new long[] {user_id});
                // end of inserting new Reminder

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ReminderNewFragment(), "");
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
