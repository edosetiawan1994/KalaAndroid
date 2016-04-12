package com.kala.kala.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.kala.kala.MainActivity;
import com.kala.kala.R;
import com.kala.kala.Util.SharedPrefs;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // set user info ======================================================================start
        setUserInfo(getIntent().getExtras().getString("username"),
                getIntent().getExtras().getString("fullname"),
                getIntent().getExtras().getString("email"),
                getIntent().getExtras().getString("phone"),
                getIntent().getExtras().getInt("birthdate"));
        // ======================================================================================end

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void setUserInfo(String username, String fullname, String email, String phone,
                            int birthdate) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        SharedPrefs.setDefaults("username", username, getApplicationContext());
        SharedPrefs.setDefaults("fullname", fullname, getApplicationContext());
        SharedPrefs.setDefaults("email", email, getApplicationContext());
        SharedPrefs.setDefaults("phone", phone, getApplicationContext());
        SharedPrefs.setDefaults("birthdate", birthdate, getApplicationContext());

        editor.apply();
    }
}