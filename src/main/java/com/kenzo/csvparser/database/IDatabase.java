package com.kenzo.csvparser.database;

public interface IDatabase extends Dao{
    void createTable(String tableName);
}
