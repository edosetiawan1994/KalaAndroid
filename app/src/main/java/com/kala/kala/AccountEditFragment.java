package com.kala.kala;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AccountEditFragment extends Fragment {

    public static EditText usernameText;
    public static EditText emailText;
    public static EditText phoneText;
    public static EditText nameText;

    private static Bitmap Image = null;
    private ImageView imageView;
    private static final int GALLERY = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account_edit, null);
        SharedPreferences pref = MainActivity.sharedPreferences;
        int pictureAccount = pref.getInt("picture", R.drawable.profilepicture);
        String usernameAccount = pref.getString("username", "no username found");
        String emailAccount = pref.getString("email", "no email found");
        String phoneAccount = pref.getString("phone", "no phone found");
        String nameAccount = pref.getString("name", "no name found");

        imageView = (ImageView) v.findViewById(R.id.profPic);
        imageView.setImageResource(pictureAccount);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Profile Picture Changed !", Toast.LENGTH_SHORT).show();
                imageView = (ImageView) getView().findViewById(R.id.profPic);
                imageView.setImageBitmap(null);
                if (Image != null)
                    Image.recycle();
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY);
            }
        });

        usernameText = (EditText) v.findViewById(R.id.editUsername);
        usernameText.setText(usernameAccount);

        emailText = (EditText) v.findViewById(R.id.editEmail);
        emailText.setText(emailAccount);

        phoneText = (EditText) v.findViewById(R.id.editPhone);
        phoneText.setText(phoneAccount);

        nameText = (EditText) v.findViewById(R.id.editFullName);
        nameText.setText(nameAccount);

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == GALLERY && resultCode == getActivity().RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getActivity().getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            Log.e("Image Path", imagePath);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
            imageView.setImageBitmap(bitmap);
            imageView.getLayoutParams().height = 800;
            imageView.getLayoutParams().width = 800;

            // Do something with the bitmap


            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }
}
