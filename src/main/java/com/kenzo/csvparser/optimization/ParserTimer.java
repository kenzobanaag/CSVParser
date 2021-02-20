package com.kenzo.csvparser.optimization;

import com.kenzo.csvparser.fileparser.FileParser;

import java.util.List;

/*
* Description: Times the execution of reading data and parsing it.
* */
public class ParserTimer implements ITimer{

    public FileParser parser;
    private List<String[]> data;

    public ParserTimer(FileParser parser) {
        this.parser = parser;
    }

    @Override
    public long runTimer() {
        // get file
        // run parser
        long startTime = System.nanoTime();

        data = parser.readThenParse();

        long endTime = System.nanoTime();
        return (endTime - startTime) / 1000000;
    }

    public List<String[]> getData() {
        return data;
    }

    public FileParser getParser() {
        return parser;
    }
}
