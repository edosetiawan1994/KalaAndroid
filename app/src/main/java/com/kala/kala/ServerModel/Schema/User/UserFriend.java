package com.kala.kala.ServerModel.Schema.User;

import com.kala.kala.ServerModel.Schema.ModelMap;

/**
 * Created by Kala on 2/29/16.
 */
public class UserFriend extends ModelMap {
    private static String TYPE = "type";
    private static String CREATED_TIME = "created_time";

    public UserFriend() {
    }

    public UserFriend(Integer type, Object created_time) {
        setType(type);
        setCreated_time(created_time);
    }

    public Integer getType() {
        return (Integer) map.get(TYPE);
    }

    public void setType(Integer type) {
        map.put(TYPE, type);
    }

    public Object getCreated_time() {
        return map.get(CREATED_TIME);
    }

    public void setCreated_time(Object created_time) {
        map.put(CREATED_TIME, created_time);
    }
}
