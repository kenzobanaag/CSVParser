package com.kenzo.csvparser.optimization;

import com.kenzo.csvparser.database.SQLiteDatabase;
import com.kenzo.csvparser.fileparser.CSVParser;
import com.kenzo.csvparser.statistics.StatisticsWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/*
* Description: Runs a timer for each phase of the program. Writes a run log file which contains all the run times
*              of each program with different input file sizes. This class will time each phase, eg. Parsing and
*              Database queries.
*
* To be able to optimize the parser, I need to know how fast the parser runs on normal and higher count inputs.
* To maximize optimization, I need to know which phase runs slower, and optimize from there.
* */
public class TimerMain {

    public static void main(String[] args) {
        // Time Parsing
        ParserTimer parserTimer = new ParserTimer(new CSVParser("input/ms3Interview.csv"));
        long parseDuration = parserTimer.runTimer();
        System.out.println("Parsing Duration : " + parseDuration);

        // Time Database Functions
        DatabaseTimer dbTimer = new DatabaseTimer(new SQLiteDatabase(parserTimer.parser.getFileName()), parserTimer.getData());
        long dbDuration = dbTimer.runTimer();
        System.out.println("DB Queries Duration : " + dbDuration);

        // Time Stat Log
        StatLogTimer logTimer = new StatLogTimer(new StatisticsWriter(), parserTimer.getParser());
        long statDuration = logTimer.runTimer();
        System.out.println("Stat Log Duration : " + statDuration);

        // write to runtime.log file all gathered data on run times
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("output/runTimes.log", true));
            writer.append("\nRun Times For File Size: ").append(
                    String.valueOf(parserTimer.parser.getInvalidCount() + parserTimer.parser.getValidCount())).append('\n');
            writer.append("Parsing Duration: ").append(String.valueOf(parseDuration)).append(" ms").append('\n');
            writer.append("BD Query Duration: ").append(String.valueOf(dbDuration)).append(" ms").append('\n');
            writer.append("Stat Log Duration: ").append(String.valueOf(statDuration)).append(" ms").append('\n');

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
