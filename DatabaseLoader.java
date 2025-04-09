
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.io.*;

public class DatabaseLoader {

    // insatance props
    private Connection connection;
    private Scanner scanner;

    // class props
    private static int numRows = 0; // total number of rows added to the database

    // class constant
    public static final String DATABASEURL = "jdbc:sqlite:f1.db"; // create databse
    private static final String PREFIX = "dataset/"; // create databse

    // Constructor - establish connection to database
    public DatabaseLoader() {
        try {
            this.connection = DriverManager.getConnection(DATABASEURL);

            // enable foreign keys
            Statement stmt = this.connection.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");
            stmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    /**
     * Factory method for DatabaseLoader class. creates a new instance of the class,
     * loading the dataset into the database
     * 
     * @return
     */
    public static void LoadData() {
        System.out.println("loading in data...");
        DatabaseLoader databaseLoader = new DatabaseLoader();

        // TODO: create tables before inserting
        databaseLoader.loadDataHelper();
        System.out.println(String.format("%d total rows added.", numRows));
    }

    /**
     * Helper function that loads all the tables from the dataset csv files
     */
    private void loadDataHelper() {
        loadDrivers();
        loadConstructors();
        loadCircuits();
        // TODO: add the other functions
    }

    /*
     * Loads the driver.csv file as the driver table
     */
    private void loadDrivers() {
        this.loadData("drivers.csv", "drivers");
    }

    /**
     * Loads the constructor.csv file as the constructor table
     */
    private void loadConstructors() {
        this.loadData("constructors.csv", "constructors");
    }

    /**
     * Loads the circuit.csv file as the circuit table
     */
    private void loadCircuits() {
        this.loadData("circuits.csv", "circuits");
    }

    /**
     * Generic insert function. Inserts data from a csv file into a table. header
     * row values are used as table columns.
     * 
     * @param fileName  name of csv file
     * @param tableName name of table to insert data into
     */
    private void loadData(String fileName, String tableName) {
        String stmt = "INSERT INTO %S (%S) VALUES (%S);"; // generic import statement
        String headers;
        String line;
        try {
            // open the file with the given fileName
            scanner = new Scanner(new File(PREFIX + fileName));

            // read the headers
            headers = scanner.hasNextLine() ? scanner.nextLine().trim() : "";

            // read all lines, inserting as the loop iterates
            while (headers.length() > 0 && scanner.hasNextLine()) {
                line = scanner.nextLine().trim();
                stmt = String.format(stmt, tableName, headers, line);

                // TODO: make database statement/prepared statement (this is temporary)
                // System.out.println(stmt);
                numRows++;
            }

            // close scanner
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }

    }

}
