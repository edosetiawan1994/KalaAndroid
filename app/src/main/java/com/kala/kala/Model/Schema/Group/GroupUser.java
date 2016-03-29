package com.kala.kala.Model.Schema.Group;

import com.kala.kala.Model.Schema.ModelMap;

/**
 * Created by Kala on 2/29/16.
 */
public class GroupUser extends ModelMap {
    private static String TYPE = "type";
    private static String CREATED_TIME = "created_time";
    private static String UPDATED_TIME = "updated_time";

    public GroupUser() {

    }

    public GroupUser(Integer type, Object created_time, Object updated_time) {
        setType(type);
        setCreated_time(created_time);
        setUpdated_time(updated_time);
    }

    public Object getCreated_time() {
        return map.get(CREATED_TIME);
    }

    public void setCreated_time(Object created_time) {
        map.put(CREATED_TIME,created_time);
    }

    public Object getUpdated_time() {
        return map.get(UPDATED_TIME);
    }

    public void setUpdated_time(Object updated_time) {
        map.put(UPDATED_TIME, updated_time);
    }

    public Integer getType() {
        return (Integer) map.get(TYPE);
    }

    public void setType(Integer type) {
        map.put(TYPE, type);
    }
}
