package com.laudynetwork.database.mysql;

import com.laudynetwork.database.mysql.utils.*;
import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.SneakyThrows;
import lombok.val;

import java.sql.*;

public class MySQL {
    private final MysqlDataSource dataSource;

    @SneakyThrows
    public MySQL(String host, String user, String pw, String db) {
        this.dataSource = new MysqlDataSource();

        this.dataSource.setServerName(host);
        this.dataSource.setPort(3306);
        this.dataSource.setDatabaseName(db);
        this.dataSource.setUser(user);
        this.dataSource.setPassword(pw);
        this.dataSource.setAllowMultiQueries(true);
        System.out.println("Connection ready to create");

    }

    public Connection checkConnectOrCreateNew() {
        return this.createConnection();
    }

    @SneakyThrows
    private Connection createConnection() {
        return this.dataSource.getConnection();
    }

    // Database Interaction
    public boolean tableInsert(String table, String columns, Object... data) {
        String sqldata = "";
        int i = 0;
        for (Object d : data) {
            if (d instanceof String) {
                sqldata = sqldata + "'" + d + "'";
            } else {
                sqldata = sqldata + d;
            }

            i++;
            if (i != data.length) {
                sqldata = sqldata + ", ";
            }
        }

        String sql = "INSERT INTO " + table + " (" + columns + ") VALUES (" + sqldata + ");";
        Statement stmt = null;
        try {
            stmt = this.checkConnectOrCreateNew().createStatement();
            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public boolean tableInsert(Insert... builders) {
        String sql = "";
        for (Insert b : builders) {
            String sqldata = "";
            int i = 0;
            for (Object d : b.getData()) {
                if (d instanceof String) {
                    sqldata = sqldata + "'" + d + "'";
                } else {
                    sqldata = sqldata + d;
                }

                i++;
                if (i != b.getData().length) {
                    sqldata = sqldata + ", ";
                }
            }


            sql = sql + "INSERT INTO " + b.getTable() + " (" + b.getColumns() + ") VALUES (" + sqldata + "); ";

        }
        Statement stmt = null;
        try {
            stmt = this.checkConnectOrCreateNew().createStatement();
            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public boolean rowUpdate(String table, String filter, UpdateValue... values) {
        for (UpdateValue value : values) {
            String change = "";
            int i = 0;
            for (String key : value.getKeys()) {
                if (value.get(key) instanceof String) {
                    change = change + key + " = '" + value.get(key) + "'";
                } else {
                    change = change + key + " = " + value.get(key);
                }

                i++;
                if (i != value.getKeys().size()) {
                    change = change + ", ";
                }
            }
            String sql = "UPDATE " + table + " SET " + change + " WHERE " + filter + ";";
            Statement stmt = null;
            try {
                stmt = this.checkConnectOrCreateNew().createStatement();
                stmt.execute(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return true;
    }

    public boolean rowUpdate(Update... builders) {
        String sql = "";
        for (Update u : builders) {
            String change = "";
            int i = 0;
            for (String key : u.getValue().getKeys()) {
                if (u.getValue().get(key) instanceof String) {
                    change = change + key + " = '" + u.getValue().get(key) + "'";
                } else {
                    change = change + key + " = " + u.getValue().get(key);
                }


                i++;
                if (i != u.getValue().getKeys().size()) {
                    change = change + ", ";
                }
            }
            sql = sql + "UPDATE " + u.getTable() + " SET " + change + " WHERE " + u.getFilter() + "; ";
        }
        Statement stmt = null;
        try {
            stmt = this.checkConnectOrCreateNew().createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public Result rowSelect(String table, String columns, String filter) {
        if (columns == null || columns.equals("")) {
            columns = "*";
        }
        String sql = "SELECT " + columns + " FROM " + table;
        if (filter != null && !filter.equals("")) {
            sql = sql + " WHERE " + filter;
        }
        sql = sql + ";";

        Statement stmt;
        ResultSet res;
        try {
            stmt = this.checkConnectOrCreateNew().createStatement();
            res = stmt.executeQuery(sql);
            ResultSetMetaData resmeta = res.getMetaData();
            Result result = new Result();
            while (res.next()) {
                Row row = new Row();
                int i = 1;
                boolean bound = true;
                while (bound) {
                    try {
                        row.addColumn(resmeta.getColumnName(i), res.getObject(i));
                    } catch (SQLException e) {
                        bound = false;
                    }

                    i++;
                }
                result.addrow(row);
            }

            stmt.close();

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            return new Result();
        }
    }

    public boolean rowExist(Select s) {
        Result result = rowSelect(s);
        return result.getRows().size() > 0;
    }

    public Result rowSelect(Select s) {
        String sql = "";
        String columns;
        String lsql;
        if (s.getColumns() == null || s.getColumns().equals("")) {
            columns = "*";
        } else {
            columns = s.getColumns();
        }
        lsql = "SELECT " + columns + " FROM " + s.getTable();
        if (s.getFilter() != null && !s.getFilter().equals("")) {
            lsql = lsql + " WHERE " + s.getFilter();
        }
        lsql = lsql + "; ";
        sql = sql + lsql;

        Statement stmt;
        ResultSet res;
        try {
            stmt = this.checkConnectOrCreateNew().createStatement();
            res = stmt.executeQuery(sql);
            ResultSetMetaData resmeta = res.getMetaData();
            Result result = new Result();
            while (res.next()) {
                Row row = new Row();
                int i = 1;
                boolean bound = true;
                while (bound) {
                    try {
                        row.addColumn(resmeta.getColumnName(i), res.getObject(i));
                    } catch (SQLException e) {
                        bound = false;
                    }
                    i++;
                }
                result.addrow(row);
            }

            stmt.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return new Result();
        }
    }

    public boolean custom(String sql) {
        Statement stmt = null;
        try {
            stmt = this.checkConnectOrCreateNew().createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return false;
    }
}
