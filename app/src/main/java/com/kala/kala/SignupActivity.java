package com.kala.kala;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    Button nextButton, cancelButton;
    EditText emailEditText, passwordEditText;
    InputMethodManager in;
    Intent intent;
    RelativeLayout layout;
    String emailPattern, passwordPattern;
    TextView passwordHintText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViewById();

        // set email and password pattern
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        passwordPattern =  "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";

        // set listener so that every time user touch
        // layout other than edittext will hide the soft keyboard
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent ev) {
                in = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                return false;
            }
        });
    }

    public void findViewById() {
        cancelButton = (Button) findViewById(R.id.signup_CancelButton);
        nextButton = (Button) findViewById(R.id.signup_NextButton);
        layout = (RelativeLayout) findViewById(R.id.signup_Layout);
        emailEditText = (EditText) findViewById(R.id.signup_EmailHint);
        passwordEditText = (EditText) findViewById(R.id.signup_PasswordHint);
        passwordHintText = (TextView) findViewById(R.id.signup_PasswordRestrictionText);
    }

    public void cancelButtonOnClick (View view) {
        onBackPressed();
    }

    public void  nextButtonOnClick (View view) {
        // get data
        String emailData = emailEditText.getText().toString();
        String passwordData = passwordEditText.getText().toString();

        // Validate if username, password is filled
        if(emailData.trim().length() > 0 && passwordData.trim().length() > 0) {
            // validate email
            if(emailData.trim().matches(emailPattern)){
//                        // check email have no duplicate in server
//                        if (email have no duplicate) {
                // validate password
                if(passwordData.trim().matches(passwordPattern)) {
                    // go to Signup Detail activity
                    intent = new Intent(getApplicationContext(), SignupDetailActivity.class);
                    intent.putExtra("email", emailData);
                    intent.putExtra("password", passwordData);
                    startActivity(intent);
                }else {
                    passwordHintText.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),
                            "password invalid",Toast.LENGTH_SHORT).show();
                }
//                        }
//                        else {
//                            Toast.makeText(getApplicationContext(),
//                                  "Email is already Registered",Toast.LENGTH_SHORT).show();
//                        }
            }else{
                Toast.makeText(getApplicationContext(),
                        "invalid email address",Toast.LENGTH_SHORT).show();
            }
        }else{
            // user didn't entered username or password
            Toast.makeText(getApplicationContext(),
                    "Please enter email and password", Toast.LENGTH_LONG).show();
        }
    }
}
