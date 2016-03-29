package com.kala.kala;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

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

    public void onClick(View view){
        Toast.makeText(getActivity(), "Invitation Sent !", Toast.LENGTH_SHORT).show();
    }
}
