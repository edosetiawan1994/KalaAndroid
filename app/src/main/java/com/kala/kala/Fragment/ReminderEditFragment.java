package com.kala.kala.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.kala.kala.Activity.ReminderEditActivity;
import com.kala.kala.R;

public class ReminderEditFragment extends Fragment {

    EditText editTitleText;
    TextView editUserText;
    TextView editDateText;
    EditText editNoteText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reminder_edit, container, false);

        ReminderEditActivity reminderEdit = (ReminderEditActivity) getActivity();
        Bundle bundle = reminderEdit.getBundle();

        String titleText = bundle.getString("title");
        String participantText = bundle.getString("participants");
        String dateText = bundle.getString("date");
        String noteText = bundle.getString("note");

        editTitleText = (EditText) v.findViewById(R.id.reminderEditTitle);
        editTitleText.setText(titleText);
        editUserText = (TextView) v.findViewById(R.id.reminderEditParticipants);
        editUserText.setText(participantText);
        editDateText = (TextView) v.findViewById(R.id.reminderEditDate);
        editDateText.setText(dateText);
        editNoteText = (EditText) v.findViewById(R.id.reminderEditNote);
        editNoteText.setText(noteText);

        return v;
    }
}
