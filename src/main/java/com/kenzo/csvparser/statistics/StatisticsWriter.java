package com.kenzo.csvparser.statistics;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/*
* Description: Writes the statistics log of the parser to a output file. This includes the total records received,
*              the successful records and the failed records.
* */
public class StatisticsWriter {

    private final String[] statLabels = new String[] {"Records Received", "Records Successful", "Records Failed"};

    public void writeStatistics(String fileName, int[] stats) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("output/"+fileName+".log"));
            for(int i = 0; i < stats.length; i++) { // for each statistic
                writer.append(String.valueOf(stats[i])).append(" ").append(statLabels[i]);
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
