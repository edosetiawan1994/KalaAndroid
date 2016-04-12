package com.kala.kala.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kala.kala.LocalModel.Comment;
import com.kala.kala.R;
import com.kala.kala.Util.SharedPrefs;
import com.kala.kala.Util.list;

import java.util.ArrayList;

public class CommentArrayAdapter extends ArrayAdapter<Comment> {

    SharedPreferences sharedPreferences;
    private Context context;
    private ArrayList<Comment> comments;
    private LayoutInflater vi;

    public CommentArrayAdapter(Context context, ArrayList<Comment> comments) {
        super(context, 0, comments);
        this.context = context;
        this.comments = comments;
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final list i = comments.get(position);

        if (i != null) {
            if (i.isHeader()) {
                //do nothing
            } else {
                Comment comment = (Comment) i;
                row = vi.inflate(R.layout.comment_row, null);
                final TextView commentText = (TextView) row.findViewById(R.id.singleComment);
                TextView commentUserName = (TextView) row.findViewById(R.id.comment_username);

                if (commentText != null)
                    commentText.setText(comment.message);
                if (commentUserName != null)
                    commentUserName.setText(SharedPrefs.getDefaults("fullname",getContext()));
            }
        }
        return row;
    }
}