package com.kenzo.csvparser;

import com.kenzo.csvparser.fileparser.CSVParser;
import com.kenzo.csvparser.fileparser.FileParser;

import java.util.List;
import java.util.Scanner;

/*
* TODO : Validate file being read
*        Read by: args, or scanner
*
* TODO : Connect to SQLite DB
*           Connection String
*
* NOTE : App should be re-runnable
*           Might need to create a new db every time we run the program, since we will have duplicate data
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
        // [PHASE TWO]
        // create db

        // connect? - SQLDatabaseClass

        // create table if not exist - SQLDatabaseClass

        // insert good data to db, bad to csv - DatabaseDAO
        // CRUD - Create, Read, Update, Delete

        // create statistics log file

        System.out.println("Parsing Successful!");
    }
}
