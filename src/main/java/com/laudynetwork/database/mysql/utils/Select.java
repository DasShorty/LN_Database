package com.laudynetwork.database.mysql.utils;

public class Select {
    private String table = "";
    private String columns = "";
    private String filter = "";

    public Select() {
    }

    public Select(String table, String columns, String filter) {
        this.table = table;
        this.columns = columns;
        this.filter = filter;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getColumns() {
        if (columns == null) {
            return "*";
        } else {
            return columns;
        }
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }
}
