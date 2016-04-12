package com.kala.kala.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kala.kala.R;

public class SignupActivity extends AppCompatActivity {

    Button nextButton, cancelButton;
    EditText emailEditText, passwordEditText;
    RelativeLayout layout;
    String emailPattern, passwordPattern;
    TextView passwordHintText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // initialize variables ===============================================================start
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        passwordPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
        // ======================================================================================end

        // set findViewById ===================================================================start
        cancelButton = (Button) findViewById(R.id.signup_CancelButton);
        nextButton = (Button) findViewById(R.id.signup_NextButton);
        layout = (RelativeLayout) findViewById(R.id.signup_Layout);
        emailEditText = (EditText) findViewById(R.id.signup_EmailHint);
        passwordEditText = (EditText) findViewById(R.id.signup_PasswordHint);
        passwordHintText = (TextView) findViewById(R.id.signup_PasswordRestrictionText);
        // ======================================================================================end

        // set Listener =======================================================================start
        setListener();
        // ======================================================================================end
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

        // cancel button listener =============================================================start
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // ======================================================================================end

        // next button listener ===============================================================start
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get data
                final String emailData = emailEditText.getText().toString();
                final String passwordData = passwordEditText.getText().toString();

                // Validate if username, password is filled
                if (emailData.trim().length() > 0 && passwordData.trim().length() > 0) {
                    // validate email
                    if (emailData.trim().matches(emailPattern)) {
                        // validate password
                        if (passwordData.trim().matches(passwordPattern)) {
                            // go to Signup Detail activity
                            Intent intent = new Intent(getApplicationContext(),
                                    SignupDetailActivity.class);
                            intent.putExtra("email", emailData);
                            intent.putExtra("password", passwordData);
                            startActivity(intent);
                        } else {
                            passwordHintText.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(),
                                    "password invalid", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "invalid email address", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // user didn't entered username or password
                    Toast.makeText(getApplicationContext(),
                            "Please enter email and password", Toast.LENGTH_LONG).show();
                }
            }
        });
        // ======================================================================================end
    }
}
