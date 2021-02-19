package com.kenzo.csvparser.fileparser;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidateFileTypeTest {

    private FileParser parser;

    @Test
    public void validate_csv() {
        parser = new CSVParser("test.csv");

        assertTrue(parser.isValidFileType());
    }

    @Test
    public void validate_csv_ignoreCase() {
        parser = new CSVParser("test.CSV");

        assertTrue(parser.isValidFileType());
    }

    @Test
    public void validate_txt() {
        parser = new CSVParser("test.txt");

        assertFalse(parser.isValidFileType());
    }

    @Test
    public void validate_txt_ignoreCase() {
        parser = new CSVParser("test.TXT");

        assertFalse(parser.isValidFileType());
    }

    @Test
    public void validate_other() {
        String[] extensions = new String[] {"pdf", "png", "jpg", "doc", "docx", "rtf"};
        for(String ext : extensions) {
            parser = new CSVParser("test."+ext);

            assertFalse(parser.isValidFileType());
        }
    }
}
