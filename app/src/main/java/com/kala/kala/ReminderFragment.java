package com.kala.kala;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ReminderFragment extends ListFragment{
    public final static String EXTRA_ID = "reminder_id";
    public final static String EXTRA_TITLE = "reminder_title";
    public final static String EXTRA_DUE_DATE = "reminder_due_date";
    public final static String EXTRA_PARTICIPANT = "reminder_participant";
    public final static String EXTRA_NOTE = "reminder_note";
    public static long reminderId;

    ArrayList<Reminder> reminders = new ArrayList<Reminder>();
    ArrayList<Note> notes = new ArrayList<Note>();
    ArrayList<User> users = new ArrayList<User>();
    ArrayList<Comment> comments = new ArrayList<Comment>();

    DatabaseHelper db;

    public ReminderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DatabaseHelper(getActivity().getApplicationContext());
        // List all Reminder
        List<Reminder> allReminders = db.getAllReminders();
        Log.d("Reminder : ", "id, title, dueDate, updatedTime, CreatedTime");
        for (Reminder reminder : allReminders) {
            Log.d("Reminder : ",  reminder.getId() + ", " + reminder.getTitle() + ", " +  reminder.getDueDate() + ", " +  reminder.getUpdatedTime() + ", " +  reminder.getCreatedTime());
            reminders.add(new Reminder(reminder.getId(), reminder.getTitle(), reminder.getDueDate()));
        }
        // List all Note
        List<Note> allNotes = db.getAllNotes();
        Log.d("Note : ", "id, content");
        for (Note note : allNotes) {
            Log.d("Note : ",  note.getId() + ", " + note.getContent());
            notes.add(new Note(note.getContent()));
        }
        // List all User
        List<User> allUsers = db.getAllUsers();
        Log.d("User : ", "id, fullname, username, email, phone");
        for (User user : allUsers) {
            Log.d("User :",  user.getId() + ", " + user.getFullname() + ", " + user.getUsername() + ", " + user.getEmail() + ", " + user.getPhone_number());
            users.add(new User(user.getFullname(),user.getUsername(),user.getEmail(),user.getPhone_number(),"null","null",0));
        }
        // List all Comment
        List<Comment> allComments = db.getAllComments();
        Log.d("Comment : ", "id, message");
        for (Comment comment : allComments) {
            Log.d("Comment :",  comment.getId() + ", " + comment.getMessage());
            comments.add(new Comment(true,comment.getMessage()));
        }
        db.closeDB();

        ReminderListAdapter adapter = new ReminderListAdapter(getContext(), reminders);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_reminder, container, false);
    }

    @Override
    public void onListItemClick(ListView listview, View view, int position,long id) {
        super.onListItemClick(listview, view, position, id);
        Intent intent = new Intent(getActivity().getApplicationContext(),ReminderDetailActivity.class);

        reminderId = reminders.get(position).getId();
        String reminderTitle = reminders.get(position).title;
        String reminderDueDate = reminders.get(position).due_date;
        String reminderParticipant = users.get(position).fullname;
        String reminderNote = notes.get(position).content;

        intent.putExtra(EXTRA_ID,reminderId);
        intent.putExtra(EXTRA_TITLE, reminderTitle);
        intent.putExtra(EXTRA_DUE_DATE, reminderDueDate);
        intent.putExtra(EXTRA_PARTICIPANT, reminderParticipant);
        intent.putExtra(EXTRA_NOTE, reminderNote);

        Toast.makeText(getActivity(), "Reminder " + reminderId + " Clicked !", Toast.LENGTH_SHORT).show();

        startActivity(intent);
    }
}
