package com.kenzo.csvparser.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DataDao implements Dao{

    private final String url;
    private final String tableName;

    protected DataDao(String connectionUrl, String tableName) {
        url = connectionUrl;
        this.tableName = tableName;
    }

    @Override
    public int insert(String[] data) {
        int res;
        Connection conn;
        String sql = "INSERT INTO " + tableName + "(a,b,c,d,e,f,g,h,i,j)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?)";

        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            for(int i = 0; i < data.length; i++)
                preparedStatement.setString(i+1, data[i]);
            res = preparedStatement.executeUpdate();
        }
        catch (SQLException throwable) {
            res = -1;
            throwable.printStackTrace();
        }

        return res;
    }

    @Override
    public int insert(List<String[]> data) {
        int res;
        Connection conn;
        String sql = "INSERT INTO " + tableName + "(a,b,c,d,e,f,g,h,i,j)" +
                " VALUES(?,?,?,?,?,?,?,?,?,?)";

        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            for(int j = 0; j < data.size(); j++)
            for(int i = 0; i < data.get(j).length; i++)
                preparedStatement.setString(i+1, data.get(j)[i]);
            res = preparedStatement.executeUpdate();
        }
        catch (SQLException throwable) {
            res = -1;
            throwable.printStackTrace();
        }

        return res;
    }
}
