package com.kala.kala.Fragment;

import android.content.SharedPreferences;
import android.database.DataSetObserver;
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

import com.kala.kala.Adapter.CommentArrayAdapter;
import com.kala.kala.LocalModel.Comment;
import com.kala.kala.R;
import com.kala.kala.Util.DatabaseHelper;
import com.kala.kala.Util.SharedPrefs;

import java.util.ArrayList;

public class ReminderCommentsFragment extends Fragment {

    private Button buttonSend;
    private ListView listView;
    private CommentArrayAdapter commentArrayAdapter;
    private EditText commentText;
    private boolean side = false;
    private long reminder_user_id;
    private long reminder_id;
    private long user_id;
    private SharedPreferences sharedPreferences;
    private DatabaseHelper db;
    private ArrayList<Comment> comments = new ArrayList<Comment>();
    private View v;
    private String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_reminder_comments, null);
        db = new DatabaseHelper(getActivity().getApplicationContext());
        reminder_id = ReminderFragment.reminderId;
        username = SharedPrefs.getDefaults("fullname", getContext());
        user_id = db.getUserByFullname(username);
        reminder_user_id = db.getReminderUserId(reminder_id, user_id);
        buttonSend = (Button) v.findViewById(R.id.sendCommentButton);
        commentArrayAdapter = new CommentArrayAdapter(getContext(), comments);
        listView = (ListView) v.findViewById(R.id.commentList);
        listView.setAdapter(commentArrayAdapter);

        commentText = (EditText) v.findViewById(R.id.commentText);
        commentText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendCommentMessage();
                }
                return false;
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendCommentMessage();
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

        return v;
    }

    private boolean sendCommentMessage() {

        // Create New Comment
        Comment newComment = new Comment(side, commentText.getText().toString());
        // Add New Comment to Array Adapter
        commentArrayAdapter.add(newComment);
        // Insert New Comment into Local Database
        db.createComment(newComment, reminder_user_id);
        // Remove Comment Text
        commentText.setText("");

        return true;
    }
}
