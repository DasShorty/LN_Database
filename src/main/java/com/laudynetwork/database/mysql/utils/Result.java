package com.laudynetwork.database.mysql.utils;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private final List<Row> rows = new ArrayList<>();

    public Result() {
    }

    public void addrow(Row row) {
        rows.add(row);
    }

    public List<Row> getRows() {
        return rows;
    }
}
