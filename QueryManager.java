import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class QueryManager {
    // insatance props
    private Connection connection;

    // Constructor - establish connection to database
    public QueryManager() {
        Properties prop = new Properties();
        String fileName = "auth.cfg";
        try {
            FileInputStream configFile = new FileInputStream(fileName);
            prop.load(configFile);
            configFile.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find config file.");
            System.exit(1);
        } catch (IOException ex) {
            System.out.println("Error reading config file.");
            System.exit(1);
        }
        String username = (prop.getProperty("username"));
        String password = (prop.getProperty("password"));

        if (username == null || password == null) {
            System.out.println("Username or password not provided.");
            System.exit(1);
        }

        String connectionUrl = "jdbc:sqlserver://uranium.cs.umanitoba.ca:1433;"
                + "database=cs3380;"
                + "user=" + username + ";"
                + "password=" + password + ";"
                + "encrypt=false;"
                + "trustServerCertificate=false;"
                + "loginTimeout=30;";

        try {
            this.connection = DriverManager.getConnection(connectionUrl);
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

        // formating gaps for easy readability
        System.out.println();

        // respond to user input
        if (command.length() < 1) { // invalid inputs
            System.out.println("\nPlease enter valid command");

        } else if (command.equals("q")) { // quit command
            System.out.println("Thank you for using our program.");
            System.out.println("Exiting...");
            continueProgram = false;

        } else if (command.equals("h")) { // help command
            this.help();

        } else if (command.equals("drivers")) { // drivers command
            this.drivers();

        } else if (command.equals("constructors")) { // constructors command
            this.constructors();

        } else if (command.equals("races")) { // races command
            this.races();

        } else if (command.equals("team_drivers")) { // team_drivers command
            this.teamDrivers(variable);

        } else if (command.equals("driver_teams")) { // driver_teams command
            this.driverTeams(variable);

        } else if (command.equals("circuits")) { // circuits command
            this.circuits();

        } else if (command.equals("fastest_lap")) { // fastest_lap command
            this.fastestLap(variable);

        } else if (command.equals("top_drivers")) { // top_drivers command
            this.topDrivers();

        } else if (command.equals("search_driver")) { // search_driver command
            this.searchDriver(variable);

        } else { // invalid command
            System.out.println("Please enter valid command");
        }

        // formating gaps for easy readability
        System.out.println();

        return continueProgram;

    }

    private void printHorDivider(String header) {
        System.out.println(String.format("%0" + header.length() + "d", 0).replace("0", "="));
    }

    /**
     * Prints the list of all the drivers
     */
    private void drivers() {
        System.out.println("Getting drivers...");
        String query = "SELECT DISTINCT * FROM drivers;";

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            String fmt = "|%25s| %25s| %15s| %25s| %5s| %5s| %25s|";

            String header = String.format(fmt, "First Name", "Last Name", "DOB", "Nickname",
                    "Number",
                    "Code", "Nationality");

            printHorDivider(header);

            System.out.println(header);

            printHorDivider(header);

            while (result.next()) {
                String driverRef = result.getString("driverRef");
                String driverNumber = result.getString("driverNumber");
                String code = result.getString("code");
                String forename = result.getString("forename");
                String surname = result.getString("surname");
                String dob = result.getString("dob");
                String nationality = result.getString("nationality");

                System.out.println(
                        String.format(fmt, forename, surname, dob, driverRef, driverNumber, code, nationality));

            }

            printHorDivider(header);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    // TODO: implement query
    private void constructors() {
        System.out.println("constructors");
    }

    // TODO: implement query
    private void races() {
        System.out.println("races");
    }

    // TODO: implement query
    private void teamDrivers(String teamName) {
        if (!teamName.equals("")) {
            System.out.println("team_drivers");
        } else {
            System.out.println("Enter a valid team name (team_drivers <teamName>)");
        }
    }

    // TODO: implement query
    private void driverTeams(String driverName) {
        if (!driverName.equals("")) {
            System.out.println("driver_teams");
        } else {
            System.out.println("Enter a valid driver name (driver_teams <driverName>)");
        }
    }

    // TODO: implement query
    private void circuits() {
        System.out.println("circuits");
    }

    // TODO: implement query
    private void fastestLap(String driverName) {
        if (!driverName.equals("")) {
            System.out.println("fastest_lap");
        } else {
            System.out.println("Enter a valid driver name (fastest_lap <driverName>)");
        }
    }

    // TODO: implement query
    private void topDrivers() {
        System.out.println("top_drivers");
    }

    // TODO: implement query
    private void searchDriver(String driverName) {
        if (!driverName.equals("")) {
            System.out.println("search_driver");
        } else {
            System.out.println("Enter a valid driver name (search_driver <driverName>)");
        }
    }

    /**
     * Prints the help menu
     */
    private void help() {
        System.out.println("This Program contains information about F1 World Championship from 1950 - 2024");
        System.out.println("\nThe following commands are available in this programm:");
        System.out.println("1.  drivers                           - Lists all drivers in the database.");
        System.out.println("2.  constructors                      - Lists all constructors (teams) in the database.");
        System.out.println("3.  races                             - Lists all races in the database.");
        System.out.println("4.  team_drivers <team_name>          - Lists all drivers for a given team.");
        System.out.println("5.  driver_teams <driver_name>        - Lists all teams a given driver has driven.");
        System.out.println("6.  circuits                          - Lists all race circuits in the database.");
        System.out.println(
                "7.  fastest_lap <driver_name>         - Displays the fastest lap times for a given driver.");
        System.out.println(
                "8.  top_drivers                       - Displays the top-performing drivers in the database.");
        System.out.println(
                "9.  search_driver <partial_name>      - Searches for a driver by a partial name.");
        System.out.println(
                "\nNote: commnands are in the format - COMMAND <VARIABLE>. Variables are only required for selected commands, see above.\n");
    }

}
