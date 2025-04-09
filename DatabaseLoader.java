
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseLoader {

    // insatance props
    private Connection connection;

    // class constant
    public static final String DATABASEURL = "jdbc:sqlite:f1.db"; // create databse

    // Constructor - establish connection to database
    public DatabaseLoader() {
        try {
            this.connection = DriverManager.getConnection(DATABASEURL);
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
        databaseLoader.loadData();
    }

    // TODO: implement this
    private void loadData() {
    }

}
