package com.kenzo.csvparser.optimization;

import com.kenzo.csvparser.fileparser.FileParser;
import com.kenzo.csvparser.statistics.StatisticsWriter;

/*
* Description: Times the execution of writing the stat log
* */
public class StatLogTimer implements ITimer{

    private StatisticsWriter writer;
    private FileParser parser;

    public StatLogTimer(StatisticsWriter writer, FileParser parser) {
        this.writer = writer;
        this.parser = parser;
    }


    @Override
    public long runTimer() {
        long startTime = System.nanoTime();

        writer.writeStatistics(parser.getFileName(), new int[] {parser.getValidCount() + parser.getInvalidCount(),
                parser.getValidCount(), parser.getInvalidCount()});

        long endTime = System.nanoTime();
        return (endTime - startTime) / 1000000;
    }
}
