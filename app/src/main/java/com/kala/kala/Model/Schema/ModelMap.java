package com.kala.kala.Model.Schema;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kala on 3/16/16.
 */
public class ModelMap {

    protected Map<String, Object> map;

    public ModelMap() {
        map = new HashMap<String, Object>();
    }

    public Map<String, Object> toMap() {
        return map;
    }
}
