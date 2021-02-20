package com.kenzo.csvparser;

import com.kenzo.csvparser.database.IDatabase;
import com.kenzo.csvparser.database.SQLiteDatabase;
import com.kenzo.csvparser.fileparser.CSVParser;
import com.kenzo.csvparser.fileparser.FileParser;
import com.kenzo.csvparser.statistics.StatisticsWriter;
import java.util.List;
import java.util.Scanner;

/*
* Description: This program parses a csv file and inserts its valid contents into an SQLite database.
*
* @param
*   args[0] : File path of csv to be parsed
* */
public class ParserMain {

    public static void main(String[] args) {
        // get & validate file
        String fileName;
        if(args.length > 0)
            fileName = args[0];
        else {
            System.out.print("Please enter csv file path: ");
            Scanner in = new Scanner(System.in);
            fileName = in.nextLine();
        }

        FileParser parser = new CSVParser(fileName);
        if(!parser.isValidFileType()) { // validate that file is a csv
            System.out.println("File is not a valid csv");
            return;
        }
        // read and parse file
        List<String[]> data = parser.readThenParse();
        if(data.size() == 0) {
            System.out.println("Either no input or file failed to parse");
            return;
        }
        else
            System.out.println("Parsing Successful!");

        // create database
        IDatabase db = new SQLiteDatabase(parser.getFileName());

        // create database table
        db.createTable("dataTable");

        // insert valid data to database
        System.out.println(db.insert(data) == 1 ? "Insert Successful" : "Failed Insert");

        // create statistics log file
        StatisticsWriter writer = new StatisticsWriter();
        writer.writeStatistics(parser.getFileName(), new int[] {parser.getValidCount() + parser.getInvalidCount(),
                parser.getValidCount(), parser.getInvalidCount()});
        System.out.println("Statistics Log Created");
    }
}