import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseLoader {

    private Connection connection;

    private static final String FILENAME = "f1.sql"; // create databse

    public static void main(String[] args) {
        System.out.println("Loading database...");
        DatabaseLoader dataLoader = new DatabaseLoader();
        if (!dataLoader.loadData()) {
            System.out.println("Unable to load Database");
            System.exit(1);
        }

        System.out.println("Done loading database...");
    }

    public DatabaseLoader() {
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

    public boolean loadData() {
        System.out.println("Loading Database...");
        boolean result = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(FILENAME)));

            String query = ""; // stores string for query
            String line;

            // read all lines, inserting as the loop iterates
            while ((line = reader.readLine()) != null) {
                // clean up
                line = line.trim();

                // add to string store
                query += line;

                if (line.length() > 0 && line.charAt(line.length() - 1) == ';') {
                    Statement statement = this.connection.createStatement();
                    statement.execute(query);
                    // System.out.println(query);
                    query = "";
                }
            }

            // close stream
            reader.close();
            result = true;
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        System.out.println("Finished loading Database...");
        return result;
    }

}
