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
    private String[] getUserInput() {
        String[] res = new String[2];

        int count = 0;
        while (this.scanner.hasNext()) {
            res[count] = this.scanner.next();
            count++;
        }

        return res;
    }

    /**
     * Collects user input using the scanner object and responds to the command
     * appropriately.
     * Generates error messages to keep users informed.
     * 
     * @return true to keep the program running, false to quit
     */
    public boolean handleInput() {
        System.out.print("Please enter a command, 'h' for HELP or 'q' to QUIT -> "); // user prompt

        // get user input and store it
        String[] input = getUserInput();
        String command = input[COMMAND];
        String variable = input[VARIABLE];
        boolean res;

        // respond to user input
        if (command.length() < 1) {
            System.out.println("\nPlease enter valid command");
            res = true;
        } else if (command.equals("q")) {
            System.out.println("Thank you for using our program.");
            res = false;
        } else if (command.equals("h")) {
            System.out.println("Welcome to the Help page");
            res = false;
        } else {
            System.out.println("Please enter valid command");
            res = true;
        }

        return res;
    }

}
