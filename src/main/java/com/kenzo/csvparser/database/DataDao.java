package com.kenzo.csvparser.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/*
* Description: Concrete implementation of Dao. Allows the insertion of 2 types of data: String[] and List<String>[].
*              This class takes in the connection string and table name to make the appropriate SQL queries.
* */
public class DataDao implements Dao{

    private final String url;
    private final String insertQuery;

    // only allow the database to have access to dao creation.
    protected DataDao(String connectionUrl, String tableName) {
        url = connectionUrl;
        // init insert sql query
        insertQuery = "INSERT INTO " + tableName + "(a,b,c,d,e,f,g,h,i,j)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?)";
    }

    /*
    * Description: Insert method for a single line of data
    * */
    @Override
    public int insert(String[] data) {
        int res;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            for(int i = 0; i < data.length; i++)
                preparedStatement.setString(i+1, data[i]); // build each parameter from A-J
            res = preparedStatement.executeUpdate(); // insert the built query then finish
        }
        catch (SQLException throwable) {
            res = -1;
            throwable.printStackTrace();
        }finally {
            try {
                if(conn != null) // close connection
                    conn.close();
            }
            catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }

        return res;
    }

    /*
    * Description: Insert method for lists of data lines.
    * */
    @Override
    public int insert(List<String[]> data) {
        int res;
        Connection conn = null;
        PreparedStatement preparedStatement= null;
        try {
            res = -1;
            conn = DriverManager.getConnection(url); // get connection
            conn.setAutoCommit(false); // allows transaction
            preparedStatement = conn.prepareStatement(insertQuery);
            for (String[] datum : data) {
                for (int i = 0; i < datum.length; i++) {
                    preparedStatement.setString(i + 1, datum[i]); // build each parameter from A-J
                }
                res = preparedStatement.executeUpdate(); // add this execution to connection sql query
            }

            if(res != 1) // Transactions are all or nothing, if one fails, cancel the whole transaction
                conn.rollback();

            conn.commit(); // run all generated queries in one go(transaction)
        }
        catch (SQLException throwable) {
            try {
                if(conn != null) { // ran into an exception while inserting data
                    conn.rollback(); // so cancel the whole query
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            res = -1; // return -1, because transaction failed
            throwable.printStackTrace();
        }
        finally {
            try {
                if(preparedStatement != null) // close preparedStatement
                    preparedStatement.close();
                if(conn != null) // close connection
                    conn.close();
            }
            catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }

        return res;
    }
}