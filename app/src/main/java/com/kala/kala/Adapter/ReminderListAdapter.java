package com.kala.kala.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kala.kala.LocalModel.Header.ReminderHeader;
import com.kala.kala.LocalModel.Note;
import com.kala.kala.LocalModel.Reminder;
import com.kala.kala.LocalModel.User;
import com.kala.kala.R;
import com.kala.kala.Util.DatabaseHelper;
import com.kala.kala.Util.list;

import java.util.ArrayList;

public class ReminderListAdapter extends ArrayAdapter<Reminder> {

    DatabaseHelper db;
    private Context context;
    private ArrayList<Reminder> reminders;
    private LayoutInflater vi;

    public ReminderListAdapter(Context context, ArrayList<Reminder> reminders) {
        super(context, 0, reminders);
        this.context = context;
        this.reminders = reminders;
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final list i = reminders.get(position);
        db = new DatabaseHelper(getContext());

        if (i != null) {
            if (i.isHeader()) {
                ReminderHeader header = (ReminderHeader) i;
                row = vi.inflate(R.layout.reminder_header, null);

                row.setOnClickListener(null);
                row.setOnLongClickListener(null);
                row.setLongClickable(false);

                final TextView headerDate = (TextView) row.findViewById(R.id.reminderHeaderDate);
                final TextView headerDay = (TextView) row.findViewById(R.id.reminderHeaderDay);
                headerDate.setText(header.getDate());
                headerDay.setText(header.getDay());
            } else {
                Reminder reminder = (Reminder) i;

                row = vi.inflate(R.layout.reminder_row, null);
                final TextView title = (TextView) row.findViewById(R.id.reminderTitle);
                final TextView user = (TextView) row.findViewById(R.id.reminderName);
                final TextView dueDate = (TextView) row.findViewById(R.id.reminderTime);
                final TextView content = (TextView) row.findViewById(R.id.reminderNote);

                Note reminderNote = db.getNoteByReminder((long) reminder.id);
                User reminderUser = db.getUserByReminder((long) reminder.id);

                if (user != null)
                    user.setText(reminderUser.fullname);
                if (title != null)
                    title.setText(reminder.title);
                if (dueDate != null)
                    dueDate.setText(reminder.due_date.substring(reminder.due_date.length() - 5));
                if (content != null)
                    content.setText(reminderNote.content);
            }
        }
        return row;
    }
}