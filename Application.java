/**
 * This class contains the code with logic to run the application allowing for
 * interaction with the STEVESCINEMAS database.
 * @author Kirin Sharma
 * @version 1.0
 * CS 300 Final Project
 *
 */


import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class Application 
{
	
    @SuppressWarnings("deprecation")
	public static void main(String[] args) throws SQLException
    {
    	// Establish a connection to the database
        Connection connection = DBInteractor.getConnection();
        if (connection != null) 
        {
            System.out.println("Connected to the database!\n");
        }
        
    	Scanner scan = new Scanner(System.in);

        int selection;
        
        // Loop which displays options to interact with the database
        while(true)
        {
        	// Print the menu of options
            System.out.println("\nPlease select from one of the options below: ");
            System.out.println("1. Calculate the possible revenue for a specific showing");
            System.out.println("2. Add a new movie record to the movie table");
            System.out.println("3. Find the average rating of all movies in the database");
            System.out.println("4. Modify the capacity of a theater");
            System.out.println("5. Cancel or remove a showing from the showing table");
            System.out.println("6. Display an entire table");
            System.out.println("7. Exit application");
            System.out.print("Enter a number 1-7 to make your selection: ");
            
            // Receive the user's selection
            selection = scan.nextInt();
            
            // Logic for each option
            if(selection == 1)
            {
            	System.out.println("\nYou chose to calculate the possible revenue for a showing.");
            	System.out.print("Please type in the showingID you would like to "
            			+ "calculate the possible revenue for: ");
            	int showingID = scan.nextInt();
            	
            	DBInteractor.CalculatePossibleRevenue(showingID, connection);
            }
            else if(selection == 2)
            {
            	System.out.println("\nYou chose to add a movie to the movie table.");
            	System.out.print("Please type in the id of the movie you would like to add: ");
            	int movieID = scan.nextInt();
            	System.out.print("\nPlease type in the name of the movie you would like to add: ");
            	scan.nextLine();
            	String movieName = scan.nextLine();
            	System.out.print("Please type in the rating of the movie you would like to add: ");
            	double rating = scan.nextDouble();
            	System.out.print("Please type in the year the movie was released: ");
            	int year = scan.nextInt() - 1900;
            	System.out.print("Please type in the month the movie was released (1-12): ");
            	int month = scan.nextInt() - 1;
            	System.out.print("Please type in the day the movie was released (1-31): ");
            	int day = scan.nextInt();
            	Date releaseDate = new Date(year, month, day);
            	
            	DBInteractor.AddMovie(movieID, movieName, rating, releaseDate, connection);
            }
            else if(selection == 3)
            {
            	System.out.println("\nYou chose to calculate the average rating of"
            			+ " all movies in the movie table.");
            	
            	DBInteractor.GetAvgRating(connection);
            }
            else if(selection == 4)
            {
            	System.out.println("\nYou chose to modify the capacity of the theater");
            	System.out.print("Please type in the theaterID of the theater you would like to "
            			+ "change the capacity of: ");
            	int theaterID = scan.nextInt();
            	System.out.println("Please type in the updated capacity: ");
            	int capacity = scan.nextInt();
            	
            	DBInteractor.ModifyCapacity(theaterID, capacity, connection);
            }
            else if(selection == 5)
            {
            	System.out.println("\nYou chose to cancel a showing.");
            	System.out.print("Please type in the showingID you would like to cancel: ");
            	int showingID = scan.nextInt();
            	
            	DBInteractor.CancelShowing(showingID, connection);
            }
            else if(selection == 6)
            {
            	System.out.println("\nYou chose to display an entire table.");
            	System.out.print("Please type in the name of the table you would like to view: ");
            	String tableName = scan.next().strip().toUpperCase();
            	
            	try
            	{
                	DBInteractor.displayTable(tableName, connection);
            	} catch (SQLException e)
            	{
            		System.out.println("\nThat is not a valid table name. Please try again.");
            	}
            	
            }
            else if(selection == 7)
            {
            	break;
            }
            else
            {
            	System.out.println("\nThat is not a valid option. Please select something else.");
            }
            
        } // end while loop
        
        // Close the scanner and print a final message
        scan.close();
        System.out.println("\nThank you for using the application!");
        
    } // end main
    
} // end Application
