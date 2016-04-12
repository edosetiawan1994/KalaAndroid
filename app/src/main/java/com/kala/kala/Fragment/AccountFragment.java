package com.kala.kala.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kala.kala.R;
import com.kala.kala.Util.SharedPrefs;

public class AccountFragment extends Fragment {

    TextView usernameText;
    TextView emailText;
    TextView phoneText;
    TextView nameText;
    TextView birthdateText;

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

        String usernameAccount = SharedPrefs.getDefaults("username", getActivity().getApplicationContext());
        String emailAccount = SharedPrefs.getDefaults("email", getActivity().getApplicationContext());
        String phoneAccount = SharedPrefs.getDefaults("phone", getActivity().getApplicationContext());
        String nameAccount = SharedPrefs.getDefaults("fullname", getActivity().getApplicationContext());
        String birthdateAccount = SharedPrefs.getDefaults("birthdate", getActivity().getApplicationContext());

        Log.d("birthdate", birthdateAccount + " ");

        usernameText = (TextView) v.findViewById(R.id.editUsername);
        usernameText.setText(usernameAccount);

        emailText = (TextView) v.findViewById(R.id.editEmail);
        emailText.setText(emailAccount);

        phoneText = (TextView) v.findViewById(R.id.editPhone);
        phoneText.setText(phoneAccount);

        nameText = (TextView) v.findViewById(R.id.editFullName);
        nameText.setText(nameAccount);

        birthdateText = (TextView) v.findViewById(R.id.editBirthday);
        birthdateText.setText(birthdateAccount);

        return v;
    }
}