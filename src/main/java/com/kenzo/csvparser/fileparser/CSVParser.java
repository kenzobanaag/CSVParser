package com.kenzo.csvparser.fileparser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVParser implements FileParser{

    private String filePath;
    private String fileName;
    private final String outfilePath = "output/";
    private final int VALID_COLUMNS = 10;
    private int validCount = 0;
    private int invalidCount = 0;

    public CSVParser(String filePath) {
        this.filePath = filePath;
        fileName = new File(filePath).getName();
        fileName = fileName.substring(0, fileName.length()-4);
    }

    public boolean isValidFileType() {
        // validate file extension
        return (filePath != null && filePath.length() > 4 && filePath.toLowerCase().endsWith(".csv"));
    }

    public List<String[]> readThenParse() {
        List<String[]> data = new ArrayList<>();
        // note: Check out file channel for better writing performance
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outfilePath + fileName + "-bad.csv"));
            String line; reader.readLine(); // ignore first line
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if(values.length != VALID_COLUMNS) {
                    // append write to a bad file
                    writer.append(line);
                    writer.newLine();
                    ++invalidCount;
                }
                else {
                    ++validCount;
                    data.add(values);
                }
            }

            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public int getValidCount() {
        return validCount;
    }

    public int getInvalidCount() {
        return invalidCount;
    }

    public String getFileName() {
        return fileName;
    }
}
