package com.kala.kalamodel_android_library.Model.Schema.User;

import com.kala.kalamodel_android_library.Model.Schema.ModelMap;

/**
 * Created by Kala on 2/29/16.
 */
public class Phone extends ModelMap {
    private static String USER_ID = "user_id";
    private static String CREATED_TIME = "created_time";

    public Phone() {
    }

    public Phone(String user_id, Object created_time) {
        setUser_id(user_id);
        setCreated_time(created_time);
    }

    public String getUser_id() {

        return (String) map.get(USER_ID);
    }

    public void setUser_id(String user_id) {
        map.put(USER_ID, user_id);
    }

    public Object getCreated_time() {
        return map.get(CREATED_TIME);
    }

    public void setCreated_time(Object created_time) {
        map.put(CREATED_TIME, created_time);
    }
}
