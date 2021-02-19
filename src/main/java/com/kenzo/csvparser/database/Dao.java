package com.kenzo.csvparser.database;

import java.util.List;

public interface Dao {
    int insert(String[] data);
    int insert(List<String[]> data);
}
