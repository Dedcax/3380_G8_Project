
import java.util.function.Function;
import java.io.*;

public class DatabaseLoadTool {

    // class constant
    private static final String PREFIX = "dataset/"; // create databse
    private static final String OUTPUT_URL = "output.sql"; // create databse

    public static void main(String[] args) {
        DatabaseLoadTool.LoadData();
    }

    /**
     * Factory method for DatabaseLoadTool class. creates a new instance of the
     * class,
     * loading the dataset into the database
     * 
     * @return
     */
    public static void LoadData() {
        System.out.println("Populating f1.sql...");
        DatabaseLoadTool databaseLoadTool = new DatabaseLoadTool();

        databaseLoadTool.loadDataHelper();
        System.out.println("Done populating f1.sql.");
    }

    /**
     * Helper function that loads all the tables from the dataset csv files
     */
    private void loadDataHelper() {
        this.loadData("circuits.csv", "Circuits", s -> this.translateCircuits(s));
        this.loadData("races.csv", "Races", s -> this.translateRaces(s));
        this.loadData("drivers.csv", "Drivers", s -> this.translateDrivers(s));
        this.loadData("constructors.csv", "Constructors", s -> this.translateConstructors(s));
        this.loadData("status.csv", "StatusCodes", s -> this.translateStatusCodes(s));
        this.loadData("constructor_results.csv", "ConstructorResults", s -> this.translateConstructorResults(s));
        this.loadData("constructor_standings.csv", "ConstructorStandings", s -> this.translateConstructorStandings(s));
        this.loadData("driver_standings.csv", "DriverStandings", s -> this.translateDriverStandings(s));
        this.loadData("lap_times.csv", "LapTimes", s -> this.translateLapTimes(s));
        this.loadData("pit_stops.csv", "PitStops", s -> this.translatePitStops(s));
        this.loadData("qualifying.csv", "Qualifying", s -> this.translateQualifying(s));
        this.loadData("results.csv", "Results", s -> this.translateResults(s));
        this.loadData("sprint_results.csv", "SprintResults", s -> this.translateSprintResults(s));

    }

    private String translateCircuits(String str) {
        String[] temp = this.splitString(str);
        temp[1] = addSingleQuote(temp[1]);
        temp[2] = addSingleQuote(temp[2]);
        temp[3] = addSingleQuote(temp[3]);
        temp[4] = addSingleQuote(temp[4]);
        return this.joinString(this.addNull(temp));
    }

    private String translateRaces(String str) {
        // create result array and fill it will "''"
        String[] temp = new String[17];

        // transfer to temp
        String[] strArr = this.splitString(str);
        for (int i = 0; i < strArr.length; i++) {
            temp[i] = strArr[i];
        }

        temp[4] = addSingleQuote(temp[4]);
        temp[5] = addSingleQuote(temp[5]);
        temp[6] = strArr.length > 6 ? addSingleQuote(temp[6]) : null;
        temp[7] = strArr.length > 7 ? addSingleQuote(temp[7]) : null;
        temp[8] = strArr.length > 8 ? addSingleQuote(temp[8]) : null;
        temp[9] = strArr.length > 9 ? addSingleQuote(temp[9]) : null;
        temp[10] = strArr.length > 10 ? addSingleQuote(temp[10]) : null;
        temp[11] = strArr.length > 11 ? addSingleQuote(temp[11]) : null;
        temp[12] = strArr.length > 12 ? addSingleQuote(temp[12]) : null;
        temp[13] = strArr.length > 13 ? addSingleQuote(temp[13]) : null;
        temp[14] = strArr.length > 14 ? addSingleQuote(temp[14]) : null;
        temp[15] = strArr.length > 15 ? addSingleQuote(temp[15]) : null;
        temp[16] = strArr.length > 16 ? addSingleQuote(temp[16]) : null;

        return String.join(", ", temp);
    }

    private String translateDrivers(String str) {
        String[] temp = this.splitString(str);
        temp[1] = addSingleQuote(temp[1]);
        temp[3] = addSingleQuote(temp[3]);
        temp[4] = addSingleQuote(temp[4]);
        temp[5] = addSingleQuote(temp[5]);
        temp[6] = addSingleQuote(temp[6]);
        temp[7] = addSingleQuote(temp[7]);
        return this.joinString(this.addNull(temp));
    }

    private String translateConstructors(String str) {
        String[] temp = this.splitString(str);
        temp[1] = addSingleQuote(temp[1]);
        temp[2] = addSingleQuote(temp[2]);
        temp[3] = addSingleQuote(temp[3]);
        return this.joinString(this.addNull(temp));
    }

    private String translateStatusCodes(String str) {
        String[] temp = this.splitString(str);
        temp[1] = addSingleQuote(temp[1]);
        return this.joinString(this.addNull(temp));
    }

    private String translateConstructorResults(String str) {
        return this.joinString(this.addNull(this.splitString(str)));
    }

    private String translateConstructorStandings(String str) {
        return this.joinString(this.addNull(this.splitString(str)));
    }

    private String translateDriverStandings(String str) {
        return this.joinString(this.addNull(this.splitString(str)));
    }

    private String translateLapTimes(String str) {
        String[] temp = this.splitString(str);
        temp[4] = addSingleQuote(temp[4]);
        return this.joinString(this.addNull(temp));
    }

    private String translatePitStops(String str) {
        String[] temp = this.splitString(str);
        temp[4] = addSingleQuote(temp[4]);
        temp[5] = addSingleQuote(temp[5]);
        return this.joinString(this.addNull(temp));
    }

    private String translateQualifying(String str) {
        // create result array and fill it will "''"
        String[] temp = new String[9];

        // transfer to temp
        String[] strArr = this.splitString(str);
        for (int i = 0; i < strArr.length; i++) {
            temp[i] = strArr[i];
        }

        temp[6] = strArr.length > 6 ? addSingleQuote(formatTime(temp[6])) : null;
        temp[7] = strArr.length > 7 ? addSingleQuote(formatTime(temp[7])) : null;
        temp[8] = strArr.length > 8 ? addSingleQuote(formatTime(temp[8])) : null;

        return String.join(", ", temp);
    }

    private String translateResults(String str) {
        String[] temp = this.splitString(str);
        temp[10] = addSingleQuote(temp[10]);
        temp[14] = addSingleQuote(temp[14]);
        temp[15] = addSingleQuote(temp[15]);
        return this.joinString(this.addNull(temp));
    }

    private String translateSprintResults(String str) {
        String[] temp = this.splitString(str);
        temp[10] = addSingleQuote(temp[10]);
        temp[13] = addSingleQuote(temp[13]);
        return this.joinString(this.addNull(temp));
    }

    /**
     * Generates insert statement for a table from a given CSV file.
     * 
     * @param fileName  name of csv file
     * @param tableName name of table to insert data into
     */
    private void loadData(String fileName, String tableName, Function<String, String> translator) {

        // I/O classes
        BufferedReader reader;
        BufferedWriter writer;

        String stmt = "INSERT INTO %s VALUES (%s);"; // generic import statement
        String line;
        int numLines = 0;

        try {
            // open the file with the given fileName
            reader = new BufferedReader(new FileReader(PREFIX + fileName));

            // open output stream
            writer = new BufferedWriter(new FileWriter(new File(OUTPUT_URL), true));

            // skip the header line
            reader.readLine();

            // read all lines, inserting as the loop iterates
            while ((line = reader.readLine()) != null) {

                line = line.replaceAll("'", "''");

                // print out import statement
                writer.write(String.format(stmt, tableName, translator.apply(line)));
                writer.newLine();
                // System.out.println(String.format(stmt, tableName, translator.apply(line)));
                numLines++;
            }

            // print out number of lines read
            System.out.println("--Number of rows -> " + numLines + "\n");

            // close scanner
            reader.close();
            writer.close();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    /**
     * returs the string in single quotes
     * 
     * @param str string to add quotes to
     * @return 'str'
     */
    private String addSingleQuote(String str) {
        String result = str.trim();

        if (result.length() == 0 || result.equals("''")) {
            result = null;
        } else {
            result = "'" + result + "'";
        }

        return String.format("%s", result);
    }

    /**
     * join string[] with ', ' to form a more readable script
     * 
     * @param temp String[] to joiin
     * @return string representation of string[]
     */
    private String joinString(String[] temp) {
        return String.join(", ", temp);
    }

    /**
     * splits a string into String[] on , delimeter
     * 
     * @param str string to split
     * @return String[] of str
     */
    private String[] splitString(String str) {
        return (str.trim()).split(",");
    }

    /**
     * Formats time values that come in the format M:SS.TTT into MM:SS.TTT where M =
     * minutes, S = seconds and T = milliseconds
     * 
     * @param time time to format
     * @return format time if is invalid format, return time otherwise
     */
    private String formatTime(String time) {
        int ind = time.indexOf(':'); // index of :
        String min = time.substring(0, ind); // minutes of duration
        String result;

        // test to see if minutes < 10, if true add 0 ass prefix
        try {
            int num = Integer.parseInt(min);
            String fmtNum = num < 10 ? "0" + num : "" + num;
            result = fmtNum + time.substring(ind);
        } catch (NumberFormatException e) {
            result = "";
        } catch (Exception e) {
            result = "";
        }

        return result;
    }

    /**
     * replaces emplty cells with null
     * 
     * @param test string array to analyse
     * @return string array with empty cells changed to null
     */
    private String[] addNull(String[] test) {
        for (int i = 0; i < test.length; i++) {
            test[i] = test[i].trim().length() == 0 ? null : test[i].trim();
        }
        return test;
    }

}
