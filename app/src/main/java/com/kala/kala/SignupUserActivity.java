package com.kala.kala;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SignupUserActivity extends AppCompatActivity {

    Bundle extras;
    Button doneButton, backButton;
    EditText usernameEditText;
    ImageView userPic;
    InputMethodManager in;
    int SELECT_PHOTO = 12345;
    Intent intent;
    RelativeLayout layout;
    String emailData, passwordData, fullnameData, phoneNumberData, birthDateData, usernameData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_user);

        extras = getIntent().getExtras();
        if(extras != null) {
            emailData = extras.getString("email");
            passwordData = extras.getString("password");
            fullnameData = extras.getString("fullname");
            phoneNumberData = extras.getString("phonenumber");
            birthDateData = extras.getString("birthdate");
        }

        findViewById();

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent ev) {
                in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return false;
            }
        });
    }

    public void findViewById() {
        layout = (RelativeLayout) findViewById(R.id.signupUser_Layout);
        usernameEditText = (EditText) findViewById(R.id.signupUser_UsernameHint);
        userPic = (ImageView) findViewById(R.id.signupUser_Pic);
        doneButton = (Button) findViewById(R.id.signupUser_DoneButton);
        backButton = (Button) findViewById(R.id.signupUser_BackButton);
    }

    public void userPictureOnClick (View view) {
        userPic.setImageBitmap(null);
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    public void doneButtonOnClick (View view) {
        usernameData = usernameEditText.getText().toString().trim();
        // Check username duplicate in server
//        if (usernameData does not have duplicates) {
            Toast.makeText(getApplicationContext(), "Signed Up", Toast.LENGTH_SHORT).show();
            Log.e("data", passwordData + " " + fullnameData + " " + phoneNumberData + " " + birthDateData + " " + usernameData);
//        intent = new Intent(getApplicationContext(), LoginActivity.class);
//        intent.putExtra("email",emailData);
//        intent.putExtra("password",passwordData);
//        intent.putExtra("fullname",fullnameData);
//        intent.putExtra("phonenumber",phoneNumberData);
//        intent.putExtra("birthdate",birthDateData);
//        intent.putExtra("username",usernameData);
//        startActivity(intent);
//        } else {
//            Toast.makeText(getApplicationContext(), "Username Used", Toast.LENGTH_SHORT).show();
//        }
    }

    public void backButtonOnClick (View view) {
        onBackPressed();
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
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
            userPic.setImageBitmap(bitmap);

            // Do something with the bitmap

            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }
}
