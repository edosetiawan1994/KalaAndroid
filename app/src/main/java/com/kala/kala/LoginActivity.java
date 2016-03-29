package com.kala.kala;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button loginButton, signupButton;
    DatabaseHelper db;
    EditText emailEditText, usernameEditText, passwordEditText;
    Intent intent;
    InputMethodManager in;
    RelativeLayout layout;
    UserSessionManager session;

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
        usernameEditText = (EditText) findViewById(R.id.login_UsernameEditText);
        passwordEditText = (EditText) findViewById(R.id.login_PasswordEditText);
    }

    public void signupButtonOnClick(View view) {
        // redirect to Signup activity
        intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(intent);
    }

    public void loginButtonOnClick(View view) {
        // Get username, password from EditText
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        final ModelManager modelManager = ModelManager.getInstance(this);

        // Validate if username, password is filled
        if(username.trim().length() > 0 && password.trim().length() > 0){
            // For testing purpose username, password is checked with static data
            // username = admin
            // password = admin

            modelManager.login(username,password,new Callback<String>() {
                @Override
                public void callback(Error error, String result) {
                    if (error != null) {
                        Log.e("Login", "failed");
                    } else {
                        Log.d("Login", "success");
                    }
                }
            });

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