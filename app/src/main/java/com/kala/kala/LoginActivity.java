package com.kala.kala;

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

import com.kala.kala.Model.Schema.User.User;

public class LoginActivity extends AppCompatActivity {
    Button loginButton, signupButton;
    DatabaseHelper db;
    EditText emailEditText, passwordEditText;
    Intent intent;
    InputMethodManager in;
    RelativeLayout layout;
    String emailPattern, passwordPattern;
    UserSessionManager session;
    String loggedFullname, loggedUsername, loggedEmail, loggedPhone;
    int loggedBirthdate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Database Helper
        db = new DatabaseHelper(getApplicationContext());

        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

        // Get all View by Id
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
        loginButton = (Button) findViewById(R.id.login_LoginButton);
        signupButton = (Button) findViewById(R.id.login_SignupButton);
        layout = (RelativeLayout) findViewById(R.id.login_Layout);
        emailEditText = (EditText) findViewById(R.id.login_EmailEditText);
        passwordEditText = (EditText) findViewById(R.id.login_PasswordEditText);
    }

    public void signupButtonOnClick(View view) {
        // redirect to Signup activity
        intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(intent);
    }

    public void loginButtonOnClick(View view) {
        // Get username, password from EditText
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        final ModelManager modelManager = ModelManager.getInstance(this);

        // Validate if username, password is filled
        if(email.trim().length() > 0 && password.trim().length() > 0){
            // validate email
            if(email.trim().matches(emailPattern)){
                modelManager.login(email, password, new Callback<String>() {
                    @Override
                    public void callback(Error error, String result) {
                        if (error != null) {
                            Log.e("Login", "failed");
                        } else {
                            Log.d("Login", "success");
                            modelManager.getUserModel().getUserById(result, new Callback<User>() {
                                @Override
                                public void callback(Error error, User result) {
                                    if (error != null) {
                                        Log.e("Get User by Id", "failed");
                                    } else {
                                        Log.d("Get User by Id", "success" + result.toMap().toString());
                                        loggedFullname = result.getFullname();
                                        loggedEmail = result.getEmail();
                                        loggedPhone = result.getPhone();
                                        loggedUsername = result.getUsername();
                                        loggedBirthdate = result.getBirthdate();
                                        Log.d("User Info", "Fullname = " + loggedFullname
                                                + ", Email = " + loggedEmail + ", Phone = "
                                                + loggedPhone + ", Username = " + loggedUsername
                                                + ", Birthdate = " + loggedBirthdate);

//                                        // Starting MainActivity
//                                        intent = new Intent(getApplicationContext(), MainActivity.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                                        // Add new Flag to start new Activity
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                    }
                });
//                        }
//                        else {
//                            Toast.makeText(getApplicationContext(),
//                                  "Email is already Registered",Toast.LENGTH_SHORT).show();
//                        }
            }else{
                Toast.makeText(getApplicationContext(),
                        "invalid email address",Toast.LENGTH_SHORT).show();
            }
            // For testing purpose username, password is checked with static data
            // username = admin
            // password = admin

//            if(username.equals("admin") && password.equals("admin")){
//                // Creating user login session
//                // Statically storing name="Android Example"
//                // and email="androidexample84@gmail.com"
//                session.createUserLoginSession("Android Example",
//                        "androidexample84@gmail.com");
//
//                // Starting MainActivity
//                intent = new Intent(getApplicationContext(), MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                // Insert dummy User
//                User dummyUser = new User("Edo","edo1994",
//                        "edo1994@mail.com","0812345678","null","null",0);
//                db.createUser(dummyUser);
//
//                // Add new Flag to start new Activity
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//
//                finish();
//            }else{
//                // username / password doesn't match&
//                Toast.makeText(getApplicationContext(), "Username/Password is incorrect",
//                        Toast.LENGTH_LONG).show();
//            }
        }else{
            // user didn't entered username or password
            Toast.makeText(getApplicationContext(),
                    "Please enter username and password",
                    Toast.LENGTH_LONG).show();
        }
    }
}