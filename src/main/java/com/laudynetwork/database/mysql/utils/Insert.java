package com.laudynetwork.database.mysql.utils;

import java.util.List;

public class Insert {
    private String table = "";
    private String columns = "";
    private Object[] data = null;

    public Insert(String table, String columns, Object... data) {
        this.table = table;
        this.columns = columns;
        this.data = data;
    }

    public Insert() {
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Object[] getData() {
        return data;
    }

    public void setData(String... data) {
        this.data = data;
    }

    public void setData(List<Object> data) {
        this.data = data.toArray();
    }
}