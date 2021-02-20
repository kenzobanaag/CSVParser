package com.kenzo.csvparser.optimization;

import com.kenzo.csvparser.database.IDatabase;

import java.util.List;

/*
* Description: Times the execution of creating a table and inserting data.
* */
public class DatabaseTimer implements ITimer{

    private final IDatabase db;
    private final List<String[]> data;

    public DatabaseTimer(IDatabase db, List<String[]> data) {
        this.db = db;
        this.data = data;
    }

    @Override
    public long runTimer() {
        long startTime = System.nanoTime();

        db.createTable("dataTable");
        db.insert(data);

        long endTime = System.nanoTime();
        return (endTime - startTime) / 1000000;
    }
}
