/**
 * This class contains static methods allowing for interaction with the database and its
 * stored procedures.
 * @author Kirin Sharma
 * @version 1.0
 * CS 300 Final Project
 *
 */


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

public class DBInteractor 
{

	private static final String URL = "jdbc:mysql://localhost:3306/STEVESCINEMAS";
    private static final String USER = "root"; // change to your username
    private static final String PASSWORD = "yourPassword"; // change to your password

    /**
     * Establishes a connection to the database with the credentials
     * stored in the constant field variables.
     * @return
     */
    public static Connection getConnection() 
    {
        try 
        {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) 
        {
            System.out.println("Database connection failed: " + e.getMessage());
            return null;
        }
    } // end getConnection
	
    /**
     * Static method to call the first stored procedure in the database,
     * which calculates the possible revenue for a specific showing
     * depending on the theater capacity and ticket price.
     * @param showingID the identifier of the showing in the DB
     * @throws SQLException 
     */
    public static void CalculatePossibleRevenue(int showingID, Connection connection) throws SQLException
    {
    	// Create a callable statement to execute
    	String procedureCall = "CALL CalculatePossibleRevenue(?)";
        CallableStatement cs = connection.prepareCall(procedureCall);
        
        // Set the parameter value
        cs.setInt(1, showingID);
        
        // Execute the stored procedure
        ResultSet rs = cs.executeQuery();
        
        ResultSetMetaData metadata = rs.getMetaData();
        int colCount = metadata.getColumnCount();
        
        // Print column names
        System.out.println();
        for (int i = 1; i <= colCount; i++) 
        {
            System.out.print(metadata.getColumnName(i) + "\t");
        }
        System.out.println();

        // Print values
        rs.next();
        for (int i = 1; i <= colCount; i++) 
        {
            System.out.print(rs.getString(i) + "\t\t");
        }
        System.out.println();
        
    } // end CalculatePossibleRevenue
    
    /**
     * Static method enabling the user to add a movie to the movie table in the database.
     * @param id the id of the movie to add
     * @param name the name of the movie to add
     * @param rating the rating of the movie to add
     * @param date the date of the movie to add
     * @param connection
     * @throws SQLException 
     */
    public static void AddMovie(int id, String name, double rating,
    		                    Date date, Connection connection) throws SQLException
    {
    	// Create a callable statement to execute
    	String procedureCall = "CALL AddMovie(?, ?, ?, ?)";
        CallableStatement cs = connection.prepareCall(procedureCall);
        
        // Set the parameter values
        cs.setInt(1, id);
        cs.setString(2, name);
        cs.setDouble(3, rating);
        cs.setDate(4, date);
        
        // Execute the stored procedure
        int rowsAffected = cs.executeUpdate();

        // Print the result
        System.out.println("\nRows affected: " + rowsAffected);
    } // end AddMovie
    
    /**
     * Static method enabling the user to see the average rating for all
     * movies in the movies table.
     * @param connection
     * @throws SQLException
     */
    public static void GetAvgRating(Connection connection) throws SQLException
    {
    	// Create a callable statement to execute
    	String procedureCall = "CALL GetAvgRating(?)";
        CallableStatement cs = connection.prepareCall(procedureCall);
        
        // Register the OUT parameter (avg_rating)
        cs.registerOutParameter(1, Types.DECIMAL);

        // Execute the stored procedure
        cs.execute();

        // Retrieve the OUT parameter value
        double averageRating = cs.getDouble(1);

        // Display the average rating
        System.out.println("\nAvgRating\n" + averageRating);
    } // end GetAvgRating
    
    /**
     * Static method allowing user to modify the capacity of a theater,
     * in the case that an expansion or reduction in size occurred.
     * @param id the id of the theater
     * @param capacity the new capacity of the theater
     * @param connection
     * @throws SQLException
     */
    public static void ModifyCapacity(int id, int capacity, Connection connection) throws SQLException
    {
    	// Create a callable statement to execute
    	String procedureCall = "CALL ModifyCapacity(?, ?)";
        CallableStatement cs = connection.prepareCall(procedureCall);
        
        // Set parameters
        cs.setInt(1, id);
        cs.setInt(2, capacity);
        
        // Execute the stored procedure
        int rowsAffected = cs.executeUpdate();

        // Print the result
        System.out.println("\nRows affected: " + rowsAffected);
    } // end ModifyCapacity
    
    /**
     * Static method allowing the user to cancel a specific showing
     * @param id the id of the showing to cancel
     * @param connection
     * @throws SQLException
     */
    public static void CancelShowing(int id, Connection connection) throws SQLException
    {
    	// Create a callable statement to execute
    	String procedureCall = "CALL CancelShowing(?)";
        CallableStatement cs = connection.prepareCall(procedureCall);
        
        // Set parameters
        cs.setInt(1, id);
        
        // Execute the stored procedure
        int rowsAffected = cs.executeUpdate();

        // Print the result
        System.out.println("\nRows affected: " + rowsAffected);
    } // end CancelShowing
    
    /**
     * Static method calling one of 4 stored procedures which selects all rows from a table.
     * The method then displays the data to the screen.
     * @param tableName
     * @param connection
     * @throws SQLException
     */
    public static void displayTable(String tableName, Connection connection) throws SQLException
    {
    	// Create a callable statement to execute
    	String procedureCall = "CALL Select" + tableName.toUpperCase() + "()";
        CallableStatement cs = connection.prepareCall(procedureCall);
        
        // Execute the select statement
        ResultSet rs = cs.executeQuery();
        
        
        ResultSetMetaData metadata = rs.getMetaData();
        int colCount = metadata.getColumnCount();
        
        // Print column names
        System.out.println();
        for (int i = 1; i <= colCount; i++) 
        {
            System.out.print(metadata.getColumnName(i) + ", ");
        }
        System.out.println("\n");

        // Print values
        while(rs.next())
        {
        	for (int i = 1; i <= colCount; i++) 
            {
                System.out.print(rs.getString(i) + "\t\t");
            }
            System.out.println();
        }
        
    } // end displayTable
    
} // end class
