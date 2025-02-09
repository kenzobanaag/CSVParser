package com.kenzo.csvparser.fileparser;

import java.util.List;

public interface FileParser {
    boolean isValidFileType();
    List<String[]> readThenParse();
    int getValidCount();
    int getInvalidCount();
    String getFileName();
}
