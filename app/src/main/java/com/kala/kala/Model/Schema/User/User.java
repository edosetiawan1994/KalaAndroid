package com.kala.kala.Model.Schema.User;

import com.kala.kala.Model.Schema.ModelMap;

/**
 * Created by Kala on 2/29/16.
 */
public class User extends ModelMap {

    private static String EMAIL = "email";
    private static String USERNAME = "username";
    private static String FULLNAME = "fullname";
    private static String BIRTHDATE = "birthdate";
    private static String PROFILE_PICTURE = "profile_picture";
    private static String PHONE = "phone";
    private static String CREATED_TIME = "created_time";
    private static String UPDATED_TIME = "updated_time";

    public User() {

    }

    public User(String email, String username, String fullname, Integer birthdate, Object profile_picture, String phone) {
        setEmail(email);
        setUsername(username);
        setFullname(fullname);
        setBirthdate(birthdate);
        setProfile_picture(profile_picture);
        setPhone(phone);
    }

    public String getEmail() {

        return (String) map.get(EMAIL);
    }

    public void setEmail(String email) {
        map.put(EMAIL, email);
    }

    public String getUsername() {
        return (String) map.get(USERNAME);
    }

    public void setUsername(String username) {
        map.put(USERNAME, username);
    }

    public String getFullname() {
        return (String) map.get(FULLNAME);
    }

    public void setFullname(String fullname) {
        map.put(FULLNAME, fullname);
    }

    public Integer getBirthdate() {
        return (Integer) map.get(BIRTHDATE);
    }

    public void setBirthdate(Integer birthdate) {
        map.put(BIRTHDATE, birthdate);
    }

    public Object getProfile_picture() {
        return (Object) map.get(PROFILE_PICTURE);
    }

    public void setProfile_picture(Object profile_picture) {
        map.put(PROFILE_PICTURE, profile_picture);
    }

    public String getPhone() {
        return (String) map.get(PHONE);
    }

    public void setPhone(String phone) {
        map.put(PHONE, phone);
    }

    public Object getCreated_time() {
        return map.get(CREATED_TIME);
    }

    public void setCreated_time(Object created_time) {
        map.put(CREATED_TIME, created_time);
    }

    public Object getUpdated_time() {
        return map.get(UPDATED_TIME);
    }

    public void setUpdated_time(Object updated_time) {
        map.put(UPDATED_TIME, updated_time);
    }
}
