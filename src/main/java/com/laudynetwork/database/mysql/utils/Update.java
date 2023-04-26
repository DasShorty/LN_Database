package com.laudynetwork.database.mysql.utils;

public class Update {
    private String table = "";
    private UpdateValue value = null;
    private String filter = "";

    public Update() {
    }

    public Update(String table, UpdateValue value, String filter) {
        this.table = table;
        this.value = value;
        this.filter = filter;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public UpdateValue getValue() {
        return value;
    }

    public void setValue(UpdateValue value) {
        this.value = value;
    }
}
