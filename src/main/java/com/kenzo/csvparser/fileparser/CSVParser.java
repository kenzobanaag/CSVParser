package com.kenzo.csvparser.fileparser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
* Description: This class parses all the valid data and converts it to a list of string[]. All the bad data
*              are written into a file-bad.csv file.
* */
public class CSVParser implements FileParser{

    private final int VALID_COLUMNS = 10;
    private final String filePath;
    private String fileName;
    private int validCount = 0;
    private int invalidCount = 0;

    public CSVParser(String filePath) {
        this.filePath = filePath;
        fileName = new File(filePath).getName(); // get only the filename
        fileName = fileName.substring(0, fileName.length()-4); // remove file extension
    }

    /*
    * Description: Validate if the given file path is a csv extension.
    * */
    public boolean isValidFileType() {
        // validate file extension
        return (filePath != null && filePath.length() > 4 && filePath.toLowerCase().endsWith(".csv"));
    }

    /*
    * Description: Reads each line of the csv file. Splits each line by commas, if the string array doesn't contain
    *              10 elements, it is considered as bad data and will be written to a bad data csv file. If it does, the
    *              array will be added to the data list and will be returned to the caller.
    * */
    public List<String[]> readThenParse() {
        List<String[]> data = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filePath)));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("output/" + fileName + "-bad.csv")));
            String line; reader.readLine(); // ignore first line
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length != VALID_COLUMNS) {
                    // append line to a bad file
                    writer.append(line);
                    writer.newLine();
                    ++invalidCount;

                } else {
                    ++validCount;
                    data.add(values); // add valid data to list
                }
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /*
    * Description: Returns the count of valid lines
    * */
    public int getValidCount() { return validCount; }

    /*
     * Description: Returns the count of invalid lines
     * */
    public int getInvalidCount() { return invalidCount; }

    /*
     * Description: Returns the filename of the input file
     * */
    public String getFileName() { return fileName; }
}