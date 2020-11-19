/*
 *  Author: Alex Thomas
 *  Creation Date: 10/29/2020
 *  Purpose: Defines a utility class for taking keyboard input
 * 
 */

/* External Imports */
import java.util.Scanner;
import java.util.List;
import java.util.function.Function;

public class KeyboardInputUtility {
    
    // Static utility functions
    
    // Prompts user for input with a specified prompt statement
    public static String prompt(Scanner input, String promptString) {
        System.out.print(promptString);
        String response = "";
        try {
            response = input.nextLine();
        } catch(Exception e) {
            e.printStackTrace();
        }        
        return response;
     }

     // Continuously prompts user for input with a specified prompt statement
     // until String is deemed valid according to the regular expression
     public static String promptTillValid(Scanner input, String promptString, String regex) {
         boolean invalid = true;
         String response = "";
         while(invalid) {
            response = prompt(input, promptString);
            if(isValidResponse(response, regex)) {
                invalid = false;
            }
         }
         return response;
     }

     // Continuously prompts user for input with a specified prompt statement
     // until String is deemed valid according to the isValid() function passed in
     public static String promptTillValid(Scanner input, String promptString, Function<String,Boolean> isValid) {
        boolean valid = false;
        String response = "";
        while(!valid) {
            response = prompt(input, promptString);
            valid = isValid.apply(response);            
        }
        return response;
     }

     // Ensures that a String matches a regex (Might be redundant)
     public static boolean isValidResponse(String response, String regex) {
        return response.matches(regex);
     }

     // Lists out options from an array and asks the user for input that is the index of the option they would like to choose
     public static int chooseFromList(Scanner input, List<?> list) {
        int indexChoice = -1;

        String numRegex = "^[0-9]+$";        
        boolean valid = false;
        do {

            System.out.println("Options");

            // Prints out choices within the list starting at 1
            int START_INDEX = 1;
            for (int i = 0; i < list.size(); i++) {
                System.out.printf("%d. %s\n",i + START_INDEX, list.get(i).toString());
            }

            String response = promptTillValid(input, "Please input your choice(Number): ", numRegex);
            indexChoice = Integer.parseInt(response) - START_INDEX;

            if(indexChoice >= 0 && indexChoice < list.size()) {
                valid = true;
            }

        }while(!valid);

        return indexChoice;
     }

}
