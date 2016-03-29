package com.kala.kala;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

import static android.hardware.SensorManager.getOrientation;

public class AccountFragment extends Fragment {

    TextView usernameText;
    TextView emailText;
    TextView phoneText;
    TextView nameText;

    private ImageView imageView;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, null);
        SharedPreferences pref = MainActivity.sharedPreferences;
        int pictureAccount = pref.getInt("picture", R.drawable.profilepicture);
        String usernameAccount = pref.getString("username", "no username found");
        String emailAccount = pref.getString("Email", "no email found");
        String phoneAccount = pref.getString("phone", "no phone found");
        String nameAccount = pref.getString("name", "no name found");

        imageView = (ImageView) v.findViewById(R.id.profPic);
        imageView.setImageResource(pictureAccount);

        usernameText = (TextView) v.findViewById(R.id.editUsername);
        usernameText.setText(usernameAccount);

        emailText = (TextView) v.findViewById(R.id.editEmail);
        emailText.setText(emailAccount);

        phoneText = (TextView) v.findViewById(R.id.editPhone);
        phoneText.setText(phoneAccount);

        nameText = (TextView) v.findViewById(R.id.editFullName);
        nameText.setText(nameAccount);

        return v;
    }
}