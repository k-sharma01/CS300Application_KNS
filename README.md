# CS300Application_KNS

This project demonstrates database interaction using Java and MySQL. It includes methods for connecting to a MySQL database, calling stored procedures, and managing data.

## **Prerequisites**

1. **Java Development Kit (JDK):**
   - Ensure JDK 8 or higher is installed on your machine.

2. **MySQL Server:**
   - Set up a MySQL database server and create the required database and stored procedures.

3. **MySQL Connector JAR:**
   - Download the MySQL Connector/J library from the [official website](https://dev.mysql.com/downloads/connector/j/).
   - Use version `9.1.0` (or a compatible version).
   - Place the downloaded `.jar` file in the project directory or any directory of your choice.
   - Add this jar file path into your PATH environment variable to ensure successful compilation.

## **Setup Instructions**

### **1. Database Configuration**
Update the database credentials in `DBInteractor.java`:

private static final String URL = "jdbc:mysql://localhost:3306/STEVESCINEMAS";
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";

### **2. Execute Data Definition Script in MySQL**
Be sure to run the STEVESCINEMASDD.sql file to initialize the database, some sample rows, and the stored procedures

### **3. Compile the project**
Change into the directory containing the project, and execute javac *.java to compile all java files.

### **4. Run the Application**
From the same directory, execute java Application to run the program.
