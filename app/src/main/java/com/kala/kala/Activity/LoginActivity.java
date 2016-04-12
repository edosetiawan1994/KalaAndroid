package com.kala.kala.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kala.kala.ServerModel.Schema.User.User;
import com.kala.kala.R;
import com.kala.kala.Util.Callback;
import com.kala.kala.Util.DatabaseHelper;
import com.kala.kala.Util.ModelManager;
import com.kala.kala.Util.UserSessionManager;

public class LoginActivity extends AppCompatActivity {
    Button loginButton, signupButton;
    DatabaseHelper db;
    EditText emailEditText, passwordEditText;
    RelativeLayout layout;
    String emailPattern, passwordPattern;
    UserSessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialize variables ===============================================================start
        db = new DatabaseHelper(getApplicationContext());
        session = new UserSessionManager(getApplicationContext());
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        passwordPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
        // ======================================================================================end

        // set findViewById ===================================================================start
        loginButton = (Button) findViewById(R.id.login_LoginButton);
        signupButton = (Button) findViewById(R.id.login_SignupButton);
        layout = (RelativeLayout) findViewById(R.id.login_Layout);
        emailEditText = (EditText) findViewById(R.id.login_EmailEditText);
        passwordEditText = (EditText) findViewById(R.id.login_PasswordEditText);
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

        // signup button listener =============================================================start
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
        // ======================================================================================end

        // login button listener ==============================================================start
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get username, password from EditText
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Validate if username, password is filled
                if (email.trim().length() > 0 && password.trim().length() > 0) {
                    // validate email
                    if (email.trim().matches(emailPattern)) {
                        final ModelManager modelManager =
                                ModelManager.getInstance(getApplicationContext());
                        modelManager.login(email, password, new Callback<String>() {
                            @Override
                            public void callback(Error error, String result) {
                                if (error != null) {
                                    Log.e("Login", "failed");
                                } else {
                                    Log.d("Login", "success");
                                    modelManager.getUserModel().getUserById(result,
                                            new Callback<User>() {
                                        @Override
                                        public void callback(Error error, User result) {
                                            if (error != null) {
                                                Log.e("Get User by Id", "failed");
                                            } else {
                                                Log.d("Get User by Id", "success");
                                                String loggedFullname = result.getFullname();
                                                String loggedEmail = result.getEmail();
                                                String loggedPhone = result.getPhone();
                                                String loggedUsername = result.getUsername();
                                                int loggedBirthdate = result.getBirthdate();
                                                Log.d("User Info", "Fullname = " + loggedFullname
                                                        + ", Email = " + loggedEmail
                                                        + ", Phone = " + loggedPhone
                                                        + ", Username = " + loggedUsername
                                                        + ", Birthdate = " + loggedBirthdate);

                                                session.createUserLoginSession(loggedFullname,
                                                        loggedEmail);

                                                // Starting MainActivity
                                                Intent intent = new Intent(getApplicationContext(),
                                                        LoadingActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                                // Put user info into intent
                                                intent.putExtra("fullname", loggedFullname);
                                                intent.putExtra("email", loggedEmail);
                                                intent.putExtra("phone", loggedPhone);
                                                intent.putExtra("username", loggedUsername);
                                                intent.putExtra("birthdate", loggedBirthdate);

                                                // Add new Flag to start new Activity
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "invalid email address",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // user didn't entered username or password
                    Toast.makeText(getApplicationContext(), "Please enter username and password",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        // ======================================================================================end
    }
}