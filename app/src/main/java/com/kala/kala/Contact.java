package com.kala.kala;

/**
 * Created by Edo on 2/12/2016.
 */
public class Contact implements list {

    public int icon;
    public String name;
    public String username;
    public String email;
    public String phone;

    public Contact(){
        super();
    }

    public Contact(int icon,String name, String username, String email, String phone) {
        super();
        this.icon = icon;
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public boolean isHeader(){
        return false;
    }
    
}
