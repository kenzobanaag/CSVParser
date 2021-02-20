package com.kenzo.csvparser.database;

import java.sql.*;
import java.util.List;

/*
* Description: Concrete implementation of a database object for an SQLite database. Responsible for creating the
*              database, creating tables and creating the dao.
* */
public class SQLiteDatabase implements IDatabase {

    private final String dbName;
    private Dao dao;
    private final String url;

    public SQLiteDatabase(String name) {
        this.dbName = name+".db";
        url = "jdbc:sqlite:output/"+dbName;
        createDatabase();
    }

    /*
    * Description: Creates an sqlite database with the given connection url
    * */
    private void createDatabase() {
        try (Connection conn = DriverManager.getConnection(url)){
            if(conn != null)
                System.out.println(dbName + " Created");
        }
        catch (SQLException err) {
            System.out.print("Failed Database Creation: ");
            err.printStackTrace();
        }
    }

    /*
    * Description: Creates a table with the given schema and initializes the dao for this table.
    * */
    @Override
    public void createTable(String tableName) {
        String dropTable = "DROP TABLE IF EXISTS " + tableName + ";"; // query for deleting a table
        // query for table creation
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
            statement.execute(dropTable); // delete the table if it already exists
            statement.execute(sql); // create a new table
            dao = new DataDao(url, tableName); // init the dao with the connection url and table name
        } catch (SQLException throwable) {
            System.out.print("Failed DB Table Creation: ");
            throwable.printStackTrace();
        }
    }

    @Override
    public int insert(String[] data) { return dao != null ? dao.insert(data) : -1; }

    @Override
    public int insert(List<String[]> data) { return dao != null ? dao.insert(data) : -1; }
}
