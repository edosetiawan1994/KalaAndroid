package com.kala.kala.Fragment;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.kala.kala.Activity.ReminderCommentsActivity;
import com.kala.kala.Activity.ReminderDetailActivity;
import com.kala.kala.Adapter.CommentArrayAdapter;
import com.kala.kala.LocalModel.Comment;
import com.kala.kala.R;

import java.util.ArrayList;


public class ReminderDetailFragment extends Fragment {

    ArrayList<Comment> comments = new ArrayList<Comment>();
    private ListView listView;
    private CommentArrayAdapter commentArrayAdapter;
    private TextView ViewAllText;
    private TextView commentHeaderText;

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
                Intent intent = new Intent(getActivity().getApplicationContext(), ReminderCommentsActivity.class);
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
