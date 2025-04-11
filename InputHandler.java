import java.util.Scanner;

public class InputHandler {
    // instance props
    private Scanner scanner; // sacnner object for collecting inputs

    // class props
    public static final int COMMAND = 0;
    public static final int VARIABLE = 1;

    // constructor
    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Get input from user using the scanner object in the format [command]
     * [variable].
     * commands are requied for all queries. variables are required for a subset of
     * queris
     * 
     * @return [command, variable] as a string array
     */
    public String[] getUserInput() {
        System.out.print("Please enter a command, 'h' for HELP or 'q' to QUIT -> ");

        String[] result = new String[2];

        // read the next line and split it on the spaces
        if (this.scanner.hasNextLine()) {

            // protects against sql injection - '-' and ';' are auto-replaced with ""
            String[] temp = (this.scanner.nextLine().trim()).replace(";", "").replace("-", "").split(" ");
            result[COMMAND] = temp[COMMAND];
            result[VARIABLE] = temp.length == 2 ? temp[VARIABLE] : "";
        }

        return result;
    }

}
