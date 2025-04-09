import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class QueryManager {
    // insatance props
    private Connection connection;

    // class constant
    private final String url = "jdbc:sqlite::memory:"; // create databse in memory

    // Constructor - establish connection to database
    public QueryManager() {
        try {
            this.connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    /**
     * Responds to user inputs with the appropriate query and error message if
     * necessary.
     * 
     * @param input user input as String[]. [command, variable]
     * @return false to quit program, true otherwise
     */
    public boolean handleInput(String[] input) {
        String command = input[InputHandler.COMMAND];
        String variable = input[InputHandler.VARIABLE];
        boolean continueProgram = true;

        // respond to user input
        if (command.length() < 1) {
            System.out.println("\nPlease enter valid command");
        } else if (command.equals("q")) {
            System.out.println("Thank you for using our program.");
            continueProgram = false;
        } else if (command.equals("h")) {
            System.out.println("Welcome to the Help page");
        } else {
            System.out.println("Please enter valid command");
        }

        return continueProgram;
    }

}
