package com.kala.kala.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.kala.kala.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignupDetailActivity extends AppCompatActivity {

    Button nextButton, backButton;
    DatePickerDialog birthDatePickerDialog;
    EditText birthDateEditText, fullNameEditText, phoneNumberEditText;
    RelativeLayout layout;
    SimpleDateFormat dateFormatter;
    String emailData, passwordData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_detail);

        // initialize variables ===============================================================start
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            emailData = extras.getString("email");
            passwordData = extras.getString("password");
        }
        // ======================================================================================end

        // set findViewById ===================================================================start
        birthDateEditText = (EditText) findViewById(R.id.signupDetail_BirthDateHint);
        fullNameEditText = (EditText) findViewById(R.id.signupDetail_FullnameHint);
        layout = (RelativeLayout) findViewById(R.id.signupDetail_Layout);
        phoneNumberEditText = (EditText) findViewById(R.id.signupDetail_PhoneNumberHint);
        nextButton = (Button) findViewById(R.id.signupDetail_NextButton);
        backButton = (Button) findViewById(R.id.signupDetail_BackButton);
        // ======================================================================================end

        // set Listener =======================================================================start
        setListener();
        // ======================================================================================end

        birthDateEditText.setInputType(InputType.TYPE_NULL);
        birthDateEditText.requestFocus();

        setDateTimeField();
    }

    private void setListener() {
        // layout listener ====================================================================start
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent ev) {
                InputMethodManager in = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                return false;
            }
        });
        // ======================================================================================end

        // next button listener ===============================================================start
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullnameData = fullNameEditText.getText().toString().trim();
                String phoneNumberData = phoneNumberEditText.getText().toString().trim();
                int birthDateData = Integer.parseInt(
                        birthDateEditText.getText().toString().trim().replaceAll("[\\D]", ""));

                Intent intent = new Intent(getApplicationContext(), SignupUserActivity.class);
                intent.putExtra("email", emailData);
                intent.putExtra("password", passwordData);
                intent.putExtra("fullname", fullnameData);
                intent.putExtra("phonenumber", phoneNumberData);
                intent.putExtra("birthdate", birthDateData);
                startActivity(intent);
            }
        });
        // ======================================================================================end

        // back button listener ===============================================================start
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // ======================================================================================end

        // birth date edit text listener ======================================================start
        birthDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birthDatePickerDialog.show();
            }
        });
        // ======================================================================================end
    }

    private void setDateTimeField() {
        Calendar calendar = Calendar.getInstance();
        birthDatePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                birthDateEditText.setText(dateFormatter.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }
}
