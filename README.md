# COMP 3380: F1 Dataset

This project demonstrates how to connect to a SQL Server database using Java JDBC (Java Database Connectivity). It includes basic database operations like creating a connection, querying, inserting, updating, and deleting records from the database.

[Data source](https://www.kaggle.com/datasets/rohanrao/formula-1-world-championship-1950-2020?select=lap_times.csv)

---	

## Prerequisites
- Java 8 or higher
- SQL Server (any version)
- JDBC Driver for SQL Server (Microsoft JDBC Driver)

---

## Setup Instructions

### 1. Load the Database (from CSV files into the Uranium SQL Server)
NOTE: The database is already pre-loaded from the F1 dataset

#### In the Windows environment
```bash
make initdb
```

#### In the Aviary environment
```bash
make initdb_aviary
```

### 2. Compile the program
NOTE: This step can be skipped

```bash
make
```

OR

```bash
make build
```

### 3. Run the Command Line Interface

#### In the Windows environment
```bash
make run
```

#### In the Aviary environment
```bash
make run_aviary
```

---

## Program Instructions

```
This Program contains information about F1 World Championship from 1950 - 2024

The following commands are available in this programm:
1.  drivers                           - Lists all drivers in the database.
2.  constructors                      - Lists all constructors (teams) in the database.
3.  races                             - Lists all races in the database.
4.  team_drivers <constructorId>      - Lists all drivers for a given team.
5.  driver_teams <driverId>           - Lists all teams a given driver has driven.
6.  circuits                          - Lists all race circuits in the database.
7.  fastest_lap <driverId>            - Displays the fastest lap times for a given driver.
8.  top_drivers                       - Displays the top-performing drivers in the database.
9.  search_driver <partial_name>      - Searches for a driver by a partial name.

Note: commnands are in the format - COMMAND <VARIABLE>. Variables are only required for selected commands, see above.
```

---

## Project Structure

```
COMP_3380/
│
├── dataset/                        #F1 data files stored here
│
├── auth.cfg                        # Authentication / DB config file
│
├── f1.sql                          # SQL setup/insert script
│
├── mssql-jdbc-8.2.2.jre8.jar       # External libraries (JAR files)
├── mssql-jdbc-11.2.0.jre11.jar
│
├── Main.java                       #main file for the application
├── DatabaseLoader.java             # Loads in the data programatically
├── DatabaseLoadTool.java           # Insert script
├── InputHandler.java               # Collects user input
├── QueryManager.java               # Respond to user input
│
├── README.md                       # Project overview and instructions
├── Makefile                        # Build/run commands (if applicable)
└── .gitignore                      # Git ignore settings
```

---
