/*
 *  Author: Alex Thomas
 *  Creation Date: 11/1/2020
 *  Purpose: Defines a utility for printing arrays and other data structures
 * 
 */

/* External Imports */
import java.util.List;

public class PrintUtility {

    /* Static Members */
    private static final int NO_STRING_MIN_WIDTH = -1;

    /* Static Utility Functions */

    public static <T> String listToString(List<T> list, int stringMinWidth, boolean horizontally, boolean numbered) {
        if (list == null || list.size() == 0) return "";

        String result = "";
        int START = 1;
        for (int col = 0; col < list.size(); col++) {
            int displayIndex = col + START;
            if (stringMinWidth >= 0) {
                result += String.format( (numbered ? (displayIndex + ". ") : "") + "%-" + stringMinWidth + "s" + (horizontally ? "\t" : "\n"),list.get(col).toString());
            } else {
                result += String.format((numbered ? (displayIndex + ". ") : "") + "%s" + (horizontally ? "\t" : "\n"),list.get(col).toString());
            }
        }

        return result;

    }

    public static <T> String listToString(List<T> list, int stringMinWidth, boolean horizontally) {
        return listToString(list, stringMinWidth, horizontally, false);
    }

    public static <T> String listToString(List<T> list, int stringMinWidth) {
        return listToString(list, stringMinWidth, true);
    }

    public static <T> String listToString(List<T> list, boolean horizontally, boolean numbered) {
        return listToString(list, NO_STRING_MIN_WIDTH, horizontally, numbered);
    }

    public static <T> String listToString(List<T> list, boolean horizontally) {
        return listToString(list, NO_STRING_MIN_WIDTH, horizontally);
    }

    public static <T> String listToString(List<T> list) {
        return listToString(list, NO_STRING_MIN_WIDTH);
    }

    public static <T> String list2DToString(List<List<T>> matrix, int stringMinWidth, int start, int end, boolean withHeader, boolean indexFromStart) {
        if (matrix == null || matrix.size() == start || end < start) return "";

        // Important Variables
        String result = "";
        int row = start;

        if (withHeader) {

            // Header
            String header = listToString(matrix.get(row), stringMinWidth);
            result += header + "\n";   

            // Separator
            String separator = "";
            while (separator.length() < header.length() + 10) {
                separator += "-";
            }
            result += separator + "\n";

            row++;
        }                

        end = Math.min(end,matrix.size());

        int displayIndex = 1;
        if (!indexFromStart) displayIndex = start;       
        for (; row < end; row++) {

            result += String.format("%d. %s\n",displayIndex,listToString(matrix.get(row), stringMinWidth));
            displayIndex++;

        }

        return result;

    }

    public static <T> String list2DToString(List<List<T>> matrix, int stringMinWidth, int start, int end, boolean withHeader) {
        return list2DToString(matrix, stringMinWidth, start, end, withHeader, true);
    }

    public static <T> String list2DToString(List<List<T>> matrix, int stringMinWidth, int start, boolean withHeader) {
        return list2DToString(matrix, stringMinWidth, start, matrix.size(), withHeader, true);
    }

    public static <T> String list2DToString(List<List<T>> matrix, int stringMinWidth, int start, boolean withHeader, boolean indexFromStart) {
        return list2DToString(matrix, stringMinWidth, start, matrix.size(), withHeader, indexFromStart);
    }

    public static <T> String list2DToString(List<List<T>> matrix, int stringMinWidth, boolean withHeader) {
        return list2DToString(matrix, stringMinWidth, 0, matrix.size(), withHeader, true);

        // if (matrix == null || matrix.size() == 0) return "";

        // // Important Variables
        // String result = "";
        // int row = 0;

        // if (withHeader) {

        //     // Header
        //     String header = listToString(matrix.get(0), stringMinWidth);
        //     result += header + "\n";   

        //     // Separator
        //     String separator = "";
        //     while (separator.length() < header.length() + 10) {
        //         separator += "-";
        //     }
        //     result += separator + "\n";

        //     row++;
        // }                

        // for (; row < matrix.size(); row++) {

        //     result += String.format("%d. %s\n",row,listToString(matrix.get(row), stringMinWidth));

        // }

        // return result;
    }

    public static <T> String list2DToString(List<List<T>> matrix, int stringMinWidth) {
        return list2DToString(matrix, stringMinWidth, 0, matrix.size(), true, true);
    }

    public static <T> String list2DToString(List<List<T>> matrix) {
        return list2DToString(matrix, NO_STRING_MIN_WIDTH, 0, matrix.size(), true, true);
    }

    // TODO: Fix this function
    public static String embeddedListToString(List<?> matrix, int stringMinWidth) {
        if (matrix == null || matrix.size() == 0) return "";

        // Create result
        String result = "";

        boolean isList = false;
        if (matrix.get(0) instanceof List) {
            isList = true;
        }

        int START = 1;
        String LIST_NAME = "List";
        for (int i = 0; i < matrix.size(); i++) {
            int displayIndex = i + START;
            if (isList) {
                result += String.format("%d. %s\n  %s\n", displayIndex, LIST_NAME, embeddedListToString((List<?>) matrix.get(i), stringMinWidth));
            } else {
                result += String.format("%d. %s\n", displayIndex, listToString(matrix));
            }            
        }

        return result;

    }

    

    // UNUSED
    public static <T> void printMatrix(T[][] matrix) {
        if (matrix == null || matrix.length == 0) return;

        int height = matrix.length;
        int width = 0;

        if (height <= 0) return;

        width = matrix[0].length;

        // Top portion of the Board
        for (int col = 0; col < width; col++) {
            System.out.print("+---");
        }
        System.out.println("+");

        for (int row = 0; row < height; row++) {

            // Middle portion of Board containing the actual tile values
            for (int col = 0; col < width; col++) {
                System.out.print("| " + matrix[row][col] + " ");
            }
            System.out.println("|");

            // Bottom portion of the row
            for (int col = 0; col < width; col++) {
                System.out.print("+---");
            }
            System.out.println("+");

        }

    }

    // UNUSED
    public static void printMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return;

        int height = matrix.length;
        int width = 0;

        if (height <= 0) return;

        width = matrix[0].length;

        // Top portion of the Board
        for (int col = 0; col < width; col++) {
            System.out.print("+---");
        }
        System.out.println("+");

        for (int row = 0; row < height; row++) {

            // Middle portion of Board containing the actual tile values
            for (int col = 0; col < width; col++) {
                System.out.print("| " + matrix[row][col] + " ");
            }
            System.out.println("|");

            // Bottom portion of the row
            for (int col = 0; col < width; col++) {
                System.out.print("+---");
            }
            System.out.println("+");

        }

    }

}
