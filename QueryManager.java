import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

        } else if (command.equals("DELETE_ALL")) { // clear the database
            (new DatabaseLoader()).clearData();

        } else if (command.equals("LOAD")) { // search_driver command
            (new DatabaseLoader()).loadData(DatabaseLoader.FILENAME);

        } else { // invalid command
            System.out.println("Please enter valid command");
        }

        // formating gaps for easy readability
        System.out.println();

        return continueProgram;

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
            String fmt = "| %5s| %25s| %25s| %15s| %25s| %5s| %5s| %25s|";

            String header = String.format(fmt, "Id", "First Name", "Last Name", "DOB", "Nickname",
                    "Number",
                    "Code", "Nationality");

            printHorDivider(header);

            System.out.println(header);

            printHorDivider(header);

            while (result.next()) {
                String driverId = result.getString("driverId");
                String driverRef = result.getString("driverRef");
                String driverNumber = result.getString("driverNumber");
                String code = result.getString("code");
                String forename = result.getString("forename");
                String surname = result.getString("surname");
                String dob = result.getString("dob");
                String nationality = result.getString("nationality");

                System.out.println(
                        String.format(fmt, driverId, forename, surname, dob, driverRef, driverNumber, code,
                                nationality));

            }

            printHorDivider(header);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    /**
     * list of all constructors
     */
    private void constructors() {
        System.out.println("Getting constructors...");
        String query = "SELECT DISTINCT * FROM constructors;";

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            String fmt = "| %5s| %25s| %25s|";

            String header = String.format(fmt, "Id", "Name", "Nationality");

            printHorDivider(header);

            System.out.println(header);

            printHorDivider(header);

            while (result.next()) {
                String constructorId = result.getString("constructorId");
                String constructorName = result.getString("constructorName");
                String nationality = result.getString("nationality");

                System.out.println(
                        String.format(fmt, constructorId, constructorName, nationality));

            }

            printHorDivider(header);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Print a list of all the race data
     */
    private void races() {
        System.out.println("Getting races...");
        String query = "SELECT raceId,raceYear,round,circuits.circuitId as cirId,circuits.circuitName as circName,raceName,raceDate"
                + " FROM races INNER JOIN circuits on (races.circuitId = circuits.circuitId);";

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            String fmt = "|%5s| %35s| %6s| %4s| %7s| %40s| %12s|";

            String header = String.format(fmt, "id", "Name", "Year", "Round", "cirId", "Circuit Name", "Date");

            printHorDivider(header);

            System.out.println(header);

            printHorDivider(header);

            while (result.next()) {
                String raceId = result.getString("raceId");
                String raceYear = result.getString("raceYear");
                String round = result.getString("round");
                String circName = result.getString("circName");
                String raceName = result.getString("raceName");
                String raceDate = result.getString("raceDate");
                String circuitId = result.getString("cirId");

                System.out.println(
                        String.format(fmt, raceId, raceName, raceYear, round, circuitId, circName, raceDate));

            }

            printHorDivider(header);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Print drivers for a certain team with contructorId
     * 
     * @param constructorId constructor to get drivers for
     */
    private void teamDrivers(String constructorId) {
        if (!constructorId.equals("")) {

            System.out.println("Getting drivers for Id:" + constructorId + " constructor...");

            String query = "SELECT DISTINCT forename, surname"
                    + " FROM results INNER JOIN drivers"
                    + " ON (results.driverId = drivers.driverId)"
                    + " WHERE constructorId = ?;";

            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, Integer.parseInt(constructorId));
                ResultSet result = statement.executeQuery();

                String fmt = "|%25s| %25s|";

                String header = String.format(fmt, "First Name", "Last Name");

                printHorDivider(header);

                System.out.println(header);

                printHorDivider(header);

                while (result.next()) {
                    String forename = result.getString("forename");
                    String surname = result.getString("surname");

                    System.out.println(
                            String.format(fmt, forename, surname));

                }

                printHorDivider(header);

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println(e.getMessage());
            }

        } else {
            System.out.println("Enter a valid constructorId (team_drivers <constructorId>)");
        }
    }

    /**
     * Print a list of constructors a driver has driven for
     * 
     * @param driverId for driver
     */
    private void driverTeams(String driverId) {
        if (!driverId.equals("")) {
            System.out.println("Getting constructors that driverId:" + driverId + " drove for...");

            String query = "SELECT DISTINCT constructorName"
                    + " FROM results INNER JOIN constructors"
                    + " ON (results.constructorId = Constructors.constructorId)"
                    + " WHERE driverId = ?;";

            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, Integer.parseInt(driverId));
                ResultSet result = statement.executeQuery();

                String fmt = "|%25s|";

                String header = String.format(fmt, "Constructor Name");

                printHorDivider(header);

                System.out.println(header);

                printHorDivider(header);

                while (result.next()) {
                    String constructorName = result.getString("constructorName");

                    System.out.println(
                            String.format(fmt, constructorName));

                }

                printHorDivider(header);

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.out.println("Enter a valid driverId (driver_teams <driverId>)");
        }
    }

    /**
     * Most used circuits
     */
    private void circuits() {
        System.out.println("Getting the most used circuits...");

        String query = "SELECT circuitName, circuitLocation, circuits.circuitId as circId, count(races.circuitId) as times_raced"
                + " FROM circuits LEFT JOIN races"
                + " ON (Circuits.circuitId = races.circuitId)"
                + " WHERE circuits.circuitId=races.circuitId"
                + " GROUP BY circuits.circuitId, circuitName, circuitLocation"
                + " ORDER BY count(races.circuitId) DESC;";

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            String fmt = "| %5s| %40s| %25s| %10s|";

            String header = String.format(fmt, "Id", "Name", "Location", "Num. Used");

            printHorDivider(header);

            System.out.println(header);

            printHorDivider(header);

            while (result.next()) {
                String circuitName = result.getString("circuitName");
                String circuitLocation = result.getString("circuitLocation");
                String circId = result.getString("circId");
                String teamRaced = result.getString("times_raced");

                System.out.println(
                        String.format(fmt, circId, circuitName, circuitLocation, teamRaced));

            }

            printHorDivider(header);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Print the fastest lap of a driver with driverId
     * 
     * @param driverId id of the driver to search for fastest lap
     */
    private void fastestLap(String driverId) {
        if (!driverId.equals("")) {
            System.out.println("Getting the fastest lap for driverId:" + driverId);

            String query = "SELECT DISTINCT circuits.circuitName as cirName, min(fastestLapTime) as fastest"
                    + " FROM results"
                    + " INNER JOIN races ON (Results.raceId = Races.raceId)"
                    + " INNER JOIN circuits ON (Races.circuitId = Circuits.circuitId)"
                    + " GROUP BY Circuits.circuitId, driverId, circuitName"
                    + " HAVING driverId = ?;";

            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, Integer.parseInt(driverId));
                ResultSet result = statement.executeQuery();

                String fmt = "| %45s| %10s|";

                String header = String.format(fmt, "Name", "Fastest");

                printHorDivider(header);

                System.out.println(header);

                printHorDivider(header);

                while (result.next()) {
                    String name = result.getString("cirName");
                    String fastest = result.getString("fastest");

                    System.out.println(
                            String.format(fmt, name, fastest));

                }

                printHorDivider(header);

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.out.println("Enter a valid driver name (fastest_lap <driverName>)");
        }
    }

    /**
     * Print top 10 drivers who have driven in the most races
     */
    private void topDrivers() {
        System.out.println("Getting Drivers with the most races...");

        String query = "SELECT TOP 10 drivers.forename as forename,drivers.surname as surname, drivers.driverId as dId, count(results.driverId) as num_races"
                + " FROM results INNER JOIN drivers"
                + " ON (Results.driverId = Drivers.driverId)"
                + " WHERE drivers.driverId=results.driverId"
                + " GROUP BY drivers.driverRef, forename, surname, Drivers.driverId"
                + " ORDER BY count(results.driverId) DESC;";

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            String fmt = "| %5s| %25s| %25s| %10s|";

            String header = String.format(fmt, "Id", "First Name", "Last Name", "Num. Races");

            printHorDivider(header);

            System.out.println(header);

            printHorDivider(header);

            while (result.next()) {
                String forename = result.getString("forename");
                String surname = result.getString("surname");
                String driverId = result.getString("dId");
                String numRaces = result.getString("num_races");

                System.out.println(
                        String.format(fmt, driverId, forename, surname, numRaces));

            }

            printHorDivider(header);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Print drivers with name that resemble supplied name, ed returns edward and
     * ked for eg.
     * 
     * @param driverName
     */
    private void searchDriver(String driverName) {
        if (!driverName.equals("")) {
            driverName = driverName.toLowerCase();

            System.out.println("Getting Drivers with the matching string sequence...");

            String query = "SELECT * FROM drivers"
                    + " WHERE LOWER(forename) LIKE ? OR LOWER(surname) LIKE ?;";

            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, "%" + driverName + "%");
                statement.setString(2, "%" + driverName + "%");
                ResultSet result = statement.executeQuery();

                String fmt = "| %5s| %25s| %25s| %15s| %25s| %5s| %5s| %25s|";

                String header = String.format(fmt, "Id", "First Name", "Last Name", "DOB", "Nickname",
                        "Number",
                        "Code", "Nationality");

                printHorDivider(header);

                System.out.println(header);

                printHorDivider(header);

                while (result.next()) {
                    String driverId = result.getString("driverId");
                    String driverRef = result.getString("driverRef");
                    String driverNumber = result.getString("driverNumber");
                    String code = result.getString("code");
                    String forename = result.getString("forename");
                    String surname = result.getString("surname");
                    String dob = result.getString("dob");
                    String nationality = result.getString("nationality");

                    System.out.println(
                            String.format(fmt, driverId, forename, surname, dob, driverRef, driverNumber, code,
                                    nationality));

                }

                printHorDivider(header);

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println(e.getMessage());
            }
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
        System.out.println("4.  team_drivers <constructorId>      - Lists all drivers for a given team.");
        System.out.println("5.  driver_teams <driverId>           - Lists all teams a given driver has driven.");
        System.out.println("6.  circuits                          - Lists all race circuits in the database.");
        System.out.println(
                "7.  fastest_lap <driverId>            - Displays the fastest lap times for a given driver.");
        System.out.println(
                "8.  top_drivers                       - Displays the top-performing drivers in the database.");
        System.out.println(
                "9.  search_driver <partial_name>      - Searches for a driver by a partial name.");
        System.out.println("10.  DELETE_ALL                       - delete all the data from the database.");
        System.out.println("11.  LOAD                             - repopulate the database.");
        System.out.println(
                "\nNote: commnands are in the format - COMMAND <VARIABLE>. Variables are only required for selected commands, see above.\n");
    }

    /**
     * Generates row divider
     * 
     * @param header header for table
     */
    private void printHorDivider(String header) {
        System.out.println(String.format("%0" + header.length() + "d", 0).replace("0", "="));
    }

}
