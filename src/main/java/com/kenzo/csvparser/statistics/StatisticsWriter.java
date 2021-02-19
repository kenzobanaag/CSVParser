package com.kenzo.csvparser.statistics;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class StatisticsWriter {

    private final String[] statLabels = new String[] {"Records Received", "Records Successful", "Records Failed"};

    public void writeStatistics(String fileName, int[] stats) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("output/"+fileName+".log"));

            for(int i = 0; i < stats.length; i++) {
                writer.append(String.valueOf(stats[i])).append(" ").append(statLabels[i]);
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
