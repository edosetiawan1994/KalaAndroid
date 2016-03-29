package com.kala.kala;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignupDetailActivity extends AppCompatActivity{

    Bundle extras;
    Button nextButton, backButton;
    Calendar calendar, newDate;
    DatePickerDialog birthDatePickerDialog;
    EditText birthDateEditText, fullNameEditText, phoneNumberEditText;
    InputMethodManager in;
    Intent intent;
    RelativeLayout layout;
    SimpleDateFormat dateFormatter;
    String emailData, passwordData, fullnameData, phoneNumberData, birthDateData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_detail);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        extras = getIntent().getExtras();
        if(extras != null) {
            emailData = extras.getString("email");
            passwordData = extras.getString("password");
        }

        findViewsById();

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent ev) {
                in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return false;
            }
        });

        birthDateEditText.setInputType(InputType.TYPE_NULL);
        birthDateEditText.requestFocus();

        setDateTimeField();
    }

    private void findViewsById() {
        birthDateEditText = (EditText) findViewById(R.id.signupDetail_BirthDateHint);
        fullNameEditText = (EditText) findViewById(R.id.signupDetail_FullnameHint);
        layout = (RelativeLayout) findViewById(R.id.signupDetail_Layout);
        phoneNumberEditText = (EditText) findViewById(R.id.signupDetail_PhoneNumberHint);
        nextButton = (Button) findViewById(R.id.signupDetail_NextButton);
        backButton = (Button) findViewById(R.id.signupDetail_BackButton);
    }

    public void nextButtonOnClick (View view) {
//        if(phone number have no duplicate) {
        fullnameData = fullNameEditText.getText().toString().trim();
        phoneNumberData = phoneNumberEditText.getText().toString().trim();
        birthDateData = birthDateEditText.getText().toString().trim();

        intent = new Intent(getApplicationContext(), SignupUserActivity.class);
        intent.putExtra("email", emailData);
        intent.putExtra("password", passwordData);
        intent.putExtra("fullname", fullnameData);
        intent.putExtra("phonenumber", phoneNumberData);
        intent.putExtra("birthdate", birthDateData);
        startActivity(intent);
//        }
//        else {
//            Toast.makeText(getApplicationContext(),"Phone Number is already Registered",Toast.LENGTH_SHORT).show();
//        }
    }

    public void backButtonOnClick (View view) {
        onBackPressed();
    }

    public void birthDateEditTextOnClick (View view) {
        birthDatePickerDialog.show();
    }

    private void setDateTimeField() {
        calendar = Calendar.getInstance();
        birthDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                birthDateEditText.setText(dateFormatter.format(newDate.getTime()));
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }
}
