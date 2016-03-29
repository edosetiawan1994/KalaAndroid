package com.kala.kala;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ContactListAdapter extends ArrayAdapter<Contact> {

    private Context context;
    private ArrayList<Contact> contacts;
    private LayoutInflater vi;

    public ContactListAdapter(Context context,ArrayList<Contact> contacts) {
        super(context,0, contacts);
        this.context = context;
        this.contacts = contacts;
        vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final list i = contacts.get(position);

        if(i != null) {
            if(i.isHeader()) {
                ContactHeader header = (ContactHeader)i;
                row = vi.inflate(R.layout.contact_header, null);

                row.setOnClickListener(null);
                row.setOnLongClickListener(null);
                row.setLongClickable(false);

                final TextView headerTitle = (TextView) row.findViewById(R.id.contactHeader);
                headerTitle.setText(header.getTitle());
            } else {
                Contact contact = (Contact)i;
                row = vi.inflate(R.layout.contact_row, null);
                final ImageView icon = (ImageView)row.findViewById(R.id.icon);
                final TextView name = (TextView)row.findViewById(R.id.contactName);
                final TextView username = (TextView)row.findViewById(R.id.contactUsername);
                username.setTextColor(Color.RED);

                if (icon != null)
                    icon.setImageResource(contact.icon);
                if (name != null)
                    name.setText(contact.name);
                if (username != null)
                    username.setText(contact.username);
            }
        }
        return row;
    }
}