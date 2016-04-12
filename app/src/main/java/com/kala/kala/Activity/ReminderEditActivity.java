package com.kala.kala.Activity;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kala.kala.Fragment.ReminderEditFragment;
import com.kala.kala.LocalModel.Note;
import com.kala.kala.LocalModel.Reminder;
import com.kala.kala.MainActivity;
import com.kala.kala.R;
import com.kala.kala.Util.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ReminderEditActivity extends AppCompatActivity {

    DatabaseHelper db;

    Bundle bundle = new Bundle();

    Long reminderId = ReminderDetailActivity.Id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_edit);

        db = new DatabaseHelper(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Reminder Edit");

        String reminderParticipants = ReminderDetailActivity.Participant;
        String reminderTitle = ReminderDetailActivity.Title;
        String reminderDate = ReminderDetailActivity.Due_date;
        String reminderTime = ReminderDetailActivity.Time;
        String reminderNote = ReminderDetailActivity.Note;

        bundle.putLong("id", reminderId);
        bundle.putString("participants", reminderParticipants);
        bundle.putString("title", reminderTitle);
        bundle.putString("date", reminderDate);
        bundle.putString("time", reminderTime);
        bundle.putString("note", reminderNote);

        Log.e("datas", reminderParticipants + " " + reminderTitle + " " + reminderDate + " " + reminderTime + " " + reminderNote);

        final Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get data from fragment
                TextView newTitle = (TextView) findViewById(R.id.reminderEditTitle);
                TextView newParticipants = (TextView) findViewById(R.id.reminderEditParticipants);
                TextView newDate = (TextView) findViewById(R.id.reminderEditDate);
                TextView newNote = (TextView) findViewById(R.id.reminderEditNote);
                String stringTitle = newTitle.getText().toString();
                String stringParticipants = newParticipants.getText().toString();
                String stringDueDate = newDate.getText().toString();
                String stringNote = newNote.getText().toString();
                // end of getting data from fragment
                Log.e("SAVED DATA", stringTitle + " " + stringParticipants + " " + stringDueDate + " " + stringNote);
                Log.e("reminder id", " " + reminderId);
                Reminder editedReminder = db.getReminder(reminderId);
                editedReminder.setTitle(stringTitle);
                editedReminder.setDuedate(stringDueDate);
                db.updateReminder(editedReminder);
                Note editedNote = db.getNoteByReminder(reminderId);
                editedNote.setContent(stringNote);
                db.updateNote(editedNote);
                db.closeDB();
                Toast.makeText(getApplicationContext(), "Save Button Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
    }

    public Bundle getBundle() {
        return bundle;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ReminderEditFragment(), "");
        viewPager.setAdapter(adapter);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;
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
