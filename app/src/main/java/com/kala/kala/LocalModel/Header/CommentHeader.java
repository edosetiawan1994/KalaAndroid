package com.kala.kala.LocalModel.Header;

import com.kala.kala.LocalModel.Comment;
import com.kala.kala.Util.list;

/**
 * Created by Edo on 3/7/2016.
 */
public class CommentHeader extends Comment implements list {
    private final String title;

    public CommentHeader(String title) {
        super();
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public boolean isHeader() {
        return true;
    }
}
