package com.kala.kala.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.kala.kala.Fragment.ContactDetailFragment;
import com.kala.kala.Fragment.ContactFragment;
import com.kala.kala.R;

import java.util.ArrayList;
import java.util.List;

public class ContactInfoActivity extends AppCompatActivity {

    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Contact Info");

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        Intent intent = getIntent();
        String name = intent.getStringExtra(ContactFragment.EXTRA_NAME);
        String username = intent.getStringExtra(ContactFragment.EXTRA_USERNAME);
        String email = intent.getStringExtra(ContactFragment.EXTRA_EMAIL);
        String phone = intent.getStringExtra(ContactFragment.EXTRA_PHONE);

        bundle.putString("name", name);
        bundle.putString("username", username);
        bundle.putString("email", email);
        bundle.putString("phone", phone);

        ContactDetailFragment ContactDetailObject = new ContactDetailFragment();
        ContactDetailObject.setArguments(bundle);

//        View frag = findViewById(R.id.viewpager);
//        frag.setVisibility(View.INVISIBLE);
    }

    public Bundle getBundle() {
        return bundle;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ContactDetailFragment(), "");
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
