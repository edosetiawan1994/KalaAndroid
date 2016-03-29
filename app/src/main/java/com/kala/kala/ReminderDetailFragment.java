package com.kala.kala;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;


public class ReminderDetailFragment extends Fragment {

    private ListView listView;
    private CommentArrayAdapter commentArrayAdapter;
    private TextView ViewAllText;
    private TextView commentHeaderText;

    ArrayList<Comment> comments = new ArrayList<Comment>();

    public ReminderDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reminder_detail, null);

        listView = (ListView) v.findViewById(R.id.commentList);

        commentArrayAdapter = new CommentArrayAdapter(getActivity().getApplicationContext(), comments);
        listView.setAdapter(commentArrayAdapter);

        commentHeaderText = (TextView) v.findViewById(R.id.commentHeader);
//        commentHeaderText.append(" . " + commentArrayAdapter.getCount() + " comments");

        ViewAllText = (TextView) v.findViewById(R.id.commentViewAll);
        ViewAllText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(),ReminderCommentsActivity.class);
                startActivity(intent);
            }
        });
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(commentArrayAdapter);

        //to scroll the list view to bottom on data change
        commentArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(commentArrayAdapter.getCount() - 1);
            }
        });

        TextView reminderParticipantsText = (TextView) v.findViewById(R.id.reminderDetailParticipants);
        TextView reminderTitleText = (TextView) v.findViewById(R.id.reminderDetailTitle);
        TextView reminderDateText = (TextView) v.findViewById(R.id.reminderDetailDueDate);
        TextView reminderNoteText = (TextView) v.findViewById(R.id.reminderDetailNote);

        ReminderDetailActivity reminderInfo = (ReminderDetailActivity) getActivity();
        Bundle bundle = reminderInfo.getBundle();

        String reminderIcon = bundle.getString("icon");
        String reminderParticipants = bundle.getString("participants");
        String reminderTitle = bundle.getString("title");
        String reminderDate = bundle.getString("date");
        String reminderTime = bundle.getString("time");
        String reminderNote = bundle.getString("note");

        reminderParticipantsText.setText(reminderParticipants);
        reminderTitleText.setText(reminderTitle);
        reminderDateText.setText(reminderDate);
        reminderNoteText.setText(reminderNote);

        return v;
    }
}
