
public class Main {
    public static void main(String[] args) {
        // TODO: load in data

        // TODO: better welcome message
        // welcome message
        System.out.println("Welcome to our F1 Database CLI Program");

        // Class for collecting user inputs
        InputHandler inputHandler = new InputHandler();

        // class for responding to user inputs
        QueryManager queryManager = new QueryManager();

        // read first input
        String[] input = inputHandler.getUserInput();

        // respond to user and loop while the user does not enter the quit command
        while (queryManager.handleInput(input)) {

            // loop condition
            input = inputHandler.getUserInput();
        }

    }

}