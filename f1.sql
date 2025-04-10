--Data scraped from : 
--https://www.kaggle.com/datasets/rohanrao/formula-1-world-championship-1950-2020?select=sprint_results.csv

use cs3380;

-- clean up
drop table if exists Circuits;
drop table if exists Races;
drop table if exists Drivers;
drop table if exists Constructors;
drop table if exists ConstructorResults;
drop table if exists ConstructorStandings;
drop table if exists DriverStandings;
drop table if exists LapTimes;
drop table if exists PitStops;
drop table if exists Qualifying;
drop table if exists Results;
drop table if exists SprintResults;
drop table if exists StatusCodes;

CREATE TABLE Circuits (
    circuitId INT PRIMARY KEY,
    circuitRef VARCHAR(100),
    circuitName VARCHAR(255),
    circuitLocation VARCHAR(255),
    country VARCHAR(100),
    lat FLOAT,
    lng FLOAT,
    alt INT,
);

CREATE TABLE Races (
    raceId INT PRIMARY KEY,
    raceYear INT,
    round INT,
    circuitId INT,   
    raceName VARCHAR(255),
    raceDate DATE,
    raceTime TIME,
    fp1_date DATE,
    fp1_time TIME,
    fp2_date DATE,
    fp2_time TIME,
    fp3_date DATE,
    fp3_time TIME,
    quali_date DATE,
    quali_time TIME,
    sprint_date DATE,
    sprint_time TIME
);

CREATE TABLE Drivers (
    driverId INT PRIMARY KEY,
    driverRef VARCHAR(100),
    driverNumber INT,
    code VARCHAR(10),
    forename VARCHAR(100),
    surname VARCHAR(100),
    dob DATE,
    nationality VARCHAR(100)
);

CREATE TABLE Constructors (
    constructorId INT PRIMARY KEY,
    constructorRef VARCHAR(100),
    constructorName VARCHAR(255),
    nationality VARCHAR(100)
);

CREATE TABLE ConstructorResults (
    constructorResultsId INT PRIMARY KEY,
    raceId INT REFERENCES Races(raceId),
    constructorId INT REFERENCES Constructors(constructorId),
    points INT,
);

CREATE TABLE ConstructorStandings (
    constructorStandingsId INT PRIMARY KEY,
    raceId INT REFERENCES Races(raceId),
    constructorId INT REFERENCES Constructors(constructorId),
    points INT,
    position INT,
    wins INT
);

CREATE TABLE DriverStandings (
    driverStandingsId INT PRIMARY KEY,
    raceId INT REFERENCES Races(raceId),
    driverId INT REFERENCES Drivers(driverId),
    points INT,
    position INT,
    wins INT
);

CREATE TABLE LapTimes (
    raceId INT,
    driverId INT,
    lap INT,
    position INT,
    lapTime VARCHAR(20),
    lapMilliseconds INT,
    PRIMARY KEY (raceId, driverId, lap),
    FOREIGN KEY (raceId) REFERENCES Races(raceId),
    FOREIGN KEY (driverId) REFERENCES Drivers(driverId)
);

CREATE TABLE PitStops (
    raceId INT,
    driverId INT,
    stopNumber INT,
    lap INT,
    stopTime TIME,
    duration VARCHAR(20),
    stopMilliseconds INT,
    PRIMARY KEY (raceId, driverId, stop),
    FOREIGN KEY (raceId) REFERENCES Races(raceId),
    FOREIGN KEY (driverId) REFERENCES Drivers(driverId)
);

CREATE TABLE Qualifying (
    qualifyId INT PRIMARY KEY,
    raceId INT,
    driverId INT,
    constructorId INT,
    carNumber INT,
    position INT,
    q1 VARCHAR(10),     --TODO: convert format for usability
    q2 VARCHAR(10),     --TODO: convert format for usability
    q3 VARCHAR(10),     --TODO: convert format for usability
    FOREIGN KEY (raceId) REFERENCES Races(raceId),
    FOREIGN KEY (driverId) REFERENCES Drivers(driverId),
    FOREIGN KEY (constructorId) REFERENCES Constructors(constructorId)
);

CREATE TABLE Results (
    resultId INT PRIMARY KEY,
    raceId INT,
    driverId INT,
    constructorId INT,
    raceNumber INT,
    grid INT,
    position INT,
    positionOrder INT,
    points INT,
    laps INT,
    resultTime VARCHAR(30),
    milliseconds INT,
    fastestLap INT,
    rank INT,
    fastestLapTime VARCHAR(20),
    fastestLapSpeed VARCHAR(20),
    statusId INT,
    FOREIGN KEY (raceId) REFERENCES Races(raceId),
    FOREIGN KEY (driverId) REFERENCES Drivers(driverId),
    FOREIGN KEY (constructorId) REFERENCES Constructors(constructorId),
    FOREIGN KEY (statusId) REFERENCES Status(statusId)
);

CREATE TABLE SprintResults (
    resultId INT PRIMARY KEY,
    raceId INT,
    driverId INT,
    constructorId INT,
    sprintNumber INT,
    grid INT,
    position INT,
    positionOrder INT,
    points INT,
    laps INT,
    resultTime VARCHAR(30),
    milliseconds INT,
    fastestLap INT,
    fastestLapTime VARCHAR(20),
    statusId INT,
    FOREIGN KEY (raceId) REFERENCES Races(raceId),
    FOREIGN KEY (driverId) REFERENCES Drivers(driverId),
    FOREIGN KEY (constructorId) REFERENCES Constructors(constructorId),
    FOREIGN KEY (statusId) REFERENCES Status(statusId)
);

CREATE TABLE StatusCodes (
    statusId INT PRIMARY KEY,
    status VARCHAR(100)
);

--bulk insert data from CSV files into database
BULK INSERT Circuits FROM 'dataset\circuits.csv';
BULK INSERT Races FROM 'dataset\races.csv';
BULK INSERT Drivers FROM 'dataset\drivers.csv';
BULK INSERT Constructors FROM 'dataset\constructors.csv';
BULK INSERT ConstructorResults FROM 'dataset\constructor_results.csv';
BULK INSERT ConstructorStandings FROM 'dataset\constructor_standings.csv';
BULK INSERT DriverStandings FROM 'dataset\driver_standings.csv';
BULK INSERT LapTimes FROM 'dataset\lap_times.csv';
BULK INSERT PitStops FROM 'dataset\pit_stops.csv';
BULK INSERT Qualifying FROM 'dataset\qualifying.csv';
BULK INSERT Results FROM 'dataset\results.csv';
BULK INSERT SprintResults FROM 'dataset\sprint_results.csv';
BULK INSERT StatusCodes FROM 'dataset\status.csv';

