package com.kenzo.csvparser.database;

import java.sql.*;
import java.util.List;

public class SQLiteDatabase implements IDatabase {

    private String dbName;
    private Dao dao;

    public SQLiteDatabase(String name) {
        this.dbName = name+".db";
        createDatabase();
    }

    private void createDatabase() {
        String url = "jdbc:sqlite:C:/Users/kenzo/Desktop/MS3Challenge/CSVParser/output/"+dbName;

        try (Connection conn = DriverManager.getConnection(url)){
            if(conn != null) {
                System.out.println(dbName + " created");
            }
        }
        catch (SQLException err) {
            System.out.print("Failed Database Creation: ");
            err.printStackTrace();
        }
    }

    @Override
    public void createTable(String tableName) {
        String url = "jdbc:sqlite:C:/Users/kenzo/Desktop/MS3Challenge/CSVParser/output/"+dbName;

        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "   id integer PRIMARY KEY,\n" +
                "   a text,\n" +
                "   b text,\n" +
                "   c text,\n" +
                "   d text,\n" +
                "   e text,\n" +
                "   f text,\n" +
                "   g text,\n" +
                "   h text,\n" +
                "   i text,\n" +
                "   j text );";

        try {
            Connection conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();
            statement.execute(sql);
            dao = new DataDao(url, tableName);
        } catch (SQLException throwable) {
            System.out.print("Failed DB Table Creation: ");
            throwable.printStackTrace();
        }
    }

    @Override
    public int insert(String[] data) {
        return dao.insert(data);
    }

    @Override
    public int insert(List<String[]> data) {
        return dao.insert(data);
    }
}
