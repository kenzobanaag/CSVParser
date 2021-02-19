package com.kenzo.csvparser.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class IJDBCDatabase {
    Connection connect(String url) throws SQLException {
        return DriverManager.getConnection(url);
    }
    abstract void createNewDatabase(String dbName);
    abstract void createNewTable(String tableName);
}
