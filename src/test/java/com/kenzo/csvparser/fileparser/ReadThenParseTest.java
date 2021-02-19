package com.kenzo.csvparser.fileparser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReadThenParseTest {

    FileParser parser;

    @Test
    public void readValidFileTest_testData() {
        parser = new CSVParser("input/testData.csv");

        assertEquals(2, parser.readThenParse().size()); // length of valid data
    }

    @Test
    public void readValidFileTest_ms3Data() {
        parser = new CSVParser("input/ms3Interview.csv");

        assertEquals(493, parser.readThenParse().size()); // length of valid data
    }
}
