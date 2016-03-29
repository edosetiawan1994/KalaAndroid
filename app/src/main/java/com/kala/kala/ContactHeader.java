package com.kala.kala;

/**
 * Created by Edo on 2/12/2016.
 */
public class ContactHeader extends Contact implements list {

    private final String title;

    public ContactHeader(String title) {
        super();
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public boolean isHeader() {
        return true;
    }
}