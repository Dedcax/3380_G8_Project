--Data scraped from : 
--https://www.kaggle.com/datasets/rohanrao/formula-1-world-championship-1950-2020?select=sprint_results.csv

use cs3380;

CREATE TABLE Circuits (
    circuitId INT PRIMARY KEY,
    circuitRef VARCHAR(100),
    name VARCHAR(255),
    location VARCHAR(255),
    country VARCHAR(100),
    lat FLOAT,
    lng FLOAT,
    alt INT,
);

CREATE TABLE Races (
    raceId INT PRIMARY KEY,
    -- circuitId INT, --TODO: maybe remove this
    year INT,
    round INT,
    raceName VARCHAR(255),
    date DATE,
    time TIME,
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

CREATE TABLE Host (
    raceId INT PRIMARY KEY REFERENCES Races(raceId),
    circuitId INT REFERENCES Circuits(circuitId),
);


CREATE TABLE ConstructorResults (
    constructorResultsId INT PRIMARY KEY,
    raceId INT REFERENCES Races(raceId),
    constructorId INT REFERENCES Constructors(constructorId),
    points INT,
    statusID INT REFERENCES Status(statusId)
);

CREATE TABLE ConstructorStandings (
    ConstructorStandingsId INT PRIMARY KEY,
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
    time VARCHAR(20),
    lapMilliseconds INT,
    PRIMARY KEY (raceId, driverId, lap),
    FOREIGN KEY (raceId) REFERENCES Races(raceId),
    FOREIGN KEY (driverId) REFERENCES Drivers(driverId)
);

CREATE TABLE PitStops (
    raceId INT,
    driverId INT,
    stop INT,
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
    statusId INT,
    number INT,
    grid INT,
    position INT,
    positionOrder INT,
    points INT,
    laps INT,
    time VARCHAR(30),
    milliseconds INT,
    fastestLap INT,
    rank INT,
    fastestLapTime VARCHAR(20),
    fastestLapSpeed VARCHAR(20),
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
    statusId INT,
    number INT,
    grid INT,
    position INT,
    positionOrder INT,
    points INT,
    laps INT,
    time VARCHAR(30),
    milliseconds INT,
    fastestLap INT,
    fastestLapTime VARCHAR(20),
    FOREIGN KEY (raceId) REFERENCES Races(raceId),
    FOREIGN KEY (driverId) REFERENCES Drivers(driverId),
    FOREIGN KEY (constructorId) REFERENCES Constructors(constructorId),
    FOREIGN KEY (statusId) REFERENCES Status(statusId)
);

CREATE TABLE Status (
    statusId INT PRIMARY KEY,
    status VARCHAR(100)
);
