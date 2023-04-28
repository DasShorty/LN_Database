package com.laudynetwork.database.mysql.utils;

import java.util.HashMap;
import java.util.Set;

public class Row {
    private final HashMap<String, Object> content = new HashMap<>();

    public Row() {
    }

    public void addcolumn(String name, Object content) {
        this.content.put(name, content);
    }

    public HashMap<String, Object> getColumns() {
        return content;
    }

    public Object get(String key) {
        return content.get(key);
    }

    public Set<String> getKeys() {
        return content.keySet();
    }
}
