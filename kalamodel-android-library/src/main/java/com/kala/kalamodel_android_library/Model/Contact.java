package com.kala.kalamodel_android_library.Model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.provider.ContactsContract.*;
import static android.provider.ContactsContract.CommonDataKinds.*;

/**
 * Created by Kala on 3/22/16.
 */
public class Contact {
    private Context context;
    private static Contact instance;

    Contact(Context context) {
        this.context = context;
    }

    public static void setInstance(Context context) {
        if (instance == null) {
            instance = new Contact(context);
        }
    }

    public static Contact getInstance() {
        return instance;
    }

    public List<String> getAllPhoneNumber() {
        Uri contentUri = Data.CONTENT_URI;
        String[] projection = new String[]{//Data._ID,
                Phone.NUMBER,
                //Phone.TYPE,
                //Phone.LABEL
        };
        String selectionClause = Data.MIMETYPE + "='" + Phone.CONTENT_ITEM_TYPE + "'";
        String[] selectionArg = null;
        String sortOrder = null;
        Cursor cursor = context.getContentResolver().query(contentUri, projection, selectionClause, selectionArg, sortOrder);
        if (cursor == null) {
            Log.e("Contact.getAllPhoneNum", "failed");
            return null;
        } else {
            int phoneIndex = cursor.getColumnIndex(Phone.NUMBER);
            List<String> phoneList = new ArrayList<String>();
            Log.d("Contact.getAllPhoneNum", String.format("success, found %d phone number", cursor.getCount()));
            while (cursor.moveToNext()) {
                String phone = cursor.getString(phoneIndex);
                phone = sanitizePhoneNumber(phone);
                phoneList.add(phone);
            }
            cursor.close();
            return phoneList;
        }
    }

    public static String sanitizePhoneNumber(String phone) {
        phone = phone.replace(" ", "");
        phone = phone.replace("(", "");
        phone = phone.replace(")", "");
        phone = phone.replace("-", "");
        if (phone.substring(0, 1).equals("0")) {
            phone = "+62" + phone.substring(1);
        }
        return phone;
    }
}
