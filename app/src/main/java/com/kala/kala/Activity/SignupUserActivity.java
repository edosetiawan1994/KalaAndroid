package com.kala.kala.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.firebase.client.Firebase;
import com.kala.kala.R;
import com.kala.kala.ServerModel.Schema.User.User;
import com.kala.kala.ServerModel.UserModel;
import com.kala.kala.Util.AWSManager;
import com.kala.kala.Util.Callback;
import com.kala.kala.Util.ModelManager;

import java.io.File;

public class SignupUserActivity extends AppCompatActivity {

    Button doneButton, backButton;
    EditText usernameEditText;
    ImageView userPic;
    int SELECT_PHOTO;
    RelativeLayout layout;
    String emailData, passwordData, fullnameData, phoneNumberData, usernameData;
    Integer birthDateData;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_user);

        // initialize variables ===============================================================start
        SELECT_PHOTO = 12345;
        Bundle extras;
        extras = getIntent().getExtras();
        if (extras != null) {
            emailData = extras.getString("email");
            passwordData = extras.getString("password");
            fullnameData = extras.getString("fullname");
            phoneNumberData = extras.getString("phonenumber");
            birthDateData = extras.getInt("birthdate");
        }
        // ======================================================================================end

        // set findViewById ===================================================================start
        layout = (RelativeLayout) findViewById(R.id.signupUser_Layout);
        usernameEditText = (EditText) findViewById(R.id.signupUser_UsernameHint);
        userPic = (ImageView) findViewById(R.id.signupUser_Pic);
        doneButton = (Button) findViewById(R.id.signupUser_DoneButton);
        backButton = (Button) findViewById(R.id.signupUser_BackButton);
        // set findViewById =====================================================================end

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

        // user picture listener ==============================================================start
        userPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPic.setImageBitmap(null);
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });
        // ======================================================================================end

        // done button listener ===============================================================start
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ModelManager modelManager = ModelManager.getInstance(getApplicationContext());
                final AWSManager awsManager = modelManager.getAwsManager();

                usernameData = usernameEditText.getText().toString().trim();

                modelManager.register(emailData, passwordData, new Callback<String>() {
                    @Override
                    public void callback(Error error, String result) {
                        if (error != null) {
                            Log.e("register new user", "failed");
                        } else {
                            Log.e("register new user", "success");
                            final UserModel userModel = new UserModel(new Firebase("https://semutdev2.firebaseio.com"), result, awsManager);
                            User updatedUser = new User(emailData, usernameData, fullnameData, birthDateData, file, phoneNumberData);
                            userModel.updateMyUser(updatedUser, new Callback<String>() {
                                @Override
                                public void callback(Error error, String result) {
                                    if (error != null) {
                                        Log.e("userUpdate", "update user failed");
                                    } else {
                                        Log.d("userUpdate", "update user success");
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                    }
                });
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
            userPic.setImageBitmap(bitmap);

            // Do something with the bitmap
            file = new File(imagePath);

            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }
}
