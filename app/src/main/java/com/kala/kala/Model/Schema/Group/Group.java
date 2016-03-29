package com.kala.kala.Model.Schema.Group;

import com.kala.kala.Model.Schema.ModelMap;

/**
 * Created by Kala on 2/29/16.
 */
public class Group extends ModelMap {
    private static String DESCRIPTION = "description";
    private static String PROFILE_PICTURE = "profile_picture";
    private static String CREATED_TIME = "created_time";
    private static String UPDATED_TIME = "updated_time";

    public Group() {

    }

    public Group(String description, String profile_picture, Object created_time, Object updated_time) {
        setDescription(description);
        setProfile_picture(profile_picture);
        setCreated_time(created_time);
        setUpdated_time(updated_time);
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

    public String getProfile_picture() {
        return (String) map.get(PROFILE_PICTURE);
    }

    public void setProfile_picture(String profile_picture) {
        map.put(PROFILE_PICTURE, profile_picture);
    }

    public String getDescription() {
        return (String) map.get(DESCRIPTION);
    }

    public void setDescription(String description) {
        map.put(DESCRIPTION, description);
    }
}
