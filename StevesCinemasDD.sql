# Create the Steves Cinemas database
DROP DATABASE IF EXISTS STEVESCINEMAS;
CREATE DATABASE STEVESCINEMAS;
USE STEVESCINEMAS;

# Create the CINEMA table
CREATE TABLE CINEMA
	(cinemaID INT PRIMARY KEY NOT NULL,
	name VARCHAR(50) NOT NULL,
    manager VARCHAR(50) NOT NULL,
    location VARCHAR(100) NOT NULL);

# Create the THEATER table    
CREATE TABLE THEATER
	(theaterID INT PRIMARY KEY NOT NULL,
    cinemaID INT NOT NULL,
    capacity INT NOT NULL,
    FOREIGN KEY (cinemaID) REFERENCES CINEMA(cinemaID));
 
# Create the MOVIE table 
CREATE TABLE MOVIE
	(movieID INT PRIMARY KEY NOT NULL,
    title VARCHAR(50) NOT NULL,
    rating DECIMAL(20, 2),
    releaseDate DATE NOT NULL);
    
# Create the SHOWING table    
CREATE TABLE SHOWING
	(showingID INT PRIMARY KEY NOT NULL,
    movieID INT NOT NULL,
    theaterID INT NOT NULL,
    startTime DATETIME NOT NULL,
    ticketPrice DECIMAL(20, 2) NOT NULL,
    childAttendance INT NOT NULL,
    adultAttendance INT NOT NULL,
    FOREIGN KEY (movieID) REFERENCES MOVIE(movieID),
    FOREIGN KEY (theaterID) REFERENCES THEATER(theaterID));
    
USE STEVESCINEMAS;
# Insert data into CINEMA table
INSERT INTO CINEMA VALUES
	(1, 'Central Cinema', 'Alice Johnson', '123 Main St, Springfield'),
	(2, 'Eastside Cinemas', 'Bob Smith', '456 Elm St, Rivertown'),
	(3, 'WestEnd Movies', 'Catherine Brown', '789 Oak St, Hilltown'),
	(4, 'Northpoint Theater', 'David White', '101 Pine St, Valleycity'),
	(5, 'Southgate Cinemas', 'Emma Black', '202 Maple St, Lakeside');

# Insert data into THEATER table
INSERT INTO THEATER VALUES
	(1, 1, 150),
	(2, 1, 200),
	(3, 2, 120),
	(4, 3, 180),
	(5, 4, 100);

# Insert data into MOVIE table
INSERT INTO MOVIE VALUES
	(1, 'Space Adventure', 8.5, '2024-01-15'),
	(2, 'Romance in Paris', 7.2, '2023-12-01'),
	(3, 'Mystery Manor', 9.1, '2024-02-20'),
	(4, 'The Great Heist', 8.7, '2024-03-05'),
	(5, 'Animated Dreams', 6.8, '2023-11-10');

# Insert data into SHOWING table
INSERT INTO SHOWING VALUES
	(1, 1, 1, '2024-11-28 15:00:00', 12.50, 20, 50),
	(2, 2, 2, '2024-11-28 18:00:00', 10.00, 10, 30),
	(3, 3, 3, '2024-11-29 20:00:00', 15.00, 5, 40),
	(4, 4, 4, '2024-11-30 14:30:00', 14.00, 15, 35),
	(5, 5, 5, '2024-11-30 16:00:00', 8.00, 25, 25);    

# Stored procedure to select the total possible revenue for a specific showing by multiplying ticket price by the theater capacity
DELIMITER &&
CREATE PROCEDURE CalculatePossibleRevenue (IN showingIdentifier INT)
	BEGIN
	SELECT showingID, theaterID, ticketPrice, capacity, (ticketPrice * capacity) AS possibleRevenue
    FROM STEVESCINEMAS.SHOWING NATURAL JOIN STEVESCINEMAS.THEATER
    WHERE showingID = showingIdentifier;
	END &&
DELIMITER ;

# Stored procedure to add a movie to the MOVIES table
DELIMITER &&
CREATE PROCEDURE AddMovie (IN id INT, IN movieName VARCHAR(50),
						   IN movieRating DECIMAL(20, 2), IN rDate DATE)
	BEGIN
	INSERT INTO STEVESCINEMAS.MOVIE VALUES
		(id, movieName, movieRating, rDate);
	END &&
DELIMITER ;

# Stored procedure to calculate the average rating of all movies in the database
DELIMITER &&
CREATE PROCEDURE GetAvgRating (OUT AvgRating DECIMAL(20,2))
	BEGIN
    SELECT AVG(rating)
    INTO AvgRating
    FROM STEVESCINEMAS.MOVIE;
    END &&
DELIMITER ;

# Stored procedure to modify the capacity of a specific theater
DELIMITER &&
CREATE PROCEDURE ModifyCapacity(IN theaterIdentifier INT, IN cap INT)
	BEGIN
    UPDATE STEVESCINEMAS.THEATER
    SET capacity = cap
    WHERE theaterID = theaterIdentifier;
    END &&
DELIMITER ;

# Stored procedure to remove a showing from the SHOWINGS table
DELIMITER &&
CREATE PROCEDURE CancelShowing(IN showID INT)
	BEGIN
    DELETE FROM STEVESCINEMAS.SHOWING
    WHERE showingID = showID;
    END &&
DELIMITER ;

# Stored procedure to select everything from the CINEMA table
DELIMITER &&
CREATE PROCEDURE SelectCINEMA()
	BEGIN
    SELECT * FROM CINEMA;
    END &&
DELIMITER ;

# Stored procedure to select everything from the MOVIE table
DELIMITER &&
CREATE PROCEDURE SelectMOVIE()
	BEGIN
    SELECT * FROM MOVIE;
    END &&
DELIMITER ;

# Stored procedure to select everything from the SHOWING table
DELIMITER &&
CREATE PROCEDURE SelectSHOWING()
	BEGIN
    SELECT * FROM SHOWING;
    END &&
DELIMITER ;

# Stored procedure to select everything from the THEATER table
DELIMITER &&
CREATE PROCEDURE SelectTHEATER()
	BEGIN
    SELECT * FROM THEATER;
    END &&
DELIMITER ;