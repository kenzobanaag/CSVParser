package com.kenzo.csvparser;

import com.kenzo.csvparser.database.IDatabase;
import com.kenzo.csvparser.database.SQLiteDatabase;
import com.kenzo.csvparser.fileparser.CSVParser;
import com.kenzo.csvparser.fileparser.FileParser;
import com.kenzo.csvparser.statistics.StatisticsWriter;

import java.util.List;
import java.util.Scanner;

/*
*
* TODO : Connect to SQLite DB
*           Connection String
*           Create singleton connection that accepts a database type
*
*        We know that the only thing that differs between different SQL connections like mysql, javadb, sqlite is the
*        connection string. We want to set the connection string/url when the data is valid and is ready to be parsed.
*        Since we want a single instance of thing connection string/object, we can use the singleton pattern to
*        establish a single instance of this class, and globally call it when we need the connection string. This
*        will allow us to easily switch databases if needed to.
*
* NOTE : App should be re-runnable
*           Need to create a new db every time we run the program, since we will have duplicate data
*
* APPROACH REASONING:
*        Since we have no description of the type of data we're receiving, we will assume that everything is a String
*        We will not parse lines into objects to insert, but to a list of strings that represent array[A-J] columns
*
*        The db schema will also assume the same
* */
public class ParserMain {

    public static void main(String[] args) {
        // note: Probably convert to JFrame App
        // get & validate file
        String fileName;
        if(args.length > 0)
            fileName = args[0];
        else {
            System.out.print("Please enter csv file path: ");
            Scanner in = new Scanner(System.in);
            fileName = in.nextLine();
        }

        // [PHASE ONE]
        // read & parse file
        FileParser parser = new CSVParser(fileName); // probably take in file name
        if(!parser.isValidFileType()) {
            System.out.println("File is not a valid csv");
            return;
        }
        List<String[]> data = parser.readThenParse();
        System.out.println("Parsing Successful!");

        // [PHASE TWO]
        // create db
        IDatabase db = new SQLiteDatabase(parser.getFileName());

        // create table if not exist
        db.createTable("dataTable");

        // insert good data to db, bad to csv - DatabaseDAO
        System.out.println(db.insert(data) == 1 ? "Insert Successful" : "Failed Insert");

        // create statistics log file
        StatisticsWriter writer = new StatisticsWriter();
        writer.writeStatistics(parser.getFileName(), new int[] {parser.getValidCount() + parser.getInvalidCount(),
                parser.getValidCount(), parser.getInvalidCount()});

        System.out.println("Statistics Log Created");
    }
}
