package com.kala.kala.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kala.kala.Activity.ContactInfoActivity;
import com.kala.kala.R;

public class ContactDetailFragment extends Fragment implements View.OnClickListener {

    public ContactDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact_detail, null);

        TextView contactNameText = (TextView) v.findViewById(R.id.contactName);
        TextView contactUsernameText = (TextView) v.findViewById(R.id.contactUsername);
        TextView contactEmailText = (TextView) v.findViewById(R.id.contactEmail);
        TextView contactPhoneText = (TextView) v.findViewById(R.id.contactPhone);
        TextView invitation = (TextView) v.findViewById(R.id.inviteKala);

        invitation.setOnClickListener(ContactDetailFragment.this);

        ContactInfoActivity contactInfo = (ContactInfoActivity) getActivity();
        Bundle bundle = contactInfo.getBundle();

        String name = bundle.getString("name");
        String username = bundle.getString("username");
        String email = bundle.getString("email");
        String phone = bundle.getString("phone");

        contactNameText.append(name);
        contactUsernameText.append(username);
        contactEmailText.append(email);
        contactPhoneText.append(phone);

        // Inflate the layout for this fragment
        return v;
    }

    public void onClick(View view) {
        Toast.makeText(getActivity(), "Invitation Sent !", Toast.LENGTH_SHORT).show();
    }
}
