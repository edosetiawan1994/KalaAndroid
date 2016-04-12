package com.kala.kala.Fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.kala.kala.Activity.ContactInfoActivity;
import com.kala.kala.Adapter.ContactListAdapter;
import com.kala.kala.LocalModel.Contact;
import com.kala.kala.LocalModel.Header.ContactHeader;
import com.kala.kala.R;
import com.kala.kala.Util.SharedPrefs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ContactFragment extends ListFragment {
    public final static String EXTRA_NAME = "contact_name";
    public final static String EXTRA_USERNAME = "contact_username";
    public final static String EXTRA_EMAIL = "contact_email";
    public final static String EXTRA_PHONE = "contact_phone";

    public SwipeRefreshLayout swipeLayout;
    ArrayList<Contact> contacts = new ArrayList<>();
    private Handler handler = new Handler();

    private final Runnable refreshing = new Runnable() {
        public void run() {
            try {
                // TODO : isRefreshing should be attached to your data request status
                if (swipeLayout.isRefreshing()) {
                    // re run the verification after 1 second
                    handler.postDelayed(this, 1000);
                    Log.d("swipe layout", "is refreshing");
                } else {
                    // stop the animation after the data is fully loaded
                    swipeLayout.setRefreshing(false);
                    // TODO : update your list with the new data
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create self contact
        // initialize shared preference
        String selfName = SharedPrefs.getDefaults("fullname", getActivity().getApplicationContext());
        String selfUsername = SharedPrefs.getDefaults("username", getActivity().getApplicationContext());
        String selfEmail = SharedPrefs.getDefaults("email", getActivity().getApplicationContext());
        String selfPhone = SharedPrefs.getDefaults("phone", getActivity().getApplicationContext());
        // end of self contact creation

        fetchContacts();

        Collections.sort(contacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact lhs, Contact rhs) {
                return lhs.name.toString().compareToIgnoreCase(rhs.name.toString());
            }
        });

        contacts.add(0, new ContactHeader("Profile"));
        contacts.add(1, new Contact(R.drawable.profilepicture, selfName, selfUsername, selfEmail, selfPhone));
        contacts.add(2, new ContactHeader("Friends"));
        contacts.add(3, new ContactHeader("Contacts"));

        ContactListAdapter adapter = new ContactListAdapter(getContext(), contacts);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact, container, false);

        swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
        // the refresh listener. this would be called when the layout is pulled down
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // get the new data from you data source
                // TODO : request data here
                // our swipeRefreshLayout needs to be notified when the data is returned in order for it to stop the animation
                handler.post(refreshing);
            }
        });
        return v;
    }

    public void fetchContacts() {

        int contact_icon = R.drawable.profilepicture;
        String contact_name = "\"Name\"";
        String contact_username = "Invite To Kala";
        String contact_phone = "\"Phone\"";
        String contact_email = "\"Email\"";

        String phoneNumber;
        String email;

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

        Uri EmailCONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;

        ContentResolver contentResolver = getActivity().getContentResolver();

        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);

        // Loop for every contact in the phone
        if (cursor.getCount() > 0) {
            Log.e("cursor count", "" + cursor.getCount());
            while (cursor.moveToNext()) {
                String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    contact_name = name;
                    // Query and loop for every phone number of the contact
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);
                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        contact_phone = phoneNumber;
                    }
                    phoneCursor.close();
                    // Query and loop for every email of the contact
                    Cursor emailCursor = contentResolver.query(EmailCONTENT_URI, null, EmailCONTACT_ID + " = ?", new String[]{contact_id}, null);
                    if (emailCursor.moveToNext() == true) {
                        email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
                        contact_email = email;
                    } else {
                        contact_email = "no email found";
                    }
                    emailCursor.close();
                    contacts.add(new Contact(contact_icon, contact_name, contact_username, contact_email, contact_phone));
                }
            }
        }
    }

    @Override
    public void onListItemClick(ListView listview, View view, int position, long id) {
        super.onListItemClick(listview, view, position, id);
        Toast.makeText(getActivity(), "Contact " + (position + 1) + " Clicked !", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity().getApplicationContext(), ContactInfoActivity.class);

        String contactName = contacts.get(position).name;
        Log.d("System", "name = " + contactName);
        String contactUsername = contacts.get(position).username;
        Log.d("System", "username = " + contactUsername);
        String contactEmail = contacts.get(position).email;
        Log.d("System", "email = " + contactEmail);
        String contactPhone = contacts.get(position).phone;
        Log.d("System", "phone = " + contactPhone);

        intent.putExtra(EXTRA_NAME, contactName);
        intent.putExtra(EXTRA_USERNAME, contactUsername);
        intent.putExtra(EXTRA_EMAIL, contactEmail);
        intent.putExtra(EXTRA_PHONE, contactPhone);

        startActivity(intent);
    }
}
