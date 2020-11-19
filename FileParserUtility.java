/*
 *  Author: Alex Thomas
 *  Creation Date: 11/7/2020
 *  Purpose: Defines a utility class for opening and reading information from files
 * 
 */

/* External Imports */
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class FileParserUtility {
    
    // Static helper methods

    public static List<List<String>> parseFile(String filename, String headerDelimiter, String bodyDelimiter, boolean ignoreEmptyLines) {

        // Set up return variable
        List<List<String>> result = new ArrayList<>();

        // Open the file
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(filename));

            // Read line by line
            String line = reader.readLine();

            // Header
            if (!headerDelimiter.equals(bodyDelimiter) && line != null) {
                String[] lineArray = line.split(headerDelimiter);
                if (lineArray.length <= 1 && ignoreEmptyLines) {
                    // Do Nothing
                }
                else {
                    result.add(new ArrayList<String>(Arrays.asList(lineArray))); 
                }                               
                line = reader.readLine();
            }

            while (line != null) {
                List<String> lineList = Arrays.asList(line.split(bodyDelimiter));
                if (lineList.size() <= 1 && ignoreEmptyLines) {
                    // Do Nothing
                }
                else {
                    result.add(new ArrayList<String>(lineList)); 
                } 
                line = reader.readLine();
            }

            // Close the reader
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }       


        // return final result
        return result;

    }

    public static List<List<String>> parseFile(String filename, String headerDelimiter, String bodyDelimiter) {
        return parseFile(filename,headerDelimiter,bodyDelimiter,false);
    }

}
